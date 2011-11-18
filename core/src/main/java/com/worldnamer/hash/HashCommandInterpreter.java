package com.worldnamer.hash;

import java.security.NoSuchAlgorithmException;

public class HashCommandInterpreter {
	public static void main(String args[]) throws NoSuchAlgorithmException {
		ArgumentParser parser = new ArgumentParser();
		ExecutionProduct execution = parser.parse(args[0]);

		HashExecutor executor = new HashExecutor(new MD5Hasher(), new SHA1Hasher(), new SHA256Hasher());
		System.out.println(executor.execute(execution));
	}
}
