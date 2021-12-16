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
	
	public Cart(String userId, LocalDateTime[] failedDiscounts, State customerState, ArrayList<String> items, ArrayList<String> discounts) {
		this.userId = userId;
		this.failedDiscounts = failedDiscounts;
		this.customerState = customerState;
		this.items = items;
		this.discounts = discounts;
	}
	
	//TODO: Implement
	public CartResponse viewCart() {
		// Get Items 
		
		return null;
	}

	//TODO: Implement
	public double totalCost() {
		return 0.0;
	}
	
	//TODO: Implement
	public Response addItem(String itemId) {
		return null;
	}
	
	//TODO: Implement
	public Response addDiscount(String discountid) {
		return null;
	}
	
	//TODO: Implement
	public Response modifyCart(HashMap<String,Integer> changes) {
		return null;
	}
	
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
			System.out.println(gsonCart);
			
			FileWriter wr = new FileWriter(file);
			wr.write(gsonCart);
			wr.flush();
			wr.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
	public String getUserId() {
		return userId;
	}
	
	public LocalDateTime[] getFailedDiscounts() {
		return failedDiscounts;
	}
	
	public State getCustomerState() {
		return customerState;
	}
	
	public ArrayList<String> getItems() {
		return items;
	}
	
	public ArrayList<String> getDiscounts() {
		return discounts;
	}

	public static boolean exists(String id) {
		if (Cart.getCart(id)  == null) {
			return false;
		}
		return true;
	}
	
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
		
		if (this.getCustomerState() != this.getCustomerState()) {
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
