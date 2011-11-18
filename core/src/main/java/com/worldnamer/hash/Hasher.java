package com.worldnamer.hash;

import java.security.NoSuchAlgorithmException;

public interface Hasher {
	public String hash(String toHash) throws NoSuchAlgorithmException;
}
