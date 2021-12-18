package test;


import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import api.CartResponse;
import api.ItemView;
import api.Response;

class TestCartResponse {
	
	
	@Test
	void getPrice() {
		ArrayList<String> errors = new ArrayList<String>();
		ArrayList<ItemView> items = new ArrayList<ItemView>();
		
		CartResponse cartResponse = new CartResponse(5.0, items, errors);
		assertEquals(cartResponse.getPrice(), 5.0);
	}
	
	@Test
	void getItems() {
		ArrayList<String> errors = new ArrayList<String>();
		ArrayList<ItemView> items = new ArrayList<ItemView>();
		ArrayList<ItemView> expected = new ArrayList<ItemView>();
		items.add(new ItemView("0001", "Product 1", "First Product", "www.test.com/0001"));
		expected.add(new ItemView("0001", "Product 1", "First Product", "www.test.com/0001"));
		CartResponse cartResponse = new CartResponse(5.0, items, errors);
		assertEquals(cartResponse.getItems(), expected);
	}
	
	@Test
	void equals() {
		ArrayList<String> errors1 = new ArrayList<String>();
		ArrayList<String> errors2 = new ArrayList<String>();
		ArrayList<String> errors3 = new ArrayList<String>();
		errors3.add("Error");
		
		ArrayList<ItemView> items1 = new ArrayList<ItemView>();
		ArrayList<ItemView> items2 = new ArrayList<ItemView>();
		ArrayList<ItemView> items3 = new ArrayList<ItemView>();
		items1.add(new ItemView("0001", "Product 1", "First Product", "www.test.com/0001"));
		items2.add(new ItemView("0001", "Product 1", "First Product", "www.test.com/0001"));
		items3.add(new ItemView("0002", "Product 1", "First Product", "www.test.com/0001"));
		
		CartResponse cartResponse1 = new CartResponse(5.0, items1, errors1);
		CartResponse cartResponse2 = new CartResponse(5.0, items2, errors2);
		assertTrue(cartResponse1.equals(cartResponse2));
		
		CartResponse cartResponse3 = new CartResponse(1.0, items2, errors2);
		assertFalse(cartResponse1.equals(cartResponse3));
		
		CartResponse cartResponse4 = new CartResponse(5.0, items1, errors3);
		assertFalse(cartResponse1.equals(cartResponse4));
	}
	
	

}
