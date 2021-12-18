package api;

import java.util.ArrayList;

public class Response {
	
	private ArrayList<String> error;
	
	public Response(ArrayList<String> error) {	
		this.error = error;
	}
	
	/*
	 * Checks if a response has Errors
	 * 
	 * Returns true if there are error(s) and false if not
	 */
	public boolean hasError() {
		if (!error.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * Gets the list of errors
	 * 
	 * Returns Errors
	 */
	public ArrayList<String> getError() {
		return error;
	}	
	
	/*
	 * Checks if two Responses are equal
	 * 
	 * Returns true if equal otherwise false
	 */
	public boolean equals(Object obj) {
		Response response;
		if (obj instanceof Response) {
			response = (Response) obj;
		} else {
			return false;
		}
		
		if (!this.getError().equals(response.getError())) {
			return false;
		}
		
		return true;
	}
}