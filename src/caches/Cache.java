package caches;

import java.util.List;

public abstract class Cache {
	// INTERNAL ACCOUNTING
	private int hitsCount;
	private int hitsBytes;
	private int missesCount;
	private int missesBytes;
	private int capacity;
	private int warmup;
	private int capacityFilledInBytes;
	private int elementsInCache;
	private Boolean capaIsInBytes;
	
	// TO BE IMPLEMENTED BY SUBCLASSES
	// Should return the content of the cache.
	public abstract List<String> getCacheContent();
	// Hook called after each new hit. Should perform some accounting here.
	protected abstract void newHitForRequest(Request rqst);
	// Check if the request is already in cache.
	protected abstract Boolean isRequestInCache(Request rqst);
	// Add the request in the cache.
	protected abstract void addToCache(Request rqst);
	// Free space in cache in order to store a new request.
	protected abstract Request freeSlotInCache();
	
	// PUBLIC INTERFACE
	public Cache(int capacity, Boolean capacityInBytes, int warmup) {
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
	
	public void get(String url, int size) {
		warmup--;
		Request rqst = new Request(url,size);
		if (isRequestInCache(rqst)) {
			accountNewHit(rqst.size);
			newHitForRequest(rqst);
		}
		else {
			accountNewMiss(rqst.size);
			if (requestFitsInCache(rqst)) {
				while (!roomLeftForRequest(rqst))
					accountRequestRemoval(freeSlotInCache());
				addToCache(rqst);
				accountRequestInsertion(rqst);
			}
		}
	}
	
	public double getHitRate() {
		if (getsCount()==0)
			return 0.0d;
		return ((double)hitsCount)/getsCount();
	}

	public double getByteHitRate() {
		if (getsCount()==0)
			return 0.0d;
		return ((double)hitsBytes)/(missesBytes+hitsBytes);
	}
	
	// PRIVATE METHODS
	private boolean roomLeftForRequest(Request rqst) {
		if (capaIsInBytes)
			return ((capacityFilledInBytes+rqst.size) <= capacity); 
		else
			return (elementsInCache < capacity);
	}
	
	private boolean requestFitsInCache(Request rqst) {
		if (capaIsInBytes)
			return (rqst.size <= capacityFilledInBytes); 
		else
			return true;
	}
	
	private void accountNewHit(int bytes) {
		if (warmup <= 0) {
			hitsCount++;
			hitsBytes+=bytes;
		}
	}

	private void accountNewMiss(int bytes) {
		if (warmup <= 0) {
			missesCount++;
			missesBytes+=bytes;
		}
	}
	
	private void accountRequestInsertion(Request rqst) {
		capacityFilledInBytes+=rqst.size;
		elementsInCache++;
	}
	
	private void accountRequestRemoval(Request rqst) {
		capacityFilledInBytes-=rqst.size;
		elementsInCache--;
	}
	
	private int getsCount() {
		return hitsCount+missesCount;
	}
}
