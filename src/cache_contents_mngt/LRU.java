package cache_contents_mngt;

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
	
	protected void newHitForRequest(Request rqst) {
		linkedlist.remove(rqst);
		linkedlist.addFirst(rqst);
	}
	
	protected Boolean isRequestInCache(Request rqst) {
		return linkedlist.contains(rqst);
	}
	
	protected void freeSpaceForRequest(Request rqst) {
		if (capaIsInBytes)
			super.accountRequestRemoval(linkedlist.removeLast());
		else {
			int spaceFreed = 0;
			while (spaceFreed < rqst.size) {
				spaceFreed+=linkedlist.getLast().size;
				super.accountRequestRemoval(linkedlist.removeLast());
			}
		}
	}
	
	protected void put(Request rqst) {
		super.accountRequestInsertion(rqst);
		linkedlist.addFirst(rqst);
	}
}

