package caches;

public class Request {
	String url;
	int size;
	
	Request(String url, int size){
		this.url = url;
		this.size= size;
	}

	@Override
	public boolean equals(Object O) {
		if (this == O) return true;
		if (!(O instanceof Request)) return false;
		Request that = (Request)O;
		return that.url.equals(this.url) && that.size==this.size;
	}	
}