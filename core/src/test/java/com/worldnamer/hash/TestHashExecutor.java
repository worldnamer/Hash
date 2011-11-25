package com.worldnamer.hash;

import static com.worldnamer.hash.ExecutionProduct.*;
import static com.worldnamer.hash.HasherBase.bytesToHex;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.util.*;

import org.easymock.Capture;
import org.junit.*;

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
		expect(md5.hash("password")).andReturn("hash".getBytes());
		replay(md5);
		replay(sha1);
		replay(sha256);
		ExecutionProduct product = new ExecutionProduct("md5", "password");
		
		assertEquals("hash", executor.execute(product));
	}

	@Test
	public void executeSHA1() throws NoSuchAlgorithmException {
		replay(md5);
		expect(sha1.hash("password")).andReturn("hash".getBytes());
		replay(sha1);
		replay(sha256);
		ExecutionProduct product = new ExecutionProduct("sha1", "password");
		
		assertEquals("hash", executor.execute(product));
	}

	@Test
	public void executeSHA256() throws NoSuchAlgorithmException {
		replay(md5);
		replay(sha1);
		expect(sha256.hash("password")).andReturn("hash".getBytes());
		replay(sha256);
		ExecutionProduct product = new ExecutionProduct("sha256", "password");
		
		assertEquals("hash", executor.execute(product));
	}

	@Test
	// sha256(md5(password))
	public void executeNested() throws NoSuchAlgorithmException {
		expect(md5.hash("password")).andReturn("hash".getBytes());
		replay(md5);
		replay(sha1);

		Capture<List<byte[]>> capture = new Capture<List<byte[]>>();
		
		expect(sha256.hash(capture(capture))).andReturn("doublehash".getBytes());
		replay(sha256);
		ExecutionProduct product = new ExecutionProduct("sha256", new ExecutionProduct("md5", "password"));
		
		assertEquals("doublehash", executor.execute(product));
		assertArrayEquals("hash".getBytes(), capture.getValue().get(0));
	}
	
	@Test
	// md5(password)
	public void executeSingleSeries() throws NoSuchAlgorithmException {
		expect(md5.hash("password")).andReturn("hash".getBytes());
		replay(md5);
		replay(sha1);
		replay(sha256);
		ExecutionProduct product = new ExecutionProduct("md5", "password");
		List<ExecutionProduct> products = new ArrayList<ExecutionProduct>();
		products.add(product);
		
		assertEquals(bytesToHex("hash".getBytes()), executor.execute(products));
	}
	
	@Test
	// md5(password)+sha1(password)
	public void executeDoubleSeries() throws NoSuchAlgorithmException {
		expect(md5.hash("password")).andReturn("hash".getBytes());
		replay(md5);
		expect(sha1.hash("password")).andReturn("hash".getBytes());
		replay(sha1);
		replay(sha256);
		ExecutionProduct productOne = new ExecutionProduct("md5", "password");
		ExecutionProduct productTwo = new ExecutionProduct("sha1", "password");
		List<ExecutionProduct> products = new ArrayList<ExecutionProduct>();
		products.add(productOne);
		products.add(productTwo);
		
		assertEquals(bytesToHex("hashhash".getBytes()), executor.execute(products));
	}
	
	@Test
	// plaintext
	public void executePlaintext() throws NoSuchAlgorithmException {
		replay(md5);
		replay(sha1);
		replay(sha256);
		
		ExecutionProduct product = new ExecutionProduct("text", "plaintext");
		
		assertEquals("plaintext", executor.execute(product));
	}
	
	@Test
	// sha256(salt+sha256(password))
	public void executeSaltPassword() throws NoSuchAlgorithmException {
		replay(md5);
		replay(sha1);
		
		Capture<List<byte[]>> captureOuter = new Capture<List<byte[]>>();
		Capture<List<byte[]>> captureInner = new Capture<List<byte[]>>();

		expect(sha256.hash(capture(captureOuter))).andReturn("passwordhash".getBytes());
		expect(sha256.hash(capture(captureInner))).andReturn("finalsalt".getBytes());
		replay(sha256);
		
		ExecutionProduct salt = product("text", "salt");
		ExecutionProduct passwordText = product("text", "password");
		ExecutionProduct password = product("sha256", passwordText);
		List<ExecutionProduct> list = productList(salt, password);
		ExecutionProduct sha256 = product("sha256", list);
		
		assertEquals("finalsalt", executor.execute(sha256));
		assertArrayEquals("password".getBytes(), captureOuter.getValue().get(0));
		assertArrayEquals("salt".getBytes(), captureInner.getValue().get(0));
		assertArrayEquals("passwordhash".getBytes(), captureInner.getValue().get(1));
	}
}
