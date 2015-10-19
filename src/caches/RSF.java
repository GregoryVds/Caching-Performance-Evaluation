package caches;

public class RSF extends PriorityQueueBasedCache {

	public RSF(int capacity, Boolean capacityInBytes, int warmup) {
		super(capacity, capacityInBytes, warmup);
	}
	
	// No accounting necessary for this cache implementation.
	protected void newHitForRequest(Request rqst) {}
		
	protected void addToCache(Request rqst) {
		priorityQueue.add(rqst, -rqst.size);
	}
}