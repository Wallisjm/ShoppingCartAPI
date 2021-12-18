package test;


import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import api.Response;

class TestResponse {
	
	
	@Test
	void hasError() {
		ArrayList<String> errors = new ArrayList<String>();		
		Response response = new Response(errors);
		assertFalse(response.hasError());
		
		errors.add("An error");
		response = new Response(errors);
		assertTrue(response.hasError());
	}
	
	@Test
	void getError() {
		ArrayList<String> errors = new ArrayList<String>();
		ArrayList<String> expected = new ArrayList<String>();
		Response response = new Response(errors);
		assertEquals(response.getError(), expected);
		
		errors.add("Error 1");
		expected.add("Error 1");
		response = new Response(errors);
		assertEquals(response.getError(), expected);
		
		errors.add("Error 2");
		expected.add("Error 2");
		response = new Response(errors);
		assertEquals(response.getError(), expected);
	}
	
	@Test
	void equals() {
		ArrayList<String> errors1 = new ArrayList<String>();		
		ArrayList<String> errors2 = new ArrayList<String>();
		ArrayList<String> errors3 = new ArrayList<String>();
		errors1.add("Test");
		errors2.add("Test");
		Response response1 = new Response(errors1);
		Response response2 = new Response(errors2);
		Response response3 = new Response(errors3);
		assertTrue(response1.equals(response2));
		assertTrue(response1.equals(response1));
		assertFalse(response1.equals(response3));
	}

}
