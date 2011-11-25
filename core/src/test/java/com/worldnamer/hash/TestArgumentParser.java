package com.worldnamer.hash;

import static org.junit.Assert.*;
import static com.worldnamer.hash.ExecutionProduct.*;

import java.util.List;

import org.junit.*;

public class TestArgumentParser {
	private ArgumentParser parser;

	@Before
	public void before() {
		parser = new ArgumentParser();
	}

	@Test
	public void password() {
		assertEquals(productList(product("md5", product("text", "password"))), parser.parse("md5(password)"));
	}

	@Test
	public void hashable() {
		assertEquals(productList(product("md5", product("text", "hashable"))), parser.parse("md5(hashable)"));
	}

	@Test
	public void SHA1() {
		assertEquals(productList(product("sha1", product("text", "plaintext"))), parser.parse("sha1(plaintext)"));
	}

	@Test
	public void SHA256() {
		assertEquals(productList(product("sha256", product("text", "plaintext"))), parser.parse("sha256(plaintext)"));
	}

	@Test
	public void MD5AndSHA1() {
		assertEquals(productList(product("md5", product("sha1", product("text", "plaintext")))),
				parser.parse("md5(sha1(plaintext))"));
	}

	@Test
	public void SHA1AndMD5() {
		assertEquals(productList(product("sha1", product("md5", product("text", "plaintext")))),
				parser.parse("sha1(md5(plaintext))"));
	}

	@Test
	public void MD5AndSHA256() {
		assertEquals(productList(product("sha256", product("md5", product("text", "plaintext")))),
				parser.parse("sha256(md5(plaintext))"));
	}

	@Test
	public void SHA256AndMD5() {
		assertEquals(productList(product("md5", product("sha256", product("text", "plaintext")))),
				parser.parse("md5(sha256(plaintext))"));
	}

	@Test
	public void SHA1AndSHA256() {
		assertEquals(productList(product("sha1", product("sha256", product("text", "plaintext")))),
				parser.parse("sha1(sha256(plaintext))"));
	}

	@Test
	public void SHA256AndSHA1() {
		assertEquals(productList(product("sha256", product("sha1", product("text", "plaintext")))),
				parser.parse("sha256(sha1(plaintext))"));
	}

	@Test
	public void AllTogether() {
		assertEquals(productList(product("sha256", product("md5", product("sha1", product("text", "plaintext"))))),
				parser.parse("sha256(md5(sha1(plaintext)))"));
	}

	@Test
	public void plaintext() {
		assertEquals(productList(product("text", "plaintext")), parser.parse("plaintext"));
	}

	@Test
	public void addition() {
		assertEquals(productList(product("text", "plain"), product("text","text")), parser.parse("plain+text"));
	}

	@Test
	public void additionWithinFunction() {
		List<ExecutionProduct> plainPlusPlain = productList(product("text", "plain"), product("text","text"));
		assertEquals(productList(product("sha1", plainPlusPlain)), parser.parse("sha1(plain+text)"));
	}

	@Test
	public void topLevelAdditionWithFunction() {
		List<ExecutionProduct> plainPlusText = productList(product("sha1", product("text", "plain")), product("text","text"));
		assertEquals(plainPlusText, parser.parse("sha1(plain)+text"));
	}
	
	@Test
	public void additionWithFunctionOfSubfunction() {
		List<ExecutionProduct> plainPlusPlain = productList(product("md5", product("text", "plain")), product("text","text"));
		assertEquals(productList(product("sha1", plainPlusPlain)), parser.parse("sha1(md5(plain)+text)"));
	}

	@Test
	public void additionWithFunctionOfSubfunctionReversed() {
		List<ExecutionProduct> plainPlusPlain = productList(product("text","text"), product("md5", product("text", "plain")));
		assertEquals(productList(product("sha1", plainPlusPlain)), parser.parse("sha1(text+md5(plain))"));
	}

	@Test
	public void complex() {
		String parse = "sha1(sha1+md5(md5))+sha256(a+b+c)";
		
		ExecutionProduct sha1Text = product("text", "sha1");
		
		ExecutionProduct md5Text = product("text", "md5");
		ExecutionProduct md5 = product("md5", productList(md5Text)); 
		
		List<ExecutionProduct> sha1List = productList(sha1Text, md5);
		ExecutionProduct sha1 = product("sha1", sha1List);
		
		ExecutionProduct aText = product("text", "a");
		ExecutionProduct bText = product("text", "b");
		ExecutionProduct cText = product("text", "c");
		ExecutionProduct sha256 = product("sha256", productList(aText, bText, cText));
		
		List<ExecutionProduct> top = productList(sha1, sha256);
		assertEquals(top, parser.parse(parse));
	}
}
