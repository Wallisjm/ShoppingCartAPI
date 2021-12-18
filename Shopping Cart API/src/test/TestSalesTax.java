package test;


import api.ItemView;
import api.SalesTax;
import api.State;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TestSalesTax {
	
	
	@Test
	void CalulateTax() {
		// Test for Texas
		assertEquals(SalesTax.calculateTax(State.TEXAS, 10.0), 10.6250);
		
		// Test for Indiana
		assertEquals(SalesTax.calculateTax(State.INDIANA, 10.0), 15.0);
	}
	

}
