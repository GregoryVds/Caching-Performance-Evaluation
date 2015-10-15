package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import caches.LFU;

public class LFUTests {
	
  @Test
  public void capacityInUnits() {
	int CAPACITY 	= 5;
	int WARMUP 		= 0;
	
    LFU cache = new LFU(CAPACITY, false, WARMUP);
    
    assertFalse(cache.get("1", 1));
    assertFalse(cache.get("2", 1));
    assertFalse(cache.get("3", 1));
    
    assertTrue(cache.get("1", 1));
    assertTrue(cache.get("2", 1));
    assertTrue(cache.get("3", 1));
    
    assertFalse(cache.get("4", 1));
    assertFalse(cache.get("5", 1));
    assertTrue(cache.get("4", 1));
    assertTrue(cache.get("5", 1));
    
  }
}