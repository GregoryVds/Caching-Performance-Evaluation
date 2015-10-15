package caches;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
	
	protected Boolean isRequestInCache(Request rqst) {
		int index = linkedlist.indexOf(rqst);
		if (index == -1) 
			return false;
		else {
			Request cachedVersion = linkedlist.get(index);
			if (cachedVersion.size == rqst.size) 
				return true;
			else
				return false;
		}
	}
	
	protected void newHitForRequest(Request rqst) {
		linkedlist.remove(rqst);
		linkedlist.addFirst(rqst);
	}
	
	protected Request freeSlotInCache() {
		return linkedlist.removeLast();
	}
	
	protected void addToCache(Request rqst) {
		linkedlist.remove(rqst);
		linkedlist.addFirst(rqst);
	}
}