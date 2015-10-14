package cache_contents_mngt;

import java.util.List;

public abstract class Cache {

	private int hitsCount;
	private int hitsBytes;
	private int missesCount;
	private int missesBytes;
	private int capacity;
	private int warmup;
	private int capacityFilledInBytes;
	private int elementsInCache;
	
	protected Boolean capaIsInBytes;
	
	// TO BE OVERIDDEN BY SUBCLASSES
	public abstract List<String> getCacheContent(); // Return the cache content (depends on the data structure used)
	protected abstract void newHitForRequest(Request rqst); // Do some accounting after a hit if necessary.
	protected abstract Boolean isRequestInCache(Request rqst); // Check if item is in cache
	protected abstract void put(Request rqst);
	protected abstract void freeSpaceForRequest(Request rqst); // Free space according to cache type policy.
	
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
			if (!requestFitsInCache(rqst))
				freeSpaceForRequest(rqst);
			put(rqst);
			accountRequestInsertion(rqst);
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
	
	// INTERFACE FOR SUBCLASSES
	protected void accountNewHit(int bytes) {
		if (warmup <= 0) {
			hitsCount++;
			hitsBytes+=bytes;
		}
	}

	protected void accountNewMiss(int bytes) {
		if (warmup <= 0) {
			missesCount++;
			missesBytes+=bytes;
		}
	}
	
	protected void accountRequestRemoval(Request rqst) {
		capacityFilledInBytes-=rqst.size;
		elementsInCache--;
	}
	
	protected void accountRequestInsertion(Request rqst) {
		capacityFilledInBytes+=rqst.size;
		elementsInCache++;
	}
	
	protected boolean requestFitsInCache(Request rqst) {
		if (capaIsInBytes)
			return ((capacityFilledInBytes+rqst.size) <= capacity); 
		else
			return (elementsInCache < capacity);
	}
	
	// PRIVATE METHODS
	private int getsCount() {
		return hitsCount+missesCount;
	}
}
