package com.worldnamer.hash;

public class ArgumentParser {
	public ExecutionProduct parse(String arguments) {
		String protocol = "unk";
		int index = -1;
		int indexMD5 = -1;
		int indexSHA1 = -1;
		int indexSHA256 = -1;

		indexMD5 = arguments.indexOf("md5(");
		if (indexMD5 != -1) {
			protocol = "md5";
			index = indexMD5;
		}

		indexSHA1 = arguments.indexOf("sha1(");
		if (indexSHA1 != -1) {
			if (indexMD5 != -1) {
				if (indexSHA1 < indexMD5) {
					protocol = "sha1";
					index = indexSHA1;
				}
			} else {
				protocol = "sha1";
				index = indexSHA1;
			}
		}

		indexSHA256 = arguments.indexOf("sha256(");
		if (indexSHA256 != -1) {
			if (indexMD5 != -1) {
				if (indexSHA256 < indexMD5) {
					if (indexSHA1 != -1) {
						if (indexSHA256 < indexSHA1) {
							protocol = "sha256";
							index = indexSHA256;
						}
					} else {
						protocol = "sha256";
						index = indexSHA256;
					}
				}
			} else {
				if (indexSHA1 != -1) {
					if (indexSHA256 < indexSHA1) {
						protocol = "sha256";
						index = indexSHA256;
					}
				} else {
					protocol = "sha256";
					index = indexSHA256;
				}
			}
		}

		if (index == -1)
			return null;

		String plaintext = arguments.substring(index + protocol.length() + 1, arguments.lastIndexOf(')'));
		ExecutionProduct product = parse(plaintext);
		if (product != null) {
			return new ExecutionProduct(protocol, product);
		} else {
			return new ExecutionProduct(protocol, plaintext);
		}
	}
}
