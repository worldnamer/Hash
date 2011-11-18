package com.worldnamer.hash;

public class ExecutionProduct {
	private String protocol;
	private String plaintext;
	private ExecutionProduct interior;

	public ExecutionProduct() {

	}

	public ExecutionProduct(String protocol, String plaintext) {
		this.protocol = protocol;
		this.plaintext = plaintext;
	}

	public ExecutionProduct(String protocol, ExecutionProduct interior) {
		this.protocol = protocol;
		this.interior = interior;
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
			if (product.getInterior() != null && this.interior != null) {
				if (this.protocol != null && product.protocol != null) {
					if (this.protocol.equals(product.protocol) && this.interior.equals(product.interior)) {
						return true;
					}
				}
			} else if (product.getInterior() == null && this.interior == null) {
				if (this.protocol == null || product.protocol == null || this.plaintext == null
						|| product.plaintext == null)
					return false;

				if ((product.protocol.equals(this.protocol)) && (product.plaintext.equals(this.plaintext))) {
					return true;
				}
			}
		}

		return false;
	}

	public void setInterior(ExecutionProduct interior) {
		this.interior = interior;
	}

	public ExecutionProduct getInterior() {
		return interior;
	}

	@Override
	public String toString() {
		if (interior != null) {
			return protocol + "(" + interior.toString() + ")";
		} else {
			return protocol + "(" + plaintext + ")";
		}
	}
}
