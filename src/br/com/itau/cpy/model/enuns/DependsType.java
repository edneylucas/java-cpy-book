package br.com.itau.cpy.model.enuns;

public enum DependsType {
	
	NUMBER("number"), BYTES("bytes");

	private DependsType(String value) {
		this.value = value;
	}
	
	private String value;

	public String getValue() {
		return value;
	}
	
	public static DependsType parse(String value) {
		for (DependsType type : values()) {
			if (type.getValue().equals(value.toUpperCase())) {
				return type;
			}
		}
		return null;
	}
}
