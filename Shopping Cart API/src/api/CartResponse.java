package api;

import java.util.ArrayList;

public class CartResponse extends Response {

	private double price;
	private ArrayList<ItemView> items;
	
	CartResponse(double price, ArrayList<ItemView> items, ArrayList<String> error) {
		super(error);
		this.price = price;
		this.items = items;
	}

	public double getPrice() {
		return price;
	}
	
	public ArrayList<ItemView> getItems() {
		return items;
	}
	
}