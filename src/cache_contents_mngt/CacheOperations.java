package cache_contents_mngt;

import java.util.List;

public interface CacheOperations {
	public List<String> getCacheContent();
	
	Boolean isRequestInCache(Request rqst);
	void newHitForRequest(Request rqst);
	void put(Request rqst);
	void freeSpaceForRequest(Request rqst);
}
