package cache_contents_mngt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cache {

	protected int hitsCounts;
	protected int hitsBytes;
	protected int missesCounts;
	protected int missesBytes;
	protected int capacity;
	protected int warmup;
	protected Boolean capacityInBytes;


	Cache(int capacity, Boolean capacityInBytes, int warmup) {
		hitsCounts =0;
		hitsBytes=0;
		missesCounts = 0;
		missesBytes = 0 ;
		this.capacity = capacity;
		this.warmup = warmup;
		this.capacityInBytes = capacityInBytes;
	}

	int getWarmup() {
		return warmup;
	}

	public void get(String url, int size) {
		warmup--;
	}

	public double getHitRate() {
		return hitsCounts/(hitsCounts+missesCounts);
	}

	public double getByteHitRate() {
		return hitsBytes/(missesBytes+hitsBytes);
	}

	public List<String> getCacheContent() {
		return new ArrayList<String>(Arrays.asList(
				"/history/apollo/",
				"/shuttle/countdown/",
				"/shuttle/missions/sts-73/mission-sts-73.html"
		));
	}

	public void newHit( int bytes){
		hitsCounts++;
		hitsBytes = hitsBytes+bytes;
	}

	public void newMiss( int bytes) {
		missesCounts++;
		missesBytes =+bytes;
	}

}
