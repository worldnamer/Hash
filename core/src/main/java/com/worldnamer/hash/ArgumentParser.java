package com.worldnamer.hash;

public class ArgumentParser {
	public ExecutionProduct parse(String arguments) {
		String protocol = "unk";
		int index = -1;
		if ((index = arguments.indexOf("md5(")) != -1) {
			protocol = "md5";
		} else if ((index = arguments.indexOf("sha1(")) != -1) {
			protocol = "sha1";
		} else if ((index = arguments.indexOf("sha256(")) != -1) {
			protocol = "sha256";
		} else {
			return null;
		}

		String plaintext = arguments.substring(index + protocol.length()+1, arguments.lastIndexOf(')'));
		return new ExecutionProduct(protocol, plaintext);
	}
}
