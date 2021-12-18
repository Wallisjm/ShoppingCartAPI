package api;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import com.google.gson.Gson;

public class Cart {

	private static final String filePath = "Cart.json";

	private String userId;
	private LocalDateTime[] failedDiscounts;
	private State customerState;
	private ArrayList<String> items;
	private ArrayList<String> discounts;

	public Cart(String userId, LocalDateTime[] failedDiscounts, State customerState, ArrayList<String> items,
			ArrayList<String> discounts) {
		this.userId = userId;
		this.failedDiscounts = failedDiscounts;
		this.customerState = customerState;
		this.items = items;
		this.discounts = discounts;
	}

	/*
	 * Creates CartResponse for Cart
	 * 
	 * Returns CartResponse
	 */
	public CartResponse viewCart() {
		// Create Fields
		double price = 0.0;
		ArrayList<ItemView> itemViews = new ArrayList<ItemView>();
		ArrayList<String> errors = new ArrayList<String>();

		for (String i : items) {
			if (Item.exists(i)) {
				Item item = Item.getItem(i);
				price += item.getPrice();
				itemViews.add(item.getItemView());
			} else {
				errors.add("Item " + i + " in cart does not exist");
			}
		}

		for (String d : discounts) {
			if (Discount.exists(d)) {
				Discount discount = Discount.getDiscount(d);
				if (!discount.getIsExpired()) {
					price = price - (discount.getDiscount() * price);
				} else {
					errors.add("Discount " + d + " applied to the cart has exipired");
				}
			} else {
				errors.add("Discount " + d + " applied to the cart does not exist");
			}
		}

		price = SalesTax.calculateTax(customerState, price);

		return new CartResponse(price, itemViews, errors);
	}

	/*
	 * Adds Item to cart
	 * 
	 * Returns Response
	 */
	public Response addItem(String itemId) {
		ArrayList<String> errors = new ArrayList<String>();

		if (Item.exists(itemId)) {
			Item item = Item.getItem(itemId);
			if (item.inStock()) {
				int change = item.updateStock(-1);
				Item.save(item);
				if (change != -1) {
					errors.add("Item " + itemId + " only had enough stock to add " + -change + " to cart");
				}
				for (int i = -change; i > 0; i--) {
					items.add(itemId);
				}
			} else {
				errors.add("Item " + itemId + " is out of stock and could not be added to cart");
			}
		} else {
			errors.add("Item " + itemId + " does not exist");
		}
		// Save Cart
		Cart.save(this);
		return new Response(errors);
	}

	/*
	 * Adds Discount to cart
	 * 
	 * Returns Response
	 */
	public Response addDiscount(String discountid) {
		ArrayList<String> errors = new ArrayList<String>();

		// Check Use Can Add Discount
		int past24hours = 0;
		int earliestDateIndex = 0;
		for (int i = 0; i < 5; i++) {
			if (failedDiscounts[i] != null) {
				if (failedDiscounts[i].isAfter(LocalDateTime.now().minusHours(24))) {
					past24hours++;
				}
				if (failedDiscounts[i].isBefore(failedDiscounts[earliestDateIndex])) {
					earliestDateIndex = i;
				}
			}
		}
		if (past24hours == 5) {
			Cart.save(this);
			return new Response(errors);
		}

		// Check if Discount Valid
		if (Discount.exists(discountid)) {
			if (Discount.getDiscount(discountid).getIsExpired()) {
				// Discount has expired
				failedDiscounts[earliestDateIndex] = LocalDateTime.now();
				errors.add("Discount " + discountid + " is expired");
			} else if (this.getDiscounts().contains(discountid)) {
				// Discount has already been added
				failedDiscounts[earliestDateIndex] = LocalDateTime.now();
				errors.add("Discount " + discountid + " has already been added");
			} else {
				// Add Valid Discount
				discounts.add(discountid);
			}
		} else {
			failedDiscounts[earliestDateIndex] = LocalDateTime.now();
			errors.add("Discount " + discountid + " does not exist");
		}

		// Add Discount

		Cart.save(this);
		return new Response(errors);
	}

