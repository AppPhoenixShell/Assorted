package os;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AppID 
{
	private static Map<Application<?,?>, AppID> appIdMap = new HashMap<>();
	
	private String runtimeUUID;
	private Application<?,?> app;
	
	private AppID(Application<?,?> app) {
		this.app = app;
		this.runtimeUUID = UUID.randomUUID().toString();
	}
	
	protected static AppID getAppID(Application<?,?> app) {
		AppID getId = appIdMap.get(app);
		if(getId == null) {
			getId = new AppID(app);
			appIdMap.put(app, getId);
		}
		return getId;
	}
}
