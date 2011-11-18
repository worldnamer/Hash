package com.worldnamer.hash;

public class ExecutionProduct {
	private String protocol;
	private String plaintext;

	public ExecutionProduct() {

	}

	public ExecutionProduct(String protocol, String plaintext) {
		this.protocol = protocol;
		this.plaintext = plaintext;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getPlaintext() {
		return plaintext;
	}

	public void setPlaintext(String plaintext) {
		this.plaintext = plaintext;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;

		if (o instanceof ExecutionProduct) {
			ExecutionProduct product = (ExecutionProduct) o;
			if (this.protocol == null || product.getProtocol() == null || this.plaintext == null
					|| product.getPlaintext() == null)
				return false;

			if ((product.getProtocol().equals(this.protocol)) && (product.getPlaintext().equals(this.plaintext))) {
				return true;
			}
		}

		return false;
	}
	
	@Override
	public String toString() {
		return protocol + "(" + plaintext + ")";
	}
}
