package br.com.itau.cpy.model.enuns;

public enum Type {
	ALPHANUM("ALPHANUM"), NUMERIC("NUMERIC"), ALPHABETIC("ALPHABETIC");

	private Type(String value) {
		this.value = value;
	}
	
	private String value;

	public String getValue() {
		return value;
	}
	
	public static Type parse(String value) {
		for (Type type : values()) {
			if (type.getValue().equals(value.toUpperCase())) {
				return type;
			}
		}
		return null;
	}
}
