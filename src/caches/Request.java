package caches;

public class Request {
	String url;
	int size;
	
	Request(String url, int size){
		this.url = url;
		this.size= size;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Request)) return false;
		Request that = (Request)o;
		return that.url.equals(this.url);
	}
	
	@Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 17 + this.url.hashCode();
        return hash;
    }
}