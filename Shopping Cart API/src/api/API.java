package api;

import java.util.ArrayList;
import java.util.HashMap;

public class API {
	


	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	
	
	public static Response viewCart(String UserId) {
		if(Cart.exists(UserId)) {
			return Cart.getCart(UserId).viewCart();
		} else {
			ArrayList<String> error = new ArrayList<String>();
			error.add("UserId " + UserId + " does not have a cart");
			return new Response(error);
		}
	}
	
	public static Response addItem(String UserId, String ItemId) {
		if(Cart.exists(UserId)) {
			return Cart.getCart(UserId).viewCart();
		} else {
			ArrayList<String> error = new ArrayList<String>();
			return new Response(error);
		}
	}
	
	public static Response applyDiscount(String UserId, String ItemId) {
		return null;
	}
	
	public static Response modifyCart(String userId, HashMap<String,Integer> changes) {
		return null;
	}
	


}

