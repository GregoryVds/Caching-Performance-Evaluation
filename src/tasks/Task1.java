package tasks;

import java.io.BufferedReader;
// import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import caches.*;
import lib.Lib;

public class Task1 {

	public static void main(String[] args) throws IOException {
		int warmupSize 		 = Integer.parseInt(args[0]);
		int cacheSizeInLines = Integer.parseInt(args[1]);
	
		LRU LRUCache = new LRU(cacheSizeInLines, false, warmupSize);
		LFU LFUCache = new LFU(cacheSizeInLines, false, warmupSize);
		
		// BufferedReader stdin = new BufferedReader(new FileReader("/Users/Greg/Desktop/trace_tiny.txt"));
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
		String request;
		while((request = stdin.readLine()) != null) {
			String url  = request.split(" ")[0];
			int size = Integer.parseInt(request.split(" ")[1]);
			LRUCache.get(url, size);
			LFUCache.get(url, size);
		}
		
		stdin.close();
		
		Lib.print("LRU Hit rate:", LRUCache.getHitRate());
		Lib.print("LFU Hit rate:", LFUCache.getHitRate());
		
		Lib.dumpCacheContentToFile(LRUCache.getCacheContent(), "cache_lru.txt");
		Lib.dumpCacheContentToFile(LFUCache.getCacheContent(), "cache_lfu.txt");
	}
}
