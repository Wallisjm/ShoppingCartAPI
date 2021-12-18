package test;

import api.Cart;
import api.CartResponse;
import api.Item;
import api.ItemView;
import api.Response;
import api.State;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import org.junit.jupiter.api.Test;

class TestCart {

	@Test
	void viewCart() {
		// Configure Item 1
		String id = "0001";
		String name = "Product 1";
		String description = "First Product";
		int stock = 10;
		double price = 99.99;
		String picture = "www.image.com/0001";
		Item item1 = new Item(id, name, description, stock, price, picture);
		Item.save(item1);
		
		// Create Cart
		LocalDateTime[] failedDiscounts = {LocalDateTime.now(), LocalDateTime.now().minusHours(1),
				LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(4)};
		ArrayList<String> items = new ArrayList<String>();
		items.add("0001");
		items.add("0001");
		items.add("0001");
		items.add("0002");
		items.add("0002");
		items.add("0004");
		ArrayList<String> discounts = new ArrayList<String>();
		discounts.add("0003"); // Expired
		discounts.add("0005"); // Not Expired
		Cart cart = new Cart("1111", failedDiscounts, State.TEXAS, items, discounts);
		
		// Build Expected Result
		price = 297.46599999999995;
		ArrayList<ItemView> itemViews = new ArrayList<>();
		//String id, String name, String description, String picture
		itemViews.add(new ItemView("0001", "Product 1", "First Product", "www.image.com/0001"));
		itemViews.add(new ItemView("0001", "Product 1", "First Product", "www.image.com/0001"));
		itemViews.add(new ItemView("0001", "Product 1", "First Product", "www.image.com/0001"));
		itemViews.add(new ItemView("0002", "Product 2", "Second Product", "www.image.com/0002"));
		itemViews.add(new ItemView("0002", "Product 2", "Second Product", "www.image.com/0002"));
		itemViews.add(new ItemView("0004", "Product 4", "Fourth Product", "www.image.com/0004"));
		ArrayList<String> errors = new ArrayList<>();
		errors.add("Discount 0003 applied to the cart has exipired");
		CartResponse expected = new CartResponse(price, itemViews, errors);
		
		// Check
		assertEquals(cart.viewCart(),(expected));
	}
	
	
	@Test 
	void addItem() {
		// Set Items Attributes
		String id = "0001";
		String name = "Product 1";
		String description = "First Product";
		int stock = 10;
		double price = 99.99;
		String picture = "www.image.com/0001";
		Item item1 = new Item(id, name, description, stock, price, picture);
		Item.save(item1);
		
		
		// Create Cart
		LocalDateTime[] failedDiscounts = {LocalDateTime.now(), LocalDateTime.now().minusHours(1),
				LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(4)};
		ArrayList<String> items = new ArrayList<String>();
		items.add("0001");
		ArrayList<String> discounts = new ArrayList<String>();
		Cart cart = new Cart("0001", failedDiscounts, State.TEXAS, items, discounts);
		Cart.save(cart);
		cart = Cart.getCart("0001");
		Response response = cart.addItem("0001");
		
		// Create Expected Outcome:
		ArrayList<ItemView> itemViews = new ArrayList<>();
		//String id, String name, String description, String picture
		itemViews.add(new ItemView("0001", "Product 1", "First Product", "www.image.com/0001"));
		itemViews.add(new ItemView("0001", "Product 1", "First Product", "www.image.com/0001"));
		price = 212.47875;		
		ArrayList<String> errors = new ArrayList<>();
		CartResponse expected = new CartResponse(price, itemViews, errors);
		
		// Test Added
		cart = Cart.getCart("0001");
		assertTrue(cart.viewCart().equals(expected));
		assertTrue(response.getError().isEmpty());
		assertEquals(Item.getItem("0001").getStock(), 9);
		
		// Test One Stock
		items.clear();
		item1 = new Item(id, name, description, 1, 99.99, picture);
		Item.save(item1);
		cart = new Cart("0001", failedDiscounts, State.TEXAS, items, discounts);
		Cart.save(cart);
		cart = Cart.getCart("0001");
		
		response = cart.addItem("0001");
		itemViews = new ArrayList<>();
		//String id, String name, String description, String picture
		itemViews.add(new ItemView("0001", "Product 1", "First Product", "www.image.com/0001"));
		expected = new CartResponse(106.239375, itemViews, errors);
		cart = Cart.getCart("0001");
		assertTrue(cart.viewCart().equals(expected));
		assertTrue(response.getError().isEmpty());
		assertEquals(0, Item.getItem("0001").getStock());
		
		// Test No Stock
		items.clear();
		item1 = new Item(id, name, description, 0, 99.99, picture);
		Item.save(item1);
		cart = new Cart("0001", failedDiscounts, State.TEXAS, items, discounts);
		Cart.save(cart);
		cart = Cart.getCart("0001");
		response = cart.addItem("0001");
		itemViews = new ArrayList<>();
		expected = new CartResponse(0, itemViews, errors);
		cart = Cart.getCart("0001");
		assertTrue(cart.viewCart().equals(expected));
		errors.add("Item 0001 is out of stock and could not be added to cart");
		assertEquals(errors, response.getError());
		assertEquals(0, Item.getItem("0001").getStock());
		
		// Test Item doesnt Exist
		errors.clear();
		response = cart.addItem("AAAA");
		cart = Cart.getCart("0001");
		assertTrue(cart.viewCart().equals(expected));
		errors.add("Item AAAA does not exist");
		assertEquals(errors, response.getError());
		assertEquals(0, Item.getItem("0001").getStock());
		
	}
	
