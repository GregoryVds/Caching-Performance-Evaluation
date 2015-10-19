package caches;

// Implement Least Frequently Used cache.
public class LFU extends PriorityQueueBasedCache {
	
	public LFU(int capacity, Boolean capacityInBytes, int warmup) {
		super(capacity, capacityInBytes, warmup);
	}
	
	protected void newHitForRequest(Request rqst) {
		priorityQueue.changePriority(rqst, priorityQueue.getPriority(rqst)-1); // Higher priority will be dropped first
	}
	
	protected void addToCache(Request rqst) {
		priorityQueue.add(rqst, 0);
	}
}