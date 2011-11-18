package com.worldnamer.hash;

import java.security.NoSuchAlgorithmException;

public class HashCommandInterpreter {
	public static void main(String args[]) throws NoSuchAlgorithmException {
		ArgumentParser parser = new ArgumentParser();
		ExecutionProduct execution = parser.parse(args[0]);
		
		if (execution != null) {
			HasherBase hasher = null;
			if ("md5".equals(execution.getProtocol())) {
				hasher = new MD5Hasher();
			} else if ("sha1".equals(execution.getProtocol())) {
				hasher = new SHA1Hasher();
			} else if ("sha256".equals(execution.getProtocol())) {
				hasher = new SHA256Hasher();
			}
			
			System.out.println(hasher.hash(execution.getPlaintext()));
		}
	}
}
