package test;


import api.ItemView;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TestItemView {
	
	
	@Test
	void getId() {
		ItemView ItemView1 = new ItemView("0001", "Product 1", "First Product", "www.test.com/0001");
		assertEquals(ItemView1.getId(), "0001");
	}
	
	@Test
	void getName() {
		ItemView ItemView1 = new ItemView("0001", "Product 1", "First Product", "www.test.com/0001");
		assertEquals(ItemView1.getName(), "Product 1");
	}
	
	@Test
	void getDescription() {
		ItemView ItemView1 = new ItemView("0001", "Product 1", "First Product", "www.test.com/0001");
		assertEquals(ItemView1.getDescription(), "First Product");
	}
	
	@Test
	void getPicture() {
		ItemView ItemView1 = new ItemView("0001", "Product 1", "First Product", "www.test.com/0001");
		assertEquals(ItemView1.getPicture(), "www.test.com/0001");
	}
	
	@Test
	void equals() {
		ItemView ItemView1 = new ItemView("0001", "Product 1", "First Product", "www.test.com/0001");
		ItemView ItemView2 = new ItemView("0001", "Product 1", "First Product", "www.test.com/0001");
		
		assertTrue(ItemView1.equals(ItemView2));
		
		ItemView ItemView3 = new ItemView("0002", "Product 1", "First Project", "www.test.com/0001");
		ItemView ItemView4 = new ItemView("0001", "Product 2", "First Project", "www.test.com/0001");
		ItemView ItemView5 = new ItemView("0001", "Product 1", "Second Project", "www.test.com/0001");
		ItemView ItemView6 = new ItemView("0001", "Product 1", "First Product", "www.test.com/0002");
		assertFalse(ItemView1.equals(ItemView3));
		assertFalse(ItemView1.equals(ItemView4));
		assertFalse(ItemView1.equals(ItemView5));
		assertFalse(ItemView1.equals(ItemView6));
	}
	


}
