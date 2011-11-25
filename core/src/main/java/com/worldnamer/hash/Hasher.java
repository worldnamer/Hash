package com.worldnamer.hash;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface Hasher {
	public byte[] hash(String toHash) throws NoSuchAlgorithmException;
	public byte[] hash(List<byte[]> forHashing) throws NoSuchAlgorithmException;
}