	@Test 
	void addDiscount() {
		// Configure Cart
		LocalDateTime[] failedDiscounts = {LocalDateTime.now().minusHours(7), LocalDateTime.now().minusHours(1),
				LocalDateTime.now().minusHours(49), LocalDateTime.now().minusHours(25), LocalDateTime.now().minusHours(4)};
		ArrayList<String> items = new ArrayList<String>();
		items.add("0001");
		ArrayList<String> discounts = new ArrayList<String>();
		Cart cart = new Cart("0001", failedDiscounts, State.TEXAS, items, discounts);
		Cart.save(cart);
		cart = Cart.getCart("0001");
		
		// Add Valid Discount
		Response response = cart.addDiscount("0005");
		assertFalse(response.hasError());
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("0005");
		cart = Cart.getCart("0001");
		assertEquals(expected,cart.getDiscounts());
		
		// Add Discount already applied
		cart = Cart.getCart("0001");
		response = cart.addDiscount("0005");
		ArrayList<String> errorExpected = new ArrayList<String>();
		errorExpected.add("Discount 0005 has already been added");
		assertEquals(errorExpected,response.getError());
		expected = new ArrayList<String>();
		expected.add("0005");
		cart = Cart.getCart("0001");
		assertEquals(expected,cart.getDiscounts());		
		
		// Add non existing discount
		cart = Cart.getCart("0001");
		response = cart.addDiscount("AAAA");
		errorExpected = new ArrayList<String>();
		errorExpected.add("Discount AAAA does not exist");
		assertEquals(errorExpected,response.getError());
		expected = new ArrayList<String>();
		expected.add("0005");
		cart = Cart.getCart("0001");
		assertEquals(expected,cart.getDiscounts());
		
		// Too many bad discounts in 24 hours
		cart = Cart.getCart("0001");
		response = cart.addDiscount("0005");
		cart = Cart.getCart("0001");
		assertFalse(response.hasError());
		expected = new ArrayList<String>();
		expected.add("0005");
		cart = Cart.getCart("0001");
		assertEquals(expected,cart.getDiscounts());
		
	}
	
