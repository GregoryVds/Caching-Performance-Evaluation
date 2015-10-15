package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import caches.Cache;
import caches.LRU;

public class CacheTests {
	
  @Test
  public void sizeInBytesManagementTest() {
	 int WARMUP 	= 0;
  	 
	 LRU cache = new LRU(30, true, WARMUP);
  	 cache.get("a", 15); 
  	 cache.get("b", 10);
  	 cache.get("c", 5);
  	 assertTrue(cache.isRequestInCache("a", 15));
  	 assertTrue(cache.isRequestInCache("b", 10));
  	 assertTrue(cache.isRequestInCache("c", 5));
  	 
  	 cache = new LRU(10, true, WARMUP);
  	 cache.get("a", 15); 
  	 assertFalse(cache.isRequestInCache("a", 15)); //Not cached, too big.
  	 assertFalse(cache.get("a", 15)); 
  	 
  	 cache = new LRU(11, true, WARMUP);
  	 cache.get("a", 9);
  	 cache.get("b", 3);
  	 assertFalse(cache.isRequestInCache("a", 9));
  	 assertTrue(cache.isRequestInCache("b", 3));
  	 cache.get("c", 8);
  	 cache.get("b", 3);
  	 assertTrue(cache.isRequestInCache("b", 3));
  	 assertTrue(cache.isRequestInCache("c", 8));
  }
  
  @Test
  public void hitRateTest() {
	int CAPACITY 	= 3;
	int WARMUP 		= 0;
    LRU cache 		= new LRU(CAPACITY, false, WARMUP);
    
    assertFalse(cache.get("a", 1)); // 0/0
    assertTrue(assertHitRate(cache, 0.0));
    
    assertFalse(cache.get("b", 1)); // 0/2
    assertTrue(assertHitRate(cache, 0.0));
    
    assertFalse(cache.get("c", 1)); // 0/3
    assertTrue(assertHitRate(cache, 0.0));
    
    assertTrue(cache.get("a", 1)); // 1/4
    assertTrue(assertHitRate(cache, 1.0/4));
    
    assertTrue(cache.get("b", 1)); // 2/5
    assertTrue(assertHitRate(cache, 2.0/5));
    
    assertTrue(cache.get("c", 1)); // 3/6
    assertTrue(assertHitRate(cache, 3.0/6));
    
    assertTrue(cache.get("c", 1)); // 4/7
    assertTrue(assertHitRate(cache, 4.0/7));
  }
  
  @Test
  public void hitRateInBytesTest() {
	int CAPACITY 	= 35;
	int WARMUP 		= 0;
	LRU cache 		= new LRU(CAPACITY, true, WARMUP);
    
    assertFalse(cache.get("a", 20)); // 0/20
    assertTrue(assertHitRate(cache, 0.0));
    
    assertTrue(cache.get("a", 20)); // 20/40
    assertTrue(assertHitRate(cache, 20.0/40));
    assertTrue(cache.isRequestInCache("a", 20));
    
    assertFalse(cache.get("b", 10)); // 20/50
    assertTrue(assertHitRate(cache, 20.0/50));
    
    assertTrue(cache.get("b", 10)); // 30/60
    assertTrue(assertHitRate(cache, 30.0/60));
    assertTrue(cache.isRequestInCache("b", 10));
    
    assertFalse(cache.get("c", 5)); // 30/65
    assertTrue(cache.isRequestInCache("c", 5));
    assertTrue(assertHitRate(cache, 30.0/65));
    
    assertTrue(cache.get("c", 5)); // 35/70
    assertTrue(assertHitRate(cache, 35.0/70));
  }
  
  @Test
  public void warmupTest() {
	int CAPACITY 	= 3;
	int WARMUP 		= 3;
	LRU cache 		= new LRU(CAPACITY, false, WARMUP);
    
    assertFalse(cache.get("a", 1)); // 0/0 (2 hit left for warm up)
    assertTrue(assertHitRate(cache, 0.0));
    
    assertFalse(cache.get("b", 1)); // 0/0 (1 hit left for warm up)
    assertTrue(assertHitRate(cache, 0.0));
    
    assertFalse(cache.get("c", 1)); // 0/0 (0 hit left for warm up)
    assertTrue(assertHitRate(cache, 0.0));
    
    assertTrue(cache.get("a", 1)); // 1/1
    assertTrue(assertHitRate(cache, 1.0/1));
    
    assertTrue(cache.get("b", 1)); // 2/2
    assertTrue(assertHitRate(cache, 2.0/2));
    
    assertFalse(cache.get("d", 1)); // 2/3
    assertTrue(assertHitRate(cache, 2.0/3));
    
    assertTrue(cache.get("a", 1)); // 3/4
    assertTrue(assertHitRate(cache, 3.0/4));
    
    assertFalse(cache.get("c", 1)); // 3/5
    assertTrue(assertHitRate(cache, 3.0/5));
  }
  
  @Test
  public void warmupHitRateInBytesTest() {
	int CAPACITY 	= 35;
	int WARMUP 		= 3;
	LRU cache 		= new LRU(CAPACITY, true, WARMUP);
    
    assertFalse(cache.get("a", 20)); // 0/0
    assertTrue(assertHitRate(cache, 0.0));
    
    assertTrue(cache.get("a", 20)); // 0/0
    assertTrue(assertHitRate(cache, 0.0));
    
    assertFalse(cache.get("b", 10)); // 0/0
    assertTrue(assertHitRate(cache, 0.0));
    
    assertTrue(cache.get("b", 10)); // 10/10
    assertTrue(assertHitRate(cache, 10.0/10));
    
    assertFalse(cache.get("c", 5)); // 10/15
    assertTrue(assertHitRate(cache, 10.0/15));
    
    assertTrue(cache.get("c", 5)); // 15/20
    assertTrue(assertHitRate(cache, 15.0/20));
    
    assertTrue(cache.get("a", 20)); // 35/40
    assertTrue(assertHitRate(cache, 35.0/40));
  }
    
  // HELPER
  public boolean assertHitRate(Cache cache, Double expected) {
	  return (Double.compare(cache.getByteHitRate(), expected) == 0) ? true : false;
  }
}
