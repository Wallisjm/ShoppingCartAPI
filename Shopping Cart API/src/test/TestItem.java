package test;

import api.Item;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TestItem {

	@Test
	void inStock() {
		Item item1 = new Item("0001", "N1", "D1", 0, 2.50, "P1");
		assertFalse(item1.inStock());

		item1 = new Item("0001", "N1", "D1", 1, 2.50, "P1");
		assertTrue(item1.inStock());

	}

	@Test
	void updateStock() {
		Item item1 = new Item("0001", "N1", "D1", 0, 2.50, "P1");
		int change = item1.updateStock(-1);
		assertEquals(change, 0);
		assertEquals(item1.getStock(), 0);

		item1 = new Item("0001", "N1", "D1", 10, 2.50, "P1");
		change = item1.updateStock(-5);
		assertEquals(change, -5);
		assertEquals(item1.getStock(), 5);

		item1 = new Item("0001", "N1", "D1", 10, 2.50, "P1");
		change = item1.updateStock(-15);
		assertEquals(change, -10);
		assertEquals(item1.getStock(), 0);

		item1 = new Item("0001", "N1", "D1", 10, 2.50, "P1");
		change = item1.updateStock(15);
		assertEquals(change, 15);
		assertEquals(item1.getStock(), 25);

	}

	@Test
	void saveAndGetItem() {

		String id = "0001";
		String name = "Product 1";
		String description = "First Product";
		int stock = 10;
		double price = 99.99;
		String picture = "www.image.com/0001";

		Item item1 = new Item(id, name, description, stock, price, picture);

		// Save Cart
		Item.save(item1);

		// Get Cart
		Item item2 = Item.getItem("0001");

		// AssertEquals
		assertTrue(item1.equals(item2));
	}

	@Test
	void getStock() {
		Item item1 = new Item("0001", "N1", "D1", 1, 2.50, "P1");
		assertTrue(item1.getStock() == 1);
	}

	@Test
	void getPrice() {
		Item item1 = new Item("0001", "N1", "D1", 1, 2.50, "P1");
		assertTrue(item1.getPrice() == 2.50);
	}

	@Test
	void equals() {
		Item item1 = new Item("0001", "N1", "D1", 1, 2.50, "P1");
		Item item2 = new Item("0001", "N1", "D1", 1, 2.50, "P1");
		assertTrue(item1.equals(item2));

		Item item3 = new Item("0002", "N1", "D1", 1, 2.50, "P1");
		Item item4 = new Item("0001", "N2", "D1", 1, 2.50, "P1");
		Item item5 = new Item("0001", "N1", "D2", 1, 2.50, "P1");
		Item item6 = new Item("0001", "N1", "D1", 2, 1.50, "P1");
		Item item7 = new Item("0001", "N1", "D1", 1, 2.50, "P2");
		assertFalse(item1.equals(item3));
		assertFalse(item1.equals(item4));
		assertFalse(item1.equals(item5));
		assertFalse(item1.equals(item6));
		assertFalse(item1.equals(item7));

	}

	@Test
	void exists() {
		assertTrue(Item.exists("0001"));
		assertFalse(Item.exists("s"));
	}

}
