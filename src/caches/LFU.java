package caches;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class LFU extends Cache {

	PriorityQueue<RequestLFU> priorityQueue;
	
	public LFU(int capacity, Boolean capacityInBytes, int warmup) {
		super(capacity, capacityInBytes, warmup);
		// Initialize custom data structures here
		priorityQueue = new PriorityQueue<RequestLFU>();
	}

	public List<String> getCacheContent() {
		ArrayList<String> stringCache = new ArrayList<String>();
		while(priorityQueue.size() > 0)
		{
			Request temp = priorityQueue.poll();
			stringCache.add(temp.url);
		}
		return stringCache;	
	}
	
	protected void newHitForRequest(Request rqst) {
		// HHHAAAAAAAAAA JE N'AI PAS D'IDEE
		 
	}
	
	protected Boolean isRequestInCache(Request rqst) {
		RequestLFU rqstLFU = new RequestLFU(rqst.url,rqst.size);
		return priorityQueue.contains(rqstLFU);
	}
	
	protected void addToCache(Request rqst) {
		RequestLFU rqstLFU = new RequestLFU(rqst.url,rqst.size);
		priorityQueue.add(rqstLFU);
	}
	
	protected Request freeSlotInCache() {
		return priorityQueue.poll();
	}


}