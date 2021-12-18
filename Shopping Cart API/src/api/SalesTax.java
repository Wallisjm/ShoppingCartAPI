package api;

public class SalesTax {
	
	/*
	 * Calculated Cost of tax for customers
	 * state: State
	 * price: double
	 * 
	 * Returns adjusted Cost
	 */
	public static double calculateTax(State state, double price) {
		switch (state) {
		case TEXAS:
			return texasTax(price);
		case INDIANA:
			return indianaTax(price);
		default:
			return price;
		}
		
	}

	/*
	 * Calculates Sales tax for Texas customers
	 * price: double
	 * 
	 * Returns adjusted price
	 */
	private static double texasTax(double price) {
		return (.0625 * price) + price;
	}
	
	private static double indianaTax(double price) {
		return 5 + price;
	}
	
}
