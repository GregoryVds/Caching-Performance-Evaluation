package cache_contents_mngt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Task1 {

	public static void main(String[] args) throws IOException {
		int warmupSize = Integer.parseInt(args[0]);
		int cacheSize  = Integer.parseInt(args[1]);
		System.out.println("Warmup Size: "+warmupSize);
		System.out.println("Cache Size: "+cacheSize);

		LRU LRUCache = new LRU(warmupSize, false, cacheSize);
		LFU LFUCache = new LFU(warmupSize, false, cacheSize);

		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String request;
		while((request = stdin.readLine()) != null) {
			System.out.println(request.split(" ")[0]);
			System.out.println(request.split(" ")[1]);
			LRUCache.get(request.split(" ")[0], Integer.parseInt(request.split(" ")[1]));
			LFUCache.get(request.split(" ")[0], Integer.parseInt(request.split(" ")[1]));
		}

		System.out.println("done");
		System.out.println("Hite rate: "+LRUCache.getHitRate());
		System.out.println("Hite rate: "+LRUCache.getHitRate());
	}
}
