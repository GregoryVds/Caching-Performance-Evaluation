package cache_contents_mngt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LRU extends Cache {

	LinkedList<Request> linkedlist;

	LRU(int capacity, Boolean capacityInBytes, int warmup) {
		super(capacity, capacityInBytes, warmup);
		// Logic Custom
		linkedlist = new LinkedList<Request>();

	}

	
	public void get(String url, int size){
		Request rqst = new Request(url,size);
		if(this.capacityInBytes){
			getTache2(rqst);
		}
		else{
			getTache1(rqst);
		}
	}
	
	public void getTache1(Request rqst) { // changer de nom
		
		
		// Si objet present (identiquement) dans le cache 
		if(linkedlist.contains(rqst)){
			linkedlist.remove(rqst);
			linkedlist.addFirst(rqst);
		}
		// objet pas present dans le cache et cache pas au max
		else if(linkedlist.size()<=this.capacity){ 
			linkedlist.addFirst(rqst);
		}
		// objet pas present dans le cache et cache plein
		else {
			linkedlist.removeLast();
			linkedlist.addFirst(rqst);
		}
	}
	
	public void getTache2(Request rsqt) {
		
		
		
	}

	public List<String> getCacheContent() {
		ArrayList<String> stringCache = new ArrayList<String>();
		while(linkedlist.size() > 0){
			Request tampon = linkedlist.removeLast();
			stringCache.add(tampon.getUrl());
		}
		return stringCache;	
	}
}

