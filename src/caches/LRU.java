package caches;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.map.LinkedMap;

// Implement Least Recently Used cache.
public class LRU extends Cache {
	// LinkedMap is a map implementation that maintains the order of the entries
	// The order is maintained by original insertion.
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
	
	// The first index in the LinkedMap is always the entry that was added first among all entries.
	protected Request freeSlotInCache() {
		return linkedMap.remove(0);
	}
	
	// Last entry added to LinkedMap at always at the tail and take the last index.
	protected void addToCache(Request rqst) {
		linkedMap.put(rqst.url, rqst);
	}
	
	protected void flushFromCache(Request rqst) {
		linkedMap.remove(rqst.url);
	}
}