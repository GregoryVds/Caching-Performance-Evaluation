package cache_contents_mngt;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LRU extends Cache implements CacheOperations {

	LinkedList<Request> linkedlist;

	public LRU(int capacity, Boolean capacityInBytes, int warmup) {
		super(capacity, capacityInBytes, warmup);
		linkedlist = new LinkedList<Request>();
	}

	public void get(String url, int size) {
		Request rqst = new Request(url,size);
		if (isInCache(rqst)) {
			newHit(rqst.size);
			linkedlist.remove(rqst);
			linkedlist.addFirst(rqst);
		}
		else { 
			newMiss(rqst.size);
			if (!fitsInCache(rqst))
				freeSpace(rqst);
			put(rqst);
		}
	}
	
	public void hitForRequest(Request rqst) {
		linkedlist.remove(rqst);
		linkedlist.addFirst(rqst);
	}
	
	public Boolean isInCache(Request rqst) {
		return linkedlist.contains(rqst);
	}
	
	@Override
	public void put(Request rqst) {
		super.put(rqst);
		linkedlist.addFirst(rqst);
	}
	
	public void freeSpace(Request rqst) {
		if (capacityExpressedInBytes)
			super.remove(linkedlist.removeLast());
		else {
			int spaceFreed = 0;
			while (spaceFreed < rqst.size) {
				spaceFreed+=linkedlist.getLast().size;
				super.remove(linkedlist.removeLast());
			}
		}
	}
	
	public List<String> getCacheContent() {
		ArrayList<String> stringCache = new ArrayList<String>();
		while(linkedlist.size() > 0){
			Request temp = linkedlist.removeLast();
			stringCache.add(temp.url);
		}
		return stringCache;	
	}
}

