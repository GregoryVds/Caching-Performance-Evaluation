package cache_contents_mngt;

import java.util.LinkedList;

public class LRU extends Cache {

	LinkedList<String> linkedlist;

	LRU(int capacity, Boolean capacityInBytes, int warmup) {
		super(capacity, capacityInBytes, warmup);
		// Logic Custom
		linkedlist = new LinkedList<String>();

	}


	public void get(String url, int size) {
		super.get(url, size);
	}

	/*List<String> getCacheContent() {
		return new ArrayList<String>(Arrays.asList(
				"/history/apollo/",
				"/shuttle/countdown/",
				"/shuttle/missions/sts-73/mission-sts-73.html"
		));
	}*/
}

