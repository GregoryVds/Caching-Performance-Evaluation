package caches;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RSF extends Cache {

	RSF(int capacity, Boolean capacityInBytes, int warmup) {
		super(capacity, capacityInBytes, warmup);
		// Initialize custom data structures here
	}

	public List<String> getCacheContent() {
		return new ArrayList<String>(Arrays.asList(
				"/history/apollo/",
				"/shuttle/countdown/",
				"/shuttle/missions/sts-73/mission-sts-73.html"
		));
	}
	
	protected void newHitForRequest(Request rqst) {
		// Do some accounting if necessary.
	}
	
	protected Boolean isRequestInCache(Request rqst) {
		return true;
	}
	
	protected void addToCache(Request rqst) {
		// To implement
	}
	
	protected Request freeSlotInCache() {
		return null; // To implement
	}
}