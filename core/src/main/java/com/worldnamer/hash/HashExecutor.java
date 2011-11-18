package com.worldnamer.hash;

import java.security.NoSuchAlgorithmException;

public class HashExecutor {
	private Hasher md5;
	private Hasher sha1;
	private Hasher sha256;

	public HashExecutor(Hasher md5, Hasher sha1, Hasher sha256) {
		this.md5 = md5;
		this.sha1 = sha1;
		this.sha256 = sha256;
	}

	public String execute(ExecutionProduct product) throws NoSuchAlgorithmException {
		Hasher hasher = null;
		if ("md5".equals(product.getProtocol())) {
			hasher = md5;
		} else if ("sha1".equals(product.getProtocol())) {
			hasher = sha1;
		} else if ("sha256".equals(product.getProtocol())) {
			hasher = sha256;
		}
		
		if (hasher != null) {
			if (product.getInterior() != null) {
				return hasher.hash(execute(product.getInterior()));
			} else {
				return hasher.hash(product.getPlaintext());
			}
		} else {
			return null;
		}
	}
}
