package com.worldnamer.hash;

import java.security.*;
import java.util.List;

public abstract class HasherBase implements Hasher {
	private String protocol;
	
	protected HasherBase(String protocol) {
		this.protocol = protocol;
	}
	
	protected static String bytesToHex(byte[] b) {
		char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
		StringBuffer buf = new StringBuffer();
		for (int j = 0; j < b.length; j++) {
			buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
			buf.append(hexDigit[b[j] & 0x0f]);
		}
	
		return buf.toString();
	}
	
	@Override
	public byte[] hash(String toHash) throws NoSuchAlgorithmException {
		MessageDigest digest;
		digest = MessageDigest.getInstance(protocol);
		
		digest.update(toHash.getBytes());
		
		return digest.digest();
	}
	
	@Override
	public byte[] hash(List<byte[]> forHashing) throws NoSuchAlgorithmException {
		MessageDigest digest;
		digest = MessageDigest.getInstance(protocol);
		
		for (byte[] toHash : forHashing) {
			digest.update(toHash);
		}
		
		return digest.digest();
	}
}
