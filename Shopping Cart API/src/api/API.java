package api;

import java.util.ArrayList;
import java.util.HashMap;

public class API {
	


	public static void main(String[] args) {
	}
	
	/*
	 * Creates CartResponse for Cart
	 * 
	 * Returns CartResponse
	 */
	public static Response viewCart(String userId) {
		if(Cart.exists(userId)) {
			return Cart.getCart(userId).viewCart();
		} else {
			ArrayList<String> error = new ArrayList<String>();
			error.add("UserId " + userId + " does not have a cart");
			return new Response(error);
		}
	}
	
	/*
	 * Adds Item to cart
	 * 
	 * Returns Response
	 */
	public static Response addItem(String userId, String itemId) {
		if(Cart.exists(userId)) {
			return Cart.getCart(userId).addItem(itemId);
		} else {
			ArrayList<String> error = new ArrayList<String>();
			error.add("UserId " + userId + " does not have a cart");
			return new Response(error);
		}
	}
	
	/*
	 * Adds Discount to cart
	 * 
	 * Returns Response
	 */
	public static Response applyDiscount(String userId, String discountID) {
		if(Cart.exists(userId)) {
			return Cart.getCart(userId).addDiscount(discountID);
		} else {
			ArrayList<String> error = new ArrayList<String>();
			error.add("UserId " + userId + " does not have a cart");
			return new Response(error);
		}
	}
	
	/*
	 * Modifies Quantity of items in the cart
	 * 
	 * Returns Response
	 */
	public static Response modifyCart(String userId, HashMap<String,Integer> changes) {
		if(Cart.exists(userId)) {
			return Cart.getCart(userId).modifyCart(changes);
		} else {
			ArrayList<String> error = new ArrayList<String>();
			error.add("UserId " + userId + " does not have a cart");
			return new Response(error);
		}
	}
	


}

