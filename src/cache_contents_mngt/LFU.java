package cache_contents_mngt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LFU extends Cache {

	public LFU(int capacity, Boolean capacityInBytes, int warmup) {
		super(capacity, capacityInBytes, warmup);
		// Logic Custom
	}

	public void get(String url, int size) {
		super.get(url, size);
	}
	
	public List<String> getCacheContent() {
		return new ArrayList<String>(Arrays.asList(
				"/history/apollo/",
				"/shuttle/countdown/",
				"/shuttle/missions/sts-73/mission-sts-73.html"
		));
	}

}