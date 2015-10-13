package cache_contents_mngt;

public class LFU extends Cache {

	LFU(int capacity, Boolean capacityInBytes, int warmup) {
		super(capacity, capacityInBytes, warmup);
		// Logic Custom
	}

	public void get(String url, int size) {
		super.get(url, size);
	}

}