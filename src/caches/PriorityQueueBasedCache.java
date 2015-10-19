package caches;

import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.util.BinaryHeapPriorityQueue;

public abstract class PriorityQueueBasedCache extends Cache {
	
	BinaryHeapPriorityQueue<Request> priorityQueue;
	
	public PriorityQueueBasedCache(int capacity, Boolean capacityInBytes, int warmup) {
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
	
	protected Request cachedRequest(Request rqst) {
		return priorityQueue.getObject(rqst);
	}
	
	protected Request freeSlotInCache() {
		return priorityQueue.removeFirst();
	}
	
	protected void flushFromCache(Request rqst) {
		priorityQueue.remove(rqst);
	}	
}
