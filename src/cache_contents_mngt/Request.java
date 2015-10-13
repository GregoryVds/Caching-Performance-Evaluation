package cache_contents_mngt;

public class Request {
	String url;
	int size;
	
	Request(String url, int size){
		this.url = url;
		this.size= size;
	}
	
	public int getSize(){
		return this.size;
	}
	
	public String getUrl(){
		return this.url;
	}
	
	
}
