package caches;

public class RLF extends PriorityQueueBasedCache {
	
	public RLF(int capacity, Boolean capacityInBytes, int warmup) {
		super(capacity, capacityInBytes, warmup);
	}
	
	// No accounting necessary for this cache implementation.
	protected void newHitForRequest(Request rqst) {}
		
	protected void addToCache(Request rqst) {
		priorityQueue.add(rqst, rqst.size);
	}
}