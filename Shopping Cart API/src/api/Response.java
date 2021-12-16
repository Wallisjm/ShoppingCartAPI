package api;

import java.util.ArrayList;

public class Response {
	
	private ArrayList<String> error;
	
	public Response(ArrayList<String> error) {	
		this.error = error;
	}
	
	public boolean hasError() {
		if (error.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	public ArrayList<String> getError() {
		return error;
	}	
	
	public String toString() {
		return "Error: " + error.toString(); 
	}
	
	public boolean equals(Object obj) {
		Response response;
		if (obj instanceof Response) {
			response = (Response) obj;
		} else {
			return false;
		}
		
		if (!this.error.equals(response.error)) {
			return false;
		}
		
		return true;
	}
}