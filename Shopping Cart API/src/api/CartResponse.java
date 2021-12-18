package api;

import java.util.ArrayList;

public class CartResponse extends Response {

	private double price;
	private ArrayList<ItemView> items;
	
	public CartResponse(double price, ArrayList<ItemView> items, ArrayList<String> error) {
		super(error);
		this.price = price;
		this.items = items;
	}

	/*
	 * Gets Price of CartResponse
	 * 
	 * Returns price
	 */
	public double getPrice() {
		return price;
	}
	
	/*
	 * Gets Items in CartResponse
	 * 
	 * Returns Items
	 */
	public ArrayList<ItemView> getItems() {
		return items;
	}
	
	public boolean equals(Object obj) {
		CartResponse cartResponse;
		if (obj instanceof CartResponse) {
			cartResponse = (CartResponse) obj;
		} else {
			return false;
		}
		
		if (this.getPrice() != cartResponse.getPrice()) {
			return false;
		}
		
		if (!this.getItems().equals(cartResponse.getItems())) {
			return false;
		}
		
		if (!this.getError().equals(cartResponse.getError())) {
			return false;
		}
		
		return true;
		
	}
}