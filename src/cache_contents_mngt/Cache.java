package cache_contents_mngt;

import java.util.List;

public class Cache {

	protected int hitsCount;
	protected int hitsBytes;
	protected int missesCount;
	protected int missesBytes;
	protected int capacity;
	protected int warmup;
	protected int capacityFilledInBytes;
	protected int elementsInCache;
	protected Boolean capacityExpressedInBytes;
	
	// TO BE OVERIDDEN BY SUBCLASSES
	public void hitForRequest(Request rqst) {} // Do some accounting after a hit if necessary.
	public void freeSpace(Request rqst) {} // Free space according to cache type policy.
	public List<String> getCacheContent(){return null;} // Return the cache content (depends on the data structure used)
	public Boolean isInCache(Request rqst){return null;} // Check if item is in cache
	
	public Cache(int capacity, Boolean capacityInBytes, int warmup) {
		hitsCount 	= 0;
		hitsBytes 	= 0;
		missesCount = 0;
		missesBytes = 0;
		capacityFilledInBytes = 0;
		elementsInCache = 0;
		this.capacity 		 = capacity;
		this.warmup	 		 = warmup;
		this.capacityExpressedInBytes = capacityInBytes;
	}
	
	public void get(String url, int size) {
		warmup--;
		Request rqst = new Request(url,size);
		if (isInCache(rqst)) {
			newHit(rqst.size);
			hitForRequest(rqst);
		}
		else { 
			newMiss(rqst.size);
			if (!fitsInCache(rqst))
				freeSpace(rqst);
			put(rqst);
		}
	}

	public int getsCount() {
		return hitsCount+missesCount;
	}
	
	public void newHit(int bytes) {
		hitsCount++;
		hitsBytes+=bytes;
	}

	public void newMiss(int bytes) {
		missesCount++;
		missesBytes+=bytes;
	}
	
	public void put(Request rqst) {
		capacityFilledInBytes+=rqst.size;
		elementsInCache++;
	}
	
	public void remove(Request rqst) {
		capacityFilledInBytes-=rqst.size;
		elementsInCache--;
	}
	
	public boolean fitsInCache(Request rqst) {
		if (capacityExpressedInBytes)
			return (capacity >= (capacityFilledInBytes + rqst.size)); 
		else
			return (elementsInCache < capacity);
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
}
