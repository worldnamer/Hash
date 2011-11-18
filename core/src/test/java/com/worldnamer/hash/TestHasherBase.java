package com.worldnamer.hash;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;

import org.junit.*;

public abstract class TestHasherBase {
	protected Class<? extends HasherBase> hasherClass;
	protected HasherBase hasher;
	protected String empty;
	protected String password;
	
	public TestHasherBase(Class<? extends HasherBase> hasherClass, String empty, String password) {
		this.hasherClass = hasherClass;
		this.empty = empty;
		this.password = password;
	}
	
	@Before
	public void before() throws InstantiationException, IllegalAccessException {
		this.hasher = hasherClass.newInstance();
	}
	
	@Test
	public void hashEmptyString() throws NoSuchAlgorithmException {
		assertEquals(empty, hasher.hash(""));
	}
	
	@Test
	public void hashPassword() throws NoSuchAlgorithmException {
		assertEquals(password, hasher.hash("password"));
	}
}
