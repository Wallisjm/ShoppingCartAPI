package test;

import api.Discount;
import api.Item;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

class TestDiscount {
	

	@Test
	void getDiscount() {
		
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
	void getId() {
		Discount discount1 = new Discount("0001", 0.10, false);
		assertEquals(discount1.getDiscount(),0.10);
	}
	
	@Test
	void getIsExpired() {
		Discount discount1 = new Discount("0001", 0.10, false);
		assertFalse(discount1.getIsExpired());
	}
	
	@Test
	void equals() {
		
		Discount discount1 = new Discount("0001", 0.10, false);
		Discount discount2 = new Discount("0001", 0.10, false);
		
		assertTrue(discount1.equals(discount2));
		Discount discount3 = new Discount("0002", 0.10, false);
		Discount discount4 = new Discount("0001", 0.11, false);
		Discount discount5 = new Discount("0001", 0.10, true);
		
		assertFalse(discount1.equals(discount3));
		assertFalse(discount1.equals(discount4));
		assertFalse(discount1.equals(discount5));
	
	}
	
	@Test
	void exists() {
		assertTrue(Discount.exists("0001"));
		assertFalse(Discount.exists("s"));
	}
	
	

}
