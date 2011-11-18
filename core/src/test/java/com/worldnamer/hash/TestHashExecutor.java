package com.worldnamer.hash;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;

import org.junit.*;
import static org.easymock.EasyMock.*;

public class TestHashExecutor {
	private Hasher md5;
	private Hasher sha1;
	private Hasher sha256;
	private HashExecutor executor;
	
	@Before
	public void before() {
		md5 = createMock(Hasher.class);
		sha1 = createMock(Hasher.class);
		sha256 = createMock(Hasher.class);
		
		executor = new HashExecutor(md5, sha1, sha256);
	}
	
	@After
	public void after() {
		verify(md5);
		verify(sha1);
		verify(sha256);
	}
	
	@Test
	public void executeMD5() throws NoSuchAlgorithmException {
		expect(md5.hash("password")).andReturn("hash");
		replay(md5);
		replay(sha1);
		replay(sha256);
		ExecutionProduct product = new ExecutionProduct("md5", "password");
		
		assertEquals("hash", executor.execute(product));
	}

	@Test
	public void executeSHA1() throws NoSuchAlgorithmException {
		replay(md5);
		expect(sha1.hash("password")).andReturn("hash");
		replay(sha1);
		replay(sha256);
		ExecutionProduct product = new ExecutionProduct("sha1", "password");
		
		assertEquals("hash", executor.execute(product));
	}

	@Test
	public void executeSHA256() throws NoSuchAlgorithmException {
		replay(md5);
		replay(sha1);
		expect(sha256.hash("password")).andReturn("hash");
		replay(sha256);
		ExecutionProduct product = new ExecutionProduct("sha256", "password");
		
		assertEquals("hash", executor.execute(product));
	}

	@Test
	public void executeNested() throws NoSuchAlgorithmException {
		expect(md5.hash("password")).andReturn("hash");
		replay(md5);
		replay(sha1);
		expect(sha256.hash("hash")).andReturn("doublehash");
		replay(sha256);
		ExecutionProduct product = new ExecutionProduct("sha256", new ExecutionProduct("md5", "password"));
		
		assertEquals("doublehash", executor.execute(product));
	}
}
