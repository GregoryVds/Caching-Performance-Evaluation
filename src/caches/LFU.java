package caches;

import java.util.ArrayList;
import java.util.List;
import edu.stanford.nlp.util.BinaryHeapPriorityQueue;

public class LFU extends Cache {

	BinaryHeapPriorityQueue<Request> priorityQueue;
	
	public LFU(int capacity, Boolean capacityInBytes, int warmup) {
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
	
	protected void newHitForRequest(Request rqst) {
		priorityQueue.changePriority(rqst, priorityQueue.getPriority(rqst)+1);
	}
	
	protected Boolean isRequestInCache(Request rqst) {
		return priorityQueue.contains(rqst);
	}
	
	protected void addToCache(Request rqst) {
		priorityQueue.add(rqst, 0);
	}
	
	protected Request freeSlotInCache() {
		return priorityQueue.removeFirst();
	}
}