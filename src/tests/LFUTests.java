package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import caches.LFU;

public class LFUTests {
	
	@Test
	public void basicHitMissTests() {
		int CAPACITY 	= 3;
		int WARMUP 		= 0;
	    LFU cache 		= new LFU(CAPACITY, false, WARMUP);
	    
	    assertFalse(cache.get("a", 1)); // (a1)
	    assertFalse(cache.get("b", 1)); // (a1 b1)
	    assertFalse(cache.get("c", 1)); // (a1 b1 c1)
	    
	    assertTrue(cache.get("a", 1)); // (a2 b1 c1)
	    assertTrue(cache.get("b", 1)); // (a2 b2 c1)
	    assertTrue(cache.get("c", 1)); // (a2 b2 c2)
	}
  
	@Test
	public void removalPolicyTest() {
		int CAPACITY 	= 3;
		int WARMUP 		= 0;
	    LFU cache 		= new LFU(CAPACITY, false, WARMUP);
	    
	    cache.get("a", 1); // (a1)
	    cache.get("a", 1); // (a2)
	    cache.get("a", 1); // (a3)
	    cache.get("b", 1); // (a3 b1)
	    cache.get("b", 1); // (a3 b2)
	    cache.get("c", 1); // (a3 b2 c1)
	
	    assertTrue(cache.isRequestInCache("a", 1));
	    assertTrue(cache.isRequestInCache("b", 1));
	    assertTrue(cache.isRequestInCache("c", 1));
	    
	    cache.get("d", 1); // (a3 b2 d1)
	    assertTrue(cache.isRequestInCache("a", 1));
	    assertTrue(cache.isRequestInCache("b", 1));
	    assertTrue(cache.isRequestInCache("d", 1));
	    assertFalse(cache.isRequestInCache("c", 1)); 
	    
	    cache.get("d", 1); // (a3 b2 d2)
	    cache.get("d", 1); // (a3 d3 b2)
	    cache.get("c", 1); // (a3 d3 c1)
	    assertTrue(cache.isRequestInCache("a", 1));
	    assertTrue(cache.isRequestInCache("d", 1));
	    assertTrue(cache.isRequestInCache("c", 1));
	    assertFalse(cache.isRequestInCache("b", 1)); 
	    
	    cache.get("c", 1); // (a3 d3 c2)
	    cache.get("c", 1); // (a3 d3 c3)
	    cache.get("c", 1); // (c4 a3 d3)
	    cache.get("d", 1); // (c4 d4 a3)
	    cache.get("e", 1); // (c4 d4 e1)
	    assertTrue(cache.isRequestInCache("c", 1));
	    assertTrue(cache.isRequestInCache("d", 1));
	    assertTrue(cache.isRequestInCache("e", 1));
	    assertFalse(cache.isRequestInCache("a", 1)); 
	}
	
	@Test
	public void removalOfChangingItems() {
		int CAPACITY 	= 3;
		int WARMUP 		= 0;
	    LFU cache 		= new LFU(CAPACITY, false, WARMUP);
	    
	    cache.get("a", 1); // (a1)
	    cache.get("b", 1); // (a3 b1)
	    cache.get("b", 2); // (a3 b2)
	    assertTrue(cache.isRequestInCache("a", 1));
	    assertTrue(cache.isRequestInCache("b", 2));
	    assertFalse(cache.isRequestInCache("b", 1));
	}
}