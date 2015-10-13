package cache_contents_mngt;

public class Cache {

	protected int hitsCount;
	protected int hitsBytes;
	protected int missesCount;
	protected int missesBytes;
	protected int capacity;
	protected int warmup;
	protected Boolean capacityInBytes;


	Cache(int capacity, Boolean capacityInBytes, int warmup) {
		hitsCount 	= 0;
		hitsBytes 	= 0;
		missesCount = 0;
		missesBytes = 0;
		this.capacity 		 = capacity;
		this.warmup	 		 = warmup;
		this.capacityInBytes = capacityInBytes;
	}

	public void get(String url, int size) {
		warmup--;
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
}
