package cache_contents_mngt;

import java.util.List;

public interface CacheOperations {
	public void hitForRequest(Request rqst);
	public void put(Request rqst);
	public void freeSpace(Request rqst);
	public List<String> getCacheContent();
	public Boolean isInCache(Request rqst);
}
