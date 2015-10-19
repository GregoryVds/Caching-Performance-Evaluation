package caches;

import java.util.List;

public abstract class Cache {
	// INTERNAL ACCOUNTING
	private int warmup; 				// #warmup request are ignored for computing hit rate.
	private long hitsCount;				
	private long hitsBytes;
	private long missesCount;
	private long missesBytes;
	private Boolean capaIsInBytes;		// Indicates if cache capacity is expressed in bytes or requests.
	private long capacity;				// Max cache capacity (in bytes or request depending of capaIsInBytes)
	private long capacityFilledInBytes; // Space currently occupied in bytes.
	private long elementsInCache;		// Number of elements currently in cache. 
	
	// TO BE IMPLEMENTED BY SUBCLASSES
	// This abstract class contains the logic common to all cache implementations.
	// The various implementations differ in the data structure they use internally
	// and in their removal policy.
	// 
	// All the accounting is done here:
	// 	- Managing the warm up phase.
	// 	- Computing the hit rate and byte hit rate.
	//  - Computing the space left in cache. 
	
	// Should return the content of the cache.
	public abstract List<String> getCacheContent();
	// Check if the request is already in cache. Returns request or null if not present.
	protected abstract Request cachedRequest(Request rqst);
	// Hook called after each new hit. Should perform some accounting here.
	protected abstract void newHitForRequest(Request rqst);
	// Add the request in the cache.
	protected abstract void addToCache(Request rqst);
	// Free space in cache in order to store a new request.
	protected abstract Request freeSlotInCache();
	// Free space in cache in order to store a new request.
	protected abstract void flushFromCache(Request rqst);
	
	// PUBLIC INTERFACE
	public Cache(long capacity, Boolean capacityInBytes, int warmup) {
		hitsCount 	= 0;
		hitsBytes 	= 0;
		missesCount = 0;
		missesBytes = 0;
		capacityFilledInBytes= 0;
		elementsInCache 	 = 0;
		this.capacity 		 = capacity;
		this.warmup	 		 = warmup;
		this.capaIsInBytes   = capacityInBytes;
	}
	
	// Returns true if request is already in cache, false otherwise.
	// Update the cache content with the new request.
	public boolean get(String url, int size) {
		warmup--;
		Request rqst 		  = new Request(url,size);
		Request cachedVersion = cachedRequest(rqst);
		// Exact match for request in cache.
		if (cachedVersion != null && cachedVersion.size==rqst.size) {
			accountNewHit(rqst.size);
			newHitForRequest(rqst);
			return true;
		}
		// No exact match for request. 
		else {
			accountNewMiss(rqst.size);
			// Request is in cache but size changed. We should flush the previous request.
			if (cachedVersion != null) {
				flushFromCache(cachedVersion);
				accountRequestRemoval(cachedVersion);
			}
			// No room left for new request, we should free some space. 
			if (requestFitsInCache(rqst)) {
				while (!roomLeftForRequest(rqst))
					accountRequestRemoval(freeSlotInCache());
				addToCache(rqst);
				accountRequestInsertion(rqst);
			}
			return false;
		}
	}
	
	// Returns the cache current hit rate.   
	public double getHitRate() {
		if (getsCount()==0)
			return 0.0d;
		return ((double)hitsCount)/getsCount();
	}

	// Returns the cache current bytes hit rate.
	public double getByteHitRate() {
		if (getsCount()==0)
			return 0.0d;
		return ((double)hitsBytes)/(missesBytes+hitsBytes);
	}
	
	// Convenient helper for testing.
	public Boolean isRequestInCache(String url, int size) {
		Request cachedVersion = cachedRequest(new Request(url, size));
		if (cachedVersion != null && cachedVersion.size == size)
			return true;
		else
			return false;
	}
	
	// PRIVATE METHODS
	// Methods used internally for accounting.
	
	// Returns true if there is enough space left in cache to store the new request, false otherwise.
	private boolean roomLeftForRequest(Request rqst) {
		if (capaIsInBytes)
			return ((capacityFilledInBytes+rqst.size) <= capacity); 
		else
			return (elementsInCache < capacity);
	}
	
	// Returns true if the request is smaller than the cache size, false otherwise.
	private boolean requestFitsInCache(Request rqst) {
		if (capaIsInBytes)
			return (rqst.size <= capacity); 
		else
			return (capacity > 0);
	}
	
	// Called on new hit for request. Performs some accounting used for computing the (byte) hit rate.
	private void accountNewHit(int bytes) {
		if (warmup < 0) {
			hitsCount++;
			hitsBytes+=bytes;
		}
	}

	// Called on new miss for request. Performs some accounting used for computing the (byte) hit rate.
	private void accountNewMiss(int bytes) {
		if (warmup < 0) {
			missesCount++;
			missesBytes+=bytes;
		}
	}
	
	// Called when request inserted in cache. Performs some accounting used for computing the space left in cache.
	private void accountRequestInsertion(Request rqst) {
		capacityFilledInBytes+=rqst.size;
		elementsInCache++;
	}
	
	// Called when request removed from cache. Performs some accounting used for computing the space left in cache.
	private void accountRequestRemoval(Request rqst) {
		capacityFilledInBytes-=rqst.size;
		elementsInCache--;
	}
	
	// Helper. Returns number of gets method calls.
	private long getsCount() {
		return hitsCount+missesCount;
	}
}
