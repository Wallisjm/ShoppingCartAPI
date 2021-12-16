package api;

import java.io.File;
import java.util.Scanner;
import com.google.gson.Gson;

public class Discount {

	private static final String filePath = "Discount.json";

	private String id;
	private double discount;
	private boolean isExpired;

	public Discount(String id, double discount, boolean isExpired) {
		this.id = id;
		this.discount = discount;
		this.isExpired = isExpired;
	}

	/*
	 * Retrieves Discount by Id.
	 * Id: String
	 * 
	 * Returns Discount or Null if item does not exist
	 */
	public static Discount getDiscount(String id) {
		File file = new File(filePath);
		try {
			Scanner sc = new Scanner(file);
			Discount[] discounts = new Gson().fromJson(sc.nextLine(), Discount[].class);
			sc.close();
			for (int i = 0; i < discounts.length; i++) {
				if (discounts[i].getId().equals(id)) {
					return discounts[i];
				}
			}

			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * Gets the Id
	 * 
	 * Returns Id
	 */
	public String getId() {
		return id;
	}

	/*
	 * Gets the Discount
	 * 
	 * Returns Discount
	 */
	public double getDiscount() {
		return discount;
	}

	/*
	 * Gets the isExpired
	 * 
	 * Returns isExpired
	 */
	public boolean getIsExpired() {
		return isExpired;
	}
	
	/*
	 * Checks if two Discounts are equal
	 * 
	 * Returns True if equal and False if not
	 */
	@Override
	public boolean equals(Object obj) {

		Discount discount;
		if (obj instanceof Discount) {
			discount = (Discount) obj;
		} else {
			return false;
		}

		if (!this.getId().equals(discount.getId())) {
			return false;
		}

		if (this.getDiscount() != discount.getDiscount()) {
			return false;
		}

		if (this.getIsExpired() != discount.getIsExpired()) {
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
		if (Discount.getDiscount(id) == null) {
			return false;
		}
		return true;
	}

}
