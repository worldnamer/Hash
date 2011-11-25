package com.worldnamer.hash;

import static com.worldnamer.hash.HasherBase.bytesToHex;

import java.security.NoSuchAlgorithmException;
import java.util.*;

public class HashExecutor {
	private Hasher md5;
	private Hasher sha1;
	private Hasher sha256;

	public HashExecutor(Hasher md5, Hasher sha1, Hasher sha256) {
		this.md5 = md5;
		this.sha1 = sha1;
		this.sha256 = sha256;
	}

	public byte[] executeBytes(ExecutionProduct product) throws NoSuchAlgorithmException {
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
				List<byte[]> interiorHashes = new ArrayList<byte[]>();
				for (ExecutionProduct interiorProduct : product.getInterior()) {
					interiorHashes.add(executeBytes(interiorProduct));
				}
				
				return hasher.hash(interiorHashes);
			} else {
				return hasher.hash(product.getPlaintext());
			}
		} else {
			return product.getPlaintext().getBytes();
		}
	}

	public String execute(ExecutionProduct product) throws NoSuchAlgorithmException {
		return new String(executeBytes(product));
	}
	
	public String execute(List<ExecutionProduct> products) throws NoSuchAlgorithmException {
		String hash = "";
		
		for (ExecutionProduct product : products) {
			if (product.getProtocol().equals("text")) {
				hash += new String(executeBytes(product));
			} else {
				hash += bytesToHex(executeBytes(product));
			}
		}
		
		return hash;
	}
}
