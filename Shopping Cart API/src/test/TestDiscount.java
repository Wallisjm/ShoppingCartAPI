package test;

import api.Discount;
import api.Item;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

class TestDiscount {
	

	@Test
	void LoadDiscount() {
		
		String id = "0001";
		double discount = 0.15;
		boolean isExpired = false;
		
		Discount discount1 = new Discount(id, discount, isExpired);
		System.out.println(new Gson().toJson(discount1));
				
		// Get Discount
		Discount discount2 = Discount.getDiscount(id);
		
		//AssertEquals
		assertTrue(discount1.equals(discount2));
	}
	
	@Test
	void equals() {
		
		String id = "0001";
		double discount = 0.12;
		boolean isExpired = false;
		
		Discount discount1 = new Discount(id, discount, isExpired);
		Discount discount2 = new Discount("0001", 0.12, false);
		
		assertTrue(discount1.equals(discount2));
		
		Discount discount3 = new Discount("0001", 0.12, true);
		assertFalse(discount2.equals(discount3));
	
	}
	
	
	/*
	@Test
	void saveCart() {
		LocalDateTime[] failedDiscounts = {LocalDateTime.now(), LocalDateTime.now().minusHours(1),
				LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(4)};
		State customerState = State.TEXAS;
		ArrayList<String> items = new ArrayList<String>();
		items.add("itemId1");
		items.add("itemId2");
		ArrayList<String> discounts = new ArrayList<String>();
		discounts.add("discountId1");
		discounts.add("discountId2");
	
		Cart carts = new Cart("UserId", failedDiscounts, customerState, items, discounts);
		
		File file = new File(filePath);
		FileWriter wr = new FileWriter(file);
		wr.write(gsonCart);
		wr.flush();
		wr.close();
		
		Cart.save(cart);
		assertEquals("A","B");
	}
	*/

}
