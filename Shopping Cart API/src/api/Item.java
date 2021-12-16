package api;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import com.google.gson.Gson;

public class Item extends ItemView {
	
	private static final String filePath = "Item.json";
	
	private int stock;
	private double price;

	
	public Item(String id, String name, String description, int stock, double price, String picture) {
		super(id, name, description, picture);
		this.stock = stock;
		this.price = price;
	}
	
	
	/*
	 * Checks if item is in stock
	 * 
	 * Returns boolean if in stock or not.
	 */
	public boolean inStock() {
		if (this.stock != 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * Updates Stock as much as possible with out being negative
	 * change: Integer
	 * 
	 * Returns amount stock was changed by 
	 */
	public int updateStock(int change) {
		if (stock + change >= 0) {
			stock += change;
			return change;
		} else {
			change = stock;
			stock = 0;
			return -change;
		}
	}
	
	/*
	 * Retrieves Item by Id.
	 * Id: String
	 * 
	 * Returns Item or Null if item does not exist
	 */
	public static Item getItem(String id) {
		File file = new File(filePath);
		try {
			Scanner sc = new Scanner(file);
			Item[] items = new Gson().fromJson(sc.nextLine(), Item[].class);
			sc.close();
			for (int i = 0; i < items.length; i++) {
				if (items[i].getId().equals(id)) {
					return items[i];
				}
			}
			
			return null;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * Saves Existing Item to File
	 * item: Item
	 */
	public static void save(Item item) {
		File file = new File(filePath);
		try {
			Scanner sc = new Scanner(file);
			Item[] items = new Gson().fromJson(sc.nextLine(), Item[].class);
			sc.close();
			for (int i = 0; i < items.length; i++) {
				if (items[i].getId().equals(item.getId())) {
					items[i] = item;
				}
			}
			String gsonItem = new Gson().toJson(items);
			System.out.println(gsonItem);
			
			FileWriter wr = new FileWriter(file);
			wr.write(gsonItem);
			wr.flush();
			wr.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Gets Stock
	 * 
	 * Returns Stock
	 */
	public int getStock() {
		return stock;
	}
	
	/*
	 * Gets Price
	 * 
	 * Returns Price
	 */
	public double getPrice() {
		return price;
	}
	
	/*
	 * Gets a copy of the ItemView of this Item
	 * 
	 * Returns ItemView
	 */
	public ItemView getItemView() {
		return new ItemView(getId(), getName(), getDescription(), getPicture());
	}
	
	
	/*
	 * Checks if two Items are equal
	 * 
	 * Returns True if equal and False if not
	 */
	@Override
	public boolean equals(Object obj) {
		Item item;
		if (obj instanceof Item) {
			item = (Item) obj;
		} else {
			return false;
		}
		
		if (!this.getItemView().equals(item.getItemView())) {
			return false;
		}
		
		if (this.getStock() != item.getStock()) {
			return false;
		}
		
		if (this.getPrice() != item.getPrice()) {
			return false;
		}
		

		
		return true;
	
	}
	
	/*
	 * Checks if an Item are exists
	 * 
	 * Returns True if exists and False if not
	 */
	public static boolean exists(String id) {
		if (Item.getItem(id)  == null) {
			return false;
		}
		return true;
	}
	
}
