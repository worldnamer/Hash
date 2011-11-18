package com.worldnamer.hash;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

public class TestExecutionProduct {
	@Test
	public void isEqual() {
		assertEquals(new ExecutionProduct("md5", "plaintext"), new ExecutionProduct("md5", "plaintext"));
	}

	@Test
	public void isNotEqual() {
		assertFalse(new ExecutionProduct("md5", "plaintext").equals(new ExecutionProduct("sha1", "plaintext")));
	}
	
	@Test
	public void notEqualOnBadInputs() {
		assertFalse(new ExecutionProduct("md5", "plaintext").equals(new ExecutionProduct()));
		assertFalse(new ExecutionProduct().equals(new ExecutionProduct("md5", "plaintext")));
	}
	
	@Test
	public void toStringCorrect() {
		String protocol = UUID.randomUUID().toString();
		String plaintext = UUID.randomUUID().toString();
		assertEquals(protocol + "(" + plaintext + ")", new ExecutionProduct(protocol, plaintext).toString());
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
	public void equalsInterior() {
		ExecutionProduct productA = new ExecutionProduct("md5", new ExecutionProduct("sha1", "t"));
		ExecutionProduct productB = new ExecutionProduct("md5", new ExecutionProduct("sha1", "t"));
		assertEquals(productA, productB);
	}
}
