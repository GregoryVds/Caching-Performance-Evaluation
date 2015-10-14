package caches;

public class RequestLFU extends Request implements Comparable<RequestLFU> {
	
	
	int frequency; 			//Counter for the frequency of the request 
	
	RequestLFU(String url, int size) {
		super(url, size);
		this.frequency = 1;
	
	}

	public void incrementFrequency(){
		this.frequency++;
	}
	
	@Override
	public int compareTo(RequestLFU rqst) {
		return rqst.frequency - this.frequency;  
	}

}
