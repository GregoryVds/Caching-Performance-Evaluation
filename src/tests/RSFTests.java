package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import caches.RSF;

public class RSFTests {
	
	  @Test
	  public void basicHitMissTests() {
		int CAPACITY 	= 3;
		int WARMUP 		= 0;
		RSF cache 		= new RSF(CAPACITY, false, WARMUP);
	    
	    assertFalse(cache.get("a", 1)); // (a1)
	    assertFalse(cache.get("b", 1)); // (a1 b1)
	    assertFalse(cache.get("c", 1)); // (a1 b1 c1)
	    
	    assertTrue(cache.get("a", 1)); // (a2 b1 c1)
	    assertTrue(cache.get("b", 1)); // (a2 b2 c1)
	    assertTrue(cache.get("c", 1)); // (a2 b2 c2)
	  }
	  
	  @Test
	  public void removalPolicyTest() {
		int CAPACITY 	= 30;
		int WARMUP 		= 0;
		RSF cache 		= new RSF(CAPACITY, true, WARMUP);
	    
	    cache.get("a", 15); // (a15)
	    cache.get("b", 10); // (b10 a15)
	    cache.get("c", 5); // (c5 b10 a15)
	    assertTrue(cache.isRequestInCache("a", 15));
	    assertTrue(cache.isRequestInCache("b", 10));
	    assertTrue(cache.isRequestInCache("c", 5));
	    
	    cache.get("d", 14); // (d14 a15)
	    assertTrue(cache.isRequestInCache("a", 15));
	    assertTrue(cache.isRequestInCache("d", 14));
	    assertFalse(cache.isRequestInCache("b", 10));
	    assertFalse(cache.isRequestInCache("c", 5));

	    cache.get("e", 5); // (e5 a15)
	    assertTrue(cache.isRequestInCache("a", 15));
	    assertTrue(cache.isRequestInCache("e", 5));
	    assertFalse(cache.isRequestInCache("d", 14));
	    
	    cache.get("e", 5); // (e5 a15)
	    cache.get("f", 4); // (f4 e5 a15)
	    assertTrue(cache.isRequestInCache("a", 15));
	    assertTrue(cache.isRequestInCache("e", 5));
	    assertTrue(cache.isRequestInCache("f", 4));
	    
	    cache.get("g", 7); // (e5 g7 a15)
	    assertTrue(cache.isRequestInCache("a", 15));
	    assertTrue(cache.isRequestInCache("g", 7));
	    assertTrue(cache.isRequestInCache("e", 5));
	    assertFalse(cache.isRequestInCache("f", 4));
	    
	    cache.get("h", 4); // (h4 g7 a15)
	    assertTrue(cache.isRequestInCache("a", 15));
	    assertTrue(cache.isRequestInCache("g", 7));
	    assertTrue(cache.isRequestInCache("h", 4));
	    assertFalse(cache.isRequestInCache("e", 5));
	  }
}