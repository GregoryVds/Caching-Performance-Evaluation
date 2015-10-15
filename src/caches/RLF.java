package caches;

import java.util.ArrayList;
import java.util.List;
import edu.stanford.nlp.util.BinaryHeapPriorityQueue;

public class RLF extends Cache {
	
	BinaryHeapPriorityQueue<Request> priorityQueue;
	
	public RLF(int capacity, Boolean capacityInBytes, int warmup) {
		super(capacity, capacityInBytes, warmup);
		priorityQueue = new BinaryHeapPriorityQueue<Request>();
	}
	
	public List<String> getCacheContent() {
		ArrayList<String> stringCache = new ArrayList<String>();
		for (Request rqst : priorityQueue) {
			stringCache.add(rqst.url);
		}
		return stringCache;	
	}

	protected Boolean isRequestInCache(Request rqst) {
		return priorityQueue.contains(rqst);
	}
	
	protected void newHitForRequest(Request rqst) {
		// Do some accounting if necessary.
	}
	
	protected void addToCache(Request rqst) {
		priorityQueue.add(rqst, rqst.size);
	}
	
	protected Request freeSlotInCache() {
		return priorityQueue.removeFirst();
	}
}