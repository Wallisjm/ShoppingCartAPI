package test;

import api.Cart;
import api.State;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import org.junit.jupiter.api.Test;

class TestCart {

	@Test
	void saveAndLoadCart() {
		Random rand = new Random();
		
		LocalDateTime[] failedDiscounts = {LocalDateTime.now(), LocalDateTime.now().minusHours(rand.nextInt()),
				LocalDateTime.now().minusHours(rand.nextInt()), LocalDateTime.now().minusHours(rand.nextInt()), LocalDateTime.now().minusHours(rand.nextInt())};
		State customerState = State.TEXAS;
		ArrayList<String> items = new ArrayList<String>();
		items.add("itemId1");
		items.add("itemId2");
		ArrayList<String> discounts = new ArrayList<String>();
		discounts.add("discountId1");
		discounts.add("discountId2");
		
		Cart cart = new Cart("0001", failedDiscounts, customerState, items, discounts);
		
		// Save Cart
		Cart.save(cart);
		
		// Get Cart
		Cart cart2 = Cart.getCart("0001");
		
		//AssertEquals
		assertTrue(cart.equals(cart2));
	}
	
	@Test
	void equals() {
		Random rand = new Random();
		
		LocalDateTime[] failedDiscounts = {LocalDateTime.now(), LocalDateTime.now().minusHours(rand.nextInt()),
				LocalDateTime.now().minusHours(rand.nextInt()), LocalDateTime.now().minusHours(rand.nextInt()), LocalDateTime.now().minusHours(rand.nextInt())};
		State customerState = State.TEXAS;
		ArrayList<String> items = new ArrayList<String>();
		items.add("itemId1");
		items.add("itemId2");
		ArrayList<String> discounts = new ArrayList<String>();
		discounts.add("discountId1");
		discounts.add("discountId2");
		
		Cart cart1 = new Cart("UserId", failedDiscounts, customerState, items, discounts);
		Cart cart2 = new Cart("UserId", failedDiscounts, customerState, items, discounts);
		assertTrue(cart1.equals(cart2));
		
		LocalDateTime[] failedDiscounts2 = {LocalDateTime.now(), LocalDateTime.now().minusHours(rand.nextInt()),
				LocalDateTime.now().minusHours(rand.nextInt()), LocalDateTime.now().minusHours(rand.nextInt()), LocalDateTime.now().minusHours(rand.nextInt())};
		
		Cart cart3 = new Cart("UserId", failedDiscounts2, customerState, items, discounts); 
		assertFalse(cart1.equals(cart3));
		
		Cart cart4 = new Cart(null, null, null, null, null);
		assertFalse(cart1.equals(cart4));
		
	}

	void viewCart() {
		// Save Correct Cart
		LocalDateTime[] failedDiscounts = {LocalDateTime.now(), LocalDateTime.now().minusHours(1),
				LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(4)};
		State customerState = State.INDIANA;
		ArrayList<String> items = new ArrayList<String>();
		items.add("0001");
		items.add("0002");
		ArrayList<String> discounts = new ArrayList<String>();
		discounts.add("0002");
		Cart cart1 = new Cart("UserId", failedDiscounts, customerState, items, discounts);
		
		
		// Generate Expected resuls
	}

}
