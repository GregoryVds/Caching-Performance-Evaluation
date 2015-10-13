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

	public void get(String url, int size) {
		Request rqst = new Request(url,size);
		if (this.capacityInBytes)
			getTache2(rqst);
		else
			getTache1(rqst);
	}
	
	public Boolean isInCache(Request rqst) {
		return linkedlist.contains(rqst);
	}
	
	public void getTache1(Request rqst) {
		System.out.println("Get");
		if (isInCache(rqst)) {
			System.out.println("Hit");
			newHit(rqst.getSize());
			linkedlist.remove(rqst);
			linkedlist.addFirst(rqst);
		}
		else { 
			System.out.println("Miss");
			newMiss(rqst.getSize());
			if (linkedlist.size() < this.capacity)
				linkedlist.removeLast();
			linkedlist.addFirst(rqst);
		}
	}
	
	public void getTache2(Request rsqt) {
		
	}

	public List<String> getCacheContent() {
		ArrayList<String> stringCache = new ArrayList<String>();
		while(linkedlist.size() > 0){
			Request temp = linkedlist.removeLast();
			stringCache.add(temp.getUrl());
		}
		return stringCache;	
	}
}

