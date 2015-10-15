package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import caches.LRU;

public class LRUTests {
	
	  @Test
	  public void basicHitMissTests() {
		int CAPACITY 	= 3;
		int WARMUP 		= 0;
	    LRU cache 		= new LRU(CAPACITY, false, WARMUP);
	    
	    assertFalse(cache.get("a", 1)); // (a1)
	    assertFalse(cache.get("b", 1)); // (a1 b1)
	    assertFalse(cache.get("c", 1)); // (a1 b1 c1)
	    
	    assertTrue(cache.get("a", 1)); // (a2 b1 c1)
	    assertTrue(cache.get("b", 1)); // (a2 b2 c1)
	    assertTrue(cache.get("c", 1)); // (a2 b2 c2)
	  }
	  
	  @Test
	  public void removalPolicy() {
		int CAPACITY 	= 3;
		int WARMUP 		= 0;
	    LRU cache 		= new LRU(CAPACITY, false, WARMUP);
	    
	    cache.get("a", 1); // (a)
	    cache.get("b", 1); // (b a)
	    cache.get("c", 1); // (c b a)

	    assertTrue(cache.isRequestInCache("a", 1));
	    assertTrue(cache.isRequestInCache("b", 1));
	    assertTrue(cache.isRequestInCache("c", 1));
	    
	    cache.get("d", 1); // (d c b)
	    assertFalse(cache.isRequestInCache("a", 1));
	    assertTrue(cache.isRequestInCache("d", 1));
	    assertTrue(cache.isRequestInCache("c", 1)); 
	    assertTrue(cache.isRequestInCache("b", 1));
	    	    
	    cache.get("d", 1); // (d c b)
	    assertTrue(cache.isRequestInCache("d", 1));
	    assertTrue(cache.isRequestInCache("c", 1)); 
	    assertTrue(cache.isRequestInCache("b", 1));
	    
	    cache.get("e", 1); // (e d c)
	    assertTrue(cache.isRequestInCache("e", 1));
	    assertTrue(cache.isRequestInCache("d", 1)); 
	    assertTrue(cache.isRequestInCache("c", 1));
	    assertFalse(cache.isRequestInCache("b", 1)); 
	  }
}