	@Test
	void modifyCart() {
		// Configure Cart
		LocalDateTime[] failedDiscounts = {LocalDateTime.now().minusHours(7), LocalDateTime.now().minusHours(1),
				LocalDateTime.now().minusHours(49), LocalDateTime.now().minusHours(25), LocalDateTime.now().minusHours(4)};
		ArrayList<String> items = new ArrayList<String>();
		items.add("0001");
		items.add("0005");
		items.add("0005");
		ArrayList<String> discounts = new ArrayList<String>();
		Cart cart = new Cart("0001", failedDiscounts, State.TEXAS, items, discounts);
		Cart.save(cart);
		cart = Cart.getCart("0001");
		
		// Set Items Attributes
		String id = "0001";
		String name = "Product 1";
		String description = "First Product";
		int stock = 2;
		double price = 99.99;
		String picture = "www.image.com/0001";
		Item item1 = new Item(id, name, description, stock, price, picture);
		Item.save(item1);
		Item item5 = new Item("0005","Product 5", "Fifth Product", 10, 20.00, "www.image.com/0005");
		Item.save(item5);
		
		item1 = Item.getItem("0001");
		item5 = Item.getItem("0005");
		
		// Test Add 2 Items to
		HashMap<String, Integer> changes = new HashMap<String, Integer>();
		changes.put("0001", 2);
		changes.put("0005", -2);
		Response response = cart.modifyCart(changes);
		assertFalse(response.hasError());
		assertEquals(0, Item.getItem("0001").getStock());
		assertEquals(12, Item.getItem("0005").getStock());
		cart = Cart.getCart("0001");
		ArrayList<String> expectedItems = new ArrayList<>();
		expectedItems.add("0001");
		expectedItems.add("0001");
		expectedItems.add("0001");
		assertEquals(expectedItems,cart.getItems());
		
		// Test Add Item not in Cart
		HashMap<String, Integer> changes2 = new HashMap<String, Integer>();
		changes2.put("0003", 2);
		ArrayList<String> expectedError = new ArrayList<>();
		expectedError.add("Item 0003 is not in cart already so its stock can not be modified");
		response = cart.modifyCart(changes2);
		cart = Cart.getCart("0001");
		assertEquals(expectedError, response.getError());
		assertEquals(expectedItems,cart.getItems());
		
		// Add out of stock item:
		HashMap<String, Integer> changes3 = new HashMap<String, Integer>();
		changes3.put("0001", 2);
		expectedError.clear();
		expectedError.add("Could not add 0001 Because it is not in stock");
		response = cart.modifyCart(changes3);
		cart = Cart.getCart("0001");
		assertEquals(expectedError, response.getError());
		assertEquals(expectedItems,cart.getItems());
		
		// Add out of until out of stock:
		item1 = new Item("0001", name, description, 1, price, picture);
		Item.save(item1);
		HashMap<String, Integer> changes4 = new HashMap<String, Integer>();
		changes4.put("0001", 2);
		expectedItems.add("0001");
		expectedError.clear();
		expectedError.add("Item 0001 only had enough stock to add 1 to cart");
		response = cart.modifyCart(changes4);
		cart = Cart.getCart("0001");
		assertEquals(expectedError, response.getError());
		assertEquals(expectedItems,cart.getItems());
		
		// Add out of until out of stock:
		HashMap<String, Integer> changes5 = new HashMap<String, Integer>();
		changes5.put("0001", -6);
		expectedError.clear();
		expectedItems.clear();
		expectedError.add("Item 0001 could only be removed from the cart 4 time(s) before it was completely removed from the cart");
		response = cart.modifyCart(changes5);
		cart = Cart.getCart("0001");
		assertEquals(expectedError, response.getError());
		assertEquals(expectedItems,cart.getItems());
	}
	
	@Test
	void saveAndLoadCart() {
		Random rand = new Random();
		
		LocalDateTime[] failedDiscounts = {LocalDateTime.now(), LocalDateTime.now().minusHours(rand.nextInt()),
				LocalDateTime.now().minusHours(rand.nextInt()), LocalDateTime.now().minusHours(rand.nextInt()), LocalDateTime.now().minusHours(rand.nextInt())};
		State customerState = State.TEXAS;
		ArrayList<String> items = new ArrayList<String>();
		items.add("0001");
		items.add("0002");
		ArrayList<String> discounts = new ArrayList<String>();
		discounts.add("0003");
		discounts.add("0004");
		
		Cart cart = new Cart("0001", failedDiscounts, customerState, items, discounts);
		
		// Save Cart
		Cart.save(cart);
		
		// Get Cart
		Cart cart2 = Cart.getCart("0001");
		
		//AssertEquals
		assertTrue(cart.equals(cart2));
		
		// Reset Time to be normal
		LocalDateTime[] failedDiscounts2 = {LocalDateTime.now(), LocalDateTime.now().minusHours(1),
				LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(4)};
		cart = new Cart("0001", failedDiscounts2, customerState, items, discounts);
		Cart.save(cart);
	}
	
	@Test
	void getUserId() {
		LocalDateTime[] failedDiscounts = {LocalDateTime.now(), LocalDateTime.now().minusHours(1),
				LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(4)};
		State customerState = State.TEXAS;
		ArrayList<String> items = new ArrayList<String>();
		items.add("itemId1");
		items.add("itemId2");
		ArrayList<String> discounts = new ArrayList<String>();
		discounts.add("discountId1");
		discounts.add("discountId2");
		Cart cart1 = new Cart("UserId", failedDiscounts, customerState, items, discounts);
		
		assertEquals(cart1.getUserId(), "UserId");
	}
	
