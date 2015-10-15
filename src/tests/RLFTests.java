package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import caches.RLF;

public class RLFTests {
	
	  @Test
	  public void basicHitMissTests() {
		int CAPACITY 	= 3;
		int WARMUP 		= 0;
		RLF cache 		= new RLF(CAPACITY, false, WARMUP);
	    
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
		RLF cache 		= new RLF(CAPACITY, true, WARMUP);
	    
	    cache.get("a", 15); // (a)
	    cache.get("b", 10); // (a b)
	    cache.get("c", 5); // (a b c)

	    assertTrue(cache.isRequestInCache("a", 15));
	    assertTrue(cache.isRequestInCache("b", 10));
	    assertTrue(cache.isRequestInCache("c", 5));
	    
	    cache.get("d", 15); // (d b c)
	    assertFalse(cache.isRequestInCache("a", 15));
	    assertTrue(cache.isRequestInCache("d", 15));
	    assertTrue(cache.isRequestInCache("b", 10));
	    assertTrue(cache.isRequestInCache("c", 5));

	    cache.get("d", 20); // (d c)
	    assertFalse(cache.isRequestInCache("a", 15));
	    assertFalse(cache.isRequestInCache("b", 10));
	    assertTrue(cache.isRequestInCache("c", 5));
	    assertTrue(cache.isRequestInCache("d", 20));
	    
	    cache.get("e", 10); // (e c)
	    assertFalse(cache.isRequestInCache("d", 20));
	    assertTrue(cache.isRequestInCache("c", 5));
	    assertTrue(cache.isRequestInCache("e", 10));

	    cache.get("f", 15); // (f e c)
	    assertTrue(cache.isRequestInCache("c", 5));
	    assertTrue(cache.isRequestInCache("e", 10));
	    assertTrue(cache.isRequestInCache("f", 15));
	    
	    cache.get("g", 5); // (e c g)
	    assertFalse(cache.isRequestInCache("f", 15));
	    assertTrue(cache.isRequestInCache("e", 10));
	    assertTrue(cache.isRequestInCache("c", 5));
	    assertTrue(cache.isRequestInCache("g", 5));
	  }
}