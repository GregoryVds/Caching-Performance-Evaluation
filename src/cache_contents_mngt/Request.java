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

	@Override
	public boolean equals(Object O) {
		if (this == O) return true;
		if (!(O instanceof Request)) return false;
		Request that = (Request)O;
		return that.getUrl().equals(this.url) && that.getSize()==this.size;
	}	
}