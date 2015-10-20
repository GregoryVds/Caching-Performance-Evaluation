package caches;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.map.LinkedMap;

// Implement Least Recently Used cache.
public class LRU extends Cache {

	LinkedMap<String, Request> linkedMap;
	
	public LRU(int capacity, Boolean capacityInBytes, int warmup) {
		super(capacity, capacityInBytes, warmup);
		linkedMap = new LinkedMap<String, Request>();
	}
	
	public List<String> getCacheContent() {
		ArrayList<String> stringCache = new ArrayList<String>();
		for (String key : linkedMap.keySet())
			stringCache.add(key);
		return stringCache;	
	}
	
	protected Request cachedRequest(Request rqst) {
		return linkedMap.get(rqst.url);
	}
		
	protected void newHitForRequest(Request rqst) {
		linkedMap.remove(rqst.url);
		linkedMap.put(rqst.url, rqst);
	}
	
	protected Request freeSlotInCache() {
		return linkedMap.remove(0);
	}
	
	protected void addToCache(Request rqst) {
		linkedMap.put(rqst.url, rqst);
	}
	
	protected void flushFromCache(Request rqst) {
		linkedMap.remove(rqst.url);
	}
}