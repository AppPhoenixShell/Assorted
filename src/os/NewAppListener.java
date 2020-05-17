package os;

public interface NewAppListener{
	public <T extends Application<?,?>> void onAppCreate(Class<T> appClass);
}
