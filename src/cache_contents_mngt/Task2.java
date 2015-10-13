package cache_contents_mngt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Task2 {

	public static void main(String[] args) throws IOException {
		int warmupSize        = Integer.parseInt(args[0]);
		int cacheSizeInBytes  = Integer.parseInt(args[1]);

		LRU LRUCache = new LRU(warmupSize, true, cacheSizeInBytes);
		LFU LFUCache = new LFU(warmupSize, true, cacheSizeInBytes);
		LFU RLFCache = new LFU(warmupSize, true, cacheSizeInBytes);
		
		BufferedReader stdin = new BufferedReader(new FileReader("/Users/Greg/Desktop/trace_tiny.txt"));
		// BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
		String request;
		while((request = stdin.readLine()) != null) {
			String url	= request.split(" ")[0];
			int size 	= Integer.parseInt(request.split(" ")[1]);
			LRUCache.get(url, size);
			LFUCache.get(url, size);
			RLFCache.get(url, size);
		}
		
		stdin.close();
		
		Lib.print("LRU Hit rate:", LRUCache.getHitRate());
		Lib.print("LRU Byte hit rate:", LRUCache.getByteHitRate());
		Lib.print("LFU Hit rate:", LFUCache.getHitRate());
		Lib.print("LFU Byte hit rate:", LFUCache.getByteHitRate());
		Lib.print("Size-based Hit rate:", RLFCache.getHitRate());
		Lib.print("Size-based Hit rate:", RLFCache.getByteHitRate());
		
		Lib.dumpCacheContentToFile(LRUCache.getCacheContent(), "cache_lru.txt");
		Lib.dumpCacheContentToFile(LFUCache.getCacheContent(), "cache_lfu.txt");
		Lib.dumpCacheContentToFile(RLFCache.getCacheContent(), "cache_sized-based.txt");
	}
}
