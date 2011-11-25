package com.worldnamer.hash;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;
import static com.worldnamer.hash.ExecutionProduct.*;

public class TestExecutionProduct {
	@Test
	public void isEqual() {
		assertEquals(product("md5", "plaintext"), product("md5", "plaintext"));
	}

	@Test
	public void isNotEqual() {
		assertFalse(product("md5", "plaintext").equals(product("sha1", "plaintext")));
	}
	
	@Test
	public void notEqualOnBadInputs() {
		assertFalse(product("md5", "plaintext").equals(new ExecutionProduct()));
		assertFalse(new ExecutionProduct().equals(product("md5", "plaintext")));
	}
	
	@Test
	public void toStringCorrect() {
		String protocol = UUID.randomUUID().toString();
		String plaintext = UUID.randomUUID().toString();
		assertEquals(protocol + "(" + plaintext + ")", product(protocol, plaintext).toString());
	}
	
	@Test 
	public void toStringWithInterior() {
		String protocol = UUID.randomUUID().toString();
		String plaintext = UUID.randomUUID().toString();
		ExecutionProduct interiorProduct = new ExecutionProduct(protocol, plaintext);
		
		ExecutionProduct exteriorProduct = new ExecutionProduct(protocol, interiorProduct);
		
		assertEquals(protocol + "(" + protocol + "(" + plaintext + "))", exteriorProduct.toString());
	}
	
	@Test 
	public void toStringWithInteriorList() {
		String protocol = "prot";
		String plaintext = "plain";
		List<ExecutionProduct> interiorProduct = productList(product(protocol, plaintext), product(protocol, plaintext));
		
		ExecutionProduct exteriorProduct = new ExecutionProduct(protocol, interiorProduct);
		
		assertEquals(protocol + "(" + protocol + "(" + plaintext + ")+" + protocol + "(" + plaintext + "))", exteriorProduct.toString());
	}
	
	@Test
	public void equalsInterior() {
		ExecutionProduct productA = new ExecutionProduct("md5", new ExecutionProduct("sha1", "t"));
		ExecutionProduct productB = new ExecutionProduct("md5", new ExecutionProduct("sha1", "t"));
		assertEquals(productA, productB);
	}
	
	@Test
	public void containsList() {
		ExecutionProduct productA = product("text", "a");
		ExecutionProduct productB = product("text", "b");
		
		List<ExecutionProduct> productList = productList(productA, productB);
		ExecutionProduct productC = product("sha1", productList);
		
		assertEquals(new ExecutionProduct("sha1", productList), productC);
	}
}
