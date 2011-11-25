package com.worldnamer.hash;

import java.util.*;

public class ExecutionProduct {
	private String protocol;
	private String plaintext;
	private List<ExecutionProduct> interior;

	public ExecutionProduct() {

	}

	static public ExecutionProduct product(String protocol, String text) {
		return new ExecutionProduct(protocol, text);
	}

	static public ExecutionProduct product(String protocol, ExecutionProduct interior) {
		return new ExecutionProduct(protocol, interior);
	}
	
	static public List<ExecutionProduct> productList(ExecutionProduct ... products) {
		return Arrays.asList(products);
	}

	static public ExecutionProduct product(String protocol, List<ExecutionProduct> productList) {
		return new ExecutionProduct(protocol, productList);
	}
	
	public ExecutionProduct(String protocol, String plaintext) {
		this.protocol = protocol;
		this.plaintext = plaintext;
	}

	public ExecutionProduct(String protocol, ExecutionProduct interior) {
		this.protocol = protocol;
		this.interior = productList(interior);
	}

	public ExecutionProduct(String protocol, List<ExecutionProduct> interior) {
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

	public void setInterior(List<ExecutionProduct> interior) {
		this.interior = interior;
	}

	public List<ExecutionProduct> getInterior() {
		return interior;
	}

	@Override
	public String toString() {
		if (interior != null) {
			String serialized = protocol + "(";
			
			for (Iterator<ExecutionProduct> iterator = interior.iterator(); iterator.hasNext(); ) {
				ExecutionProduct product = iterator.next();
				if (product != null) {
					serialized += product.toString();
				}
				if (iterator.hasNext()) {
					serialized += "+";
				}
			}
			serialized += ")";
			
			return serialized;
		} else {
			return protocol + "(" + plaintext + ")";
		}
	}
}