	@Test
	void getFailedDiscounts() {
		LocalDateTime[] failedDiscounts = {LocalDateTime.now(), LocalDateTime.now().minusHours(1),
				LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(4)};
		State customerState = State.TEXAS;
		ArrayList<String> items = new ArrayList<String>();
		items.add("itemId1");
		items.add("itemId2");
		ArrayList<String> discounts = new ArrayList<String>();
		discounts.add("discountId1");
		discounts.add("discountId2");
		Cart cart1 = new Cart("UserId", failedDiscounts, customerState, items, discounts);
		
		assertEquals(cart1.getFailedDiscounts(), failedDiscounts);
	}
	
	
	@Test
	void getCustomerState() {
		LocalDateTime[] failedDiscounts = {LocalDateTime.now(), LocalDateTime.now().minusHours(1),
				LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(4)};
		State customerState = State.TEXAS;
		ArrayList<String> items = new ArrayList<String>();
		items.add("itemId1");
		items.add("itemId2");
		ArrayList<String> discounts = new ArrayList<String>();
		discounts.add("discountId1");
		discounts.add("discountId2");
		Cart cart1 = new Cart("UserId", failedDiscounts, customerState, items, discounts);
		
		assertEquals(cart1.getCustomerState(), customerState);
	}
	
	@Test
	void getItems() {
		LocalDateTime[] failedDiscounts = {LocalDateTime.now(), LocalDateTime.now().minusHours(1),
				LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(4)};
		State customerState = State.TEXAS;
		ArrayList<String> items = new ArrayList<String>();
		items.add("itemId1");
		items.add("itemId2");
		ArrayList<String> discounts = new ArrayList<String>();
		discounts.add("discountId1");
		discounts.add("discountId2");
		Cart cart1 = new Cart("UserId", failedDiscounts, customerState, items, discounts);
		
		assertEquals(cart1.getItems(), items);
	}
	
	@Test
	void getDiscount() {
		LocalDateTime[] failedDiscounts = {LocalDateTime.now(), LocalDateTime.now().minusHours(1),
				LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(4)};
		State customerState = State.TEXAS;
		ArrayList<String> items = new ArrayList<String>();
		items.add("itemId1");
		items.add("itemId2");
		ArrayList<String> discounts = new ArrayList<String>();
		discounts.add("discountId1");
		discounts.add("discountId2");
		Cart cart1 = new Cart("UserId", failedDiscounts, customerState, items, discounts);
		
		assertEquals(cart1.getDiscounts(), discounts);
	}
	
	@Test
	void exists() {
		assertTrue(Cart.exists("0001"));
		assertFalse(Cart.exists("A"));
	}
	
	@Test
	void equals() {
		
		
		LocalDateTime[] failedDiscounts = {LocalDateTime.now(), LocalDateTime.now().minusHours(1),
				LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(4)};
		LocalDateTime[] failedDiscounts2 = {LocalDateTime.now(), LocalDateTime.now().minusHours(2),
				LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(4), LocalDateTime.now().minusHours(5)};
		
		State customerState = State.TEXAS;
		State customerState2 = State.INDIANA;
		
		ArrayList<String> items = new ArrayList<String>();
		items.add("itemId1");
		items.add("itemId2");
		ArrayList<String> items2 = new ArrayList<String>();
		items.add("itemId3");
		items.add("itemId4");
		
		ArrayList<String> discounts = new ArrayList<String>();
		discounts.add("discountId1");
		discounts.add("discountId2");
		ArrayList<String> discounts2 = new ArrayList<String>();
		discounts.add("discountId3");
		discounts.add("discountId4");
		
		Cart cart1 = new Cart("UserId", failedDiscounts, customerState, items, discounts);
		Cart cart2 = new Cart("UserId", failedDiscounts, customerState, items, discounts);
		assertTrue(cart1.equals(cart2));
		
		Cart cart3 = new Cart("UserId2", failedDiscounts, customerState, items, discounts); 
		assertFalse(cart1.equals(cart3));
		
		cart3 = new Cart("UserId", failedDiscounts2, customerState, items, discounts); 
		assertFalse(cart1.equals(cart3));
		
		cart3 = new Cart("UserId", failedDiscounts, customerState2, items, discounts); 
		assertFalse(cart1.equals(cart3));
		
		cart3 = new Cart("UserId", failedDiscounts, customerState, items2, discounts); 
		assertFalse(cart1.equals(cart3));
		
		cart3 = new Cart("UserId", failedDiscounts, customerState, items, discounts2); 
		assertFalse(cart1.equals(cart3));
	}


}
