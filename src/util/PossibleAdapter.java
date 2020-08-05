package au.com.possibleme.view;

import android.os.Handler;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.phoenixshell.pcoa.Model;
import app.phoenixshell.pcoa.ModelClientService;
import app.phoenixshell.vvm.ClassVVMBuilder;
import app.phoenixshell.vvm.VVMBuilder;
import app.phoenixshell.vvm.ViewModel;
import app.phoenixshell.pcoa.ModelClientCallback;
import app.phoenixshell.pcoa.ModelConnectionManager;
import au.com.possibleme.api.PublicApi;

public class PossibleAdapter<M extends Model, A> extends RecyclerView.Adapter<PossibleAdapter.PossibleVH<M, A>>
    implements ModelClientService<M>
{
    private final List<M> allCacheModel = new ArrayList<>();
    private final List<M> filteredCacheModel = new ArrayList<>();


    private Lifecycle lifecycle;
    private VVMBuilder vvmBuilder;
    private PublicApi api;
    private Handler mHandler;
    private final ModelConnectionManager manager;
    private AdapterFilter<M> adapterFilter;

    private final ModelClientCallback<M> modelCallback = new ModelClientCallback<M>(){
        @Override
        public void onObject(M newObject) {
            boolean isUpdatedAll = false;
            boolean isUpdateFilter = false;

           for(int i=0; i < allCacheModel.size(); i++){
               M model = allCacheModel.get(i);
               if(model.getId() == newObject.getId()){
                   allCacheModel.set(i, newObject);
                   isUpdatedAll = true;
               }
           }
            //check if streamed ID exists in filtered cache model
            for(int i = 0; i < filteredCacheModel.size(); i++){
                M model = filteredCacheModel.get(i);
                if(model.getId() == newObject.getId()) {
                    filteredCacheModel.set(i, newObject);
                    notifyItemChanged(i);
                    isUpdateFilter = true;
                }
            }
            //if object has been update in a list, no need to add it return
            if(isUpdatedAll || isUpdateFilter)
                return;


            allCacheModel.add(newObject);

            //check if the new object is in the filtered list, if so add it
            if(adapterFilter != null && adapterFilter.inFilteredList(newObject)){
                filteredCacheModel.add(newObject);
                notifyItemInserted(filteredCacheModel.size()-1);
            }
            else if(adapterFilter == null){
                filteredCacheModel.add(newObject);
                notifyItemInserted(filteredCacheModel.size()-1);
            }
        }
        @Override
        public void onRemove(M newObject) {
            for(int i = 0; i < filteredCacheModel.size(); i++){
                M model = filteredCacheModel.get(i);
                if(model.getId() == newObject.getId()) {
                    filteredCacheModel.remove(i);
                    notifyItemRemoved(i);
                }
            }
        }
    };

    public PossibleAdapter(PublicApi api, Lifecycle lifecycle, Class<ViewModel> vvmClass, Handler handler){
        this(api, lifecycle, new ClassVVMBuilder(vvmClass,api), handler);
    }

    public PossibleAdapter(PublicApi api, Lifecycle lifecycle, VVMBuilder vvmBuilder, Handler handler){
        this.manager = new ModelConnectionManager(lifecycle, modelCallback);
        this.lifecycle= lifecycle;
        this.vvmBuilder = vvmBuilder;
        this.api = api;
        this.mHandler = handler;
    }

    @Override
    public ModelClientCallback<M> getModelCallback() {
        return modelCallback;
    }

    public ModelConnectionManager getConnectionManager(){
        return manager;
    }

    @NonNull
    @Override
    public PossibleVH<M, A> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewModel<M, A> newVM = vvmBuilder.buildNewVM(viewType, parent, lifecycle, mHandler);
        PossibleVH<M, A> possibleVH = new PossibleVH<>(newVM);
        return possibleVH;
    }

    @Override
    public void onBindViewHolder(@NonNull PossibleVH<M, A> holder, int position) {
        M bindModel = filteredCacheModel.get(position);
        holder.viewModel.setModel(bindModel);
    }

    @Override
    public int getItemCount() {
        return filteredCacheModel.size();
    }

    public static class PossibleVH<M, A> extends RecyclerView.ViewHolder
    {
        final ViewModel<M, A> viewModel;

        public PossibleVH(ViewModel<M, A> viewModel) {
            super(viewModel.getRoot());
            this.viewModel = viewModel;
        }
    }
}