	/*
	 * Modifies Quantity of items in the cart
	 * 
	 * Returns Response
	 */
	public Response modifyCart(HashMap<String, Integer> changes) {
		ArrayList<String> errors = new ArrayList<>();
		for (String itemId : changes.keySet()) {	
			if (!Item.exists(itemId)) {
				errors.add("Item " + itemId + " does not exist");
			} else if (!items.contains(itemId)) {
				errors.add("Item " + itemId + " is not in cart already so its stock can not be modified");
			} else {
				Item item = Item.getItem(itemId);
				int change = changes.get(itemId);

				if (change > 0) {
					if (item.inStock()) {
						int changeInStock = item.updateStock(-change);
						Item.save(item);
						for (int i = 0; i < -changeInStock; i++) {
							items.add(itemId);
						}
						if (-changeInStock != change) {
							errors.add("Item " + itemId + " only had enough stock to add " + -changeInStock + " to cart");
						}
					} else {
						errors.add("Could not add " + itemId + " Because it is not in stock");
					}
				} else if (change < 0) {
					int removed = 0;
					for (int i = 0; i < -change; i++) {
						int index = items.indexOf(itemId);
						if (index != -1) {
							items.remove(index);
							item.updateStock(1);
							Item.save(item);
							removed++;
						} else {
							// Removing too many
							errors.add("Item " + itemId + " could only be removed from the cart " + removed
									+ " time(s) before it was completely removed from the cart");
							break;
						}
					}
				}
			}
		}
		Cart.save(this);
		return new Response(errors);
	}

	/*
	 * Retrieves Cart from Json File cartId: String
	 * 
	 * Returns Cart
	 */
	public static Cart getCart(String cartId) {
		File file = new File(filePath);
		try {
			Scanner sc = new Scanner(file);
			Cart[] carts = new Gson().fromJson(sc.nextLine(), Cart[].class);
			sc.close();
			for (int i = 0; i < carts.length; i++) {
				if (carts[i].getUserId().equals(cartId)) {
					return carts[i];
				}
			}

			return null;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/*
	 * Saves to Json File cart: Cart
	 */
	public static void save(Cart cart) {

		// https://attacomsian.com/blog/gson-read-json-file
		// https://stackabuse.com/java-save-write-string-into-a-file/#files.write
		File file = new File(filePath);
		try {

			Scanner sc = new Scanner(file);
			Cart[] carts = new Gson().fromJson(sc.nextLine(), Cart[].class);
			sc.close();
			for (int i = 0; i < carts.length; i++) {
				if (carts[i].getUserId().equals(cart.getUserId())) {
					carts[i] = cart;
				}
			}
			String gsonCart = new Gson().toJson(carts);

			FileWriter wr = new FileWriter(file);
			wr.write(gsonCart);
			wr.flush();
			wr.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * Gets userId
	 * 
	 * Returns userId
	 */
	public String getUserId() {
		return userId;
	}

	/*
	 * Gets FailedDiscounts
	 * 
	 * Returns failedDiscoutns
	 */
	public LocalDateTime[] getFailedDiscounts() {
		return failedDiscounts;
	}

	/*
	 * Gets Customer State
	 * 
	 * Returns State
	 */
	public State getCustomerState() {
		return customerState;
	}

	/*
	 * Gets Items
	 * 
	 * Returns Items
	 */
	public ArrayList<String> getItems() {
		return items;
	}

	/*
	 * Gets Discounts
	 * 
	 * Returns Discounts
	 */
	public ArrayList<String> getDiscounts() {
		return discounts;
	}

	/*
	 * Test if a Cart Exists id: String
	 * 
	 * Returns true if exists, otherwise false
	 */
	public static boolean exists(String id) {
		if (Cart.getCart(id) == null) {
			return false;
		}
		return true;
	}

	/*
	 * Checks if two Carts are equals obj: Object
	 * 
	 * 
	 * Returns true if equal, otherwise false
	 */
	@Override
	public boolean equals(Object obj) {

		Cart cart;
		if (obj instanceof Cart) {
			cart = (Cart) obj;
		} else {
			return false;
		}

		if (!this.getUserId().equals(cart.getUserId())) {
			return false;
		}

		if (this.getFailedDiscounts().length == cart.getFailedDiscounts().length) {
			for (int i = 0; i < cart.getFailedDiscounts().length; i++) {
				if (!this.getFailedDiscounts()[i].equals(cart.getFailedDiscounts()[i])) {
					return false;
				}
			}
		} else {
			return false;
		}

		if (this.getCustomerState() != cart.getCustomerState()) {
			return false;
		}

		if (!this.getItems().equals(cart.getItems())) {
			return false;
		}

		if (!this.getDiscounts().equals(cart.getDiscounts())) {
			return false;
		}

		return true;
	}
}
