package caches;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Implement Least Recently Used cache.
public class LRU extends Cache {

	LinkedList<Request> linkedlist;

	public LRU(int capacity, Boolean capacityInBytes, int warmup) {
		super(capacity, capacityInBytes, warmup);
		linkedlist = new LinkedList<Request>();
	}
	
	public List<String> getCacheContent() {
		ArrayList<String> stringCache = new ArrayList<String>();
		while(linkedlist.size() > 0){
			Request temp = linkedlist.removeLast();
			stringCache.add(temp.url);
		}
		return stringCache;	
	}
	
	protected Request cachedRequest(Request rqst) {
		int index = linkedlist.indexOf(rqst);
		if (index == -1)
			return null;
		else
			return linkedlist.get(index);
	}
		
	protected void newHitForRequest(Request rqst) {
		linkedlist.remove(rqst);
		linkedlist.addFirst(rqst);
	}
	
	protected Request freeSlotInCache() {
		return linkedlist.removeLast();
	}
	
	protected void addToCache(Request rqst) {
		linkedlist.addFirst(rqst);
	}
	
	protected void flushFromCache(Request rqst) {
		linkedlist.remove(rqst);
	}
}