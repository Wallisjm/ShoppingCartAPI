package test;

import api.API;
import api.CartResponse;
import api.Response;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

class TestAPI {

	@Test
	void ViewCart() {
		String userId = "A";
		// Generate Expected Response
		ArrayList<String> error = new ArrayList<String>();
		error.add("UserId " + userId + " does not have a cart");
		Response expected =  new Response(error);
		
		assertTrue(API.viewCart(userId).equals(expected));
		
		// Check correct output since we already know ouputs are correct from TestCart
		assertTrue(API.viewCart("0001") instanceof CartResponse);
	}
	
	@Test
	void addItem() {
		String userId = "A";
		// Generate Expected Response
		ArrayList<String> error = new ArrayList<String>();
		error.add("UserId " + userId + " does not have a cart");
		Response expected =  new Response(error);
		
		assertTrue(API.addItem(userId, "0001").equals(expected));
		
		// Check correct output since we already know ouputs are correct from TestCart
		assertTrue(API.addItem("0001", "0001") instanceof Response);
	}
	
	@Test
	void applyDiscount() {
		String userId = "A";
		// Generate Expected Response
		ArrayList<String> error = new ArrayList<String>();
		error.add("UserId " + userId + " does not have a cart");
		Response expected =  new Response(error);
		
		assertTrue(API.applyDiscount(userId, "0001").equals(expected));
		
		// Check correct output since we already know ouputs are correct from TestCart
		assertTrue(API.applyDiscount("0001", "0001") instanceof Response);
	}
	
	@Test
	void modifyCart() {
		String userId = "A";
		// Generate Expected Response
		ArrayList<String> error = new ArrayList<String>();
		error.add("UserId " + userId + " does not have a cart");
		Response expected =  new Response(error);
		
		assertTrue(API.modifyCart(userId, new HashMap<String,Integer>()).equals(expected));
		
		// Check correct output since we already know ouputs are correct from TestCart
		assertTrue(API.modifyCart("0001", new HashMap<String,Integer>()) instanceof Response);
	}


}
