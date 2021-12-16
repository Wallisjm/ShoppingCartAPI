package test;

import api.API;
import api.Cart;
import api.Response;
import api.State;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import org.junit.jupiter.api.Test;

class TestAPI {

	@Test
	void ViewCartInvalidUserId() {
		String userId = "A";
		// Generate Expected Response
		ArrayList<String> error = new ArrayList<String>();
		error.add("UserId " + userId + " does not have a cart");
		Response expected =  new Response(error);
		
		assertTrue(API.viewCart(userId).equals(expected));	
	}
	


}
