package com.worldnamer.hash;

import static com.worldnamer.hash.ExecutionProduct.*;

import java.util.*;

public class ArgumentParser {
	private class ArgumentString {
		String arguments;
	}
	
	private List<ExecutionProduct> process(String protocolWithParens, ArgumentString argstr, ExecutionProduct parent) {
		String protocol = protocolWithParens.substring(0, protocolWithParens.lastIndexOf('('));

		ExecutionProduct product = new ExecutionProduct();
		product.setProtocol(protocol);

		argstr.arguments = argstr.arguments.substring(protocolWithParens.length(), argstr.arguments.length());
		List<ExecutionProduct> list = newestAlgorithm(argstr, product);
		product.setInterior(list);

		List<ExecutionProduct> topLevelList = new ArrayList<ExecutionProduct>();
		topLevelList.add(product);

		ExecutionProduct from = product;
		List<ExecutionProduct> fromList = list;
		if (argstr.arguments.startsWith(")")) {
			argstr.arguments = argstr.arguments.substring(1, argstr.arguments.length());
			from = parent;
			fromList = topLevelList;
		}
		if (argstr.arguments.startsWith("+")) {
			while (!argstr.arguments.isEmpty() && !argstr.arguments.startsWith(")")) {
				argstr.arguments = argstr.arguments.substring(1, argstr.arguments.length());
				fromList.addAll(newestAlgorithm(argstr, from));
			}
			if (!argstr.arguments.isEmpty()) {
				argstr.arguments = argstr.arguments.substring(1, argstr.arguments.length());
			}
		}
		
		return topLevelList;
	}
	
	private List<ExecutionProduct> newestAlgorithm(ArgumentString argstr, ExecutionProduct parent) {
		String md5 = "md5(";
		String sha1 = "sha1(";
		String sha256 = "sha256(";

		if (argstr.arguments.startsWith(md5)) {
			return process(md5, argstr, parent);
		} else if (argstr.arguments.startsWith(sha1)) {
			return process(sha1, argstr, parent);
		} else if (argstr.arguments.startsWith(sha256)) {
			return process(sha256, argstr, parent);
		} else {
			if (parent == null) {
				List<ExecutionProduct> list = new ArrayList<ExecutionProduct>();
				for (String part : argstr.arguments.split("\\+")) {
					list.add(product("text", part));
					argstr.arguments = "";
				}
				
				return list;
			} else {
				List<ExecutionProduct> list = new ArrayList<ExecutionProduct>();
				int nextParens = argstr.arguments.indexOf(')');
				int nextPlus = argstr.arguments.indexOf('+');
				if (nextPlus != -1 && nextParens != -1 && nextPlus < nextParens) {
					list.add(product("text", argstr.arguments.substring(0, nextPlus)));
					argstr.arguments = argstr.arguments.substring(nextPlus, argstr.arguments.length());
				} else if (nextParens == -1) {
					list.add(product("text", argstr.arguments));
					argstr.arguments = "";
				} else {
					list.add(product("text", argstr.arguments.substring(0, nextParens)));
					argstr.arguments = argstr.arguments.substring(nextParens, argstr.arguments.length());
				}
				
				return list;
			}
		}
	}

	public List<ExecutionProduct> parse(String arguments) {
		ArgumentString argstr = new ArgumentString();
		argstr.arguments = arguments;
		
		List<ExecutionProduct> list = newestAlgorithm(argstr, null);
		
		while (!argstr.arguments.equals("")) {
			if (argstr.arguments.startsWith(")")) {
				argstr.arguments = argstr.arguments.substring(1, argstr.arguments.length());
			}
			if (argstr.arguments.startsWith("+")) {
				argstr.arguments = argstr.arguments.substring(1, argstr.arguments.length());
			}
			if (!argstr.arguments.equals("")) {
				list.addAll(newestAlgorithm(argstr, null));
			}
		}
		
		return list;
	}
}
