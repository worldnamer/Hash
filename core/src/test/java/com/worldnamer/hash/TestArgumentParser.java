package com.worldnamer.hash;

import static org.junit.Assert.*;

import org.junit.*;

public class TestArgumentParser {
	ArgumentParser parser;

	@Before
	public void before() {
		parser = new ArgumentParser();
	}

	@Test
	public void testPassword() {
		assertEquals(new ExecutionProduct("md5", "password"), parser.parse("md5(password)"));
	}

	@Test
	public void testHashable() {
		assertEquals(new ExecutionProduct("md5", "hashable"), parser.parse("md5(hashable)"));
	}

	@Test
	public void testSHA1() {
		assertEquals(new ExecutionProduct("sha1", "plaintext"), parser.parse("sha1(plaintext)"));
	}

	@Test
	public void testSHA256() {
		assertEquals(new ExecutionProduct("sha256", "plaintext"), parser.parse("sha256(plaintext)"));
	}

	@Test
	public void testMD5AndSHA1() {
		assertEquals(new ExecutionProduct("md5", new ExecutionProduct("sha1", "plaintext")),
				parser.parse("md5(sha1(plaintext))"));
	}

	@Test
	public void testSHA1AndMD5() {
		assertEquals(new ExecutionProduct("sha1", new ExecutionProduct("md5", "plaintext")),
				parser.parse("sha1(md5(plaintext))"));
	}

	@Test
	public void testMD5AndSHA256() {
		assertEquals(new ExecutionProduct("sha256", new ExecutionProduct("md5", "plaintext")),
				parser.parse("sha256(md5(plaintext))"));
	}

	@Test
	public void testSHA256AndMD5() {
		assertEquals(new ExecutionProduct("md5", new ExecutionProduct("sha256", "plaintext")),
				parser.parse("md5(sha256(plaintext))"));
	}

	@Test
	public void testSHA1AndSHA256() {
		assertEquals(new ExecutionProduct("sha1", new ExecutionProduct("sha256", "plaintext")),
				parser.parse("sha1(sha256(plaintext))"));
	}

	@Test
	public void testSHA256AndSHA1() {
		assertEquals(new ExecutionProduct("sha256", new ExecutionProduct("sha1", "plaintext")),
				parser.parse("sha256(sha1(plaintext))"));
	}

	@Test
	public void testAllTogether() {
		assertEquals(new ExecutionProduct("sha256", new ExecutionProduct("md5", new ExecutionProduct("sha1", "plaintext"))),
				parser.parse("sha256(md5(sha1(plaintext)))"));
	}
}
