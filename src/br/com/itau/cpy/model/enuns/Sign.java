package br.com.itau.cpy.model.enuns;

public enum Sign {

	IMPLICIT("implicit"), EXPLICIT("explicit"), NONE("nosign");
	
	private Sign(String value) {
		this.value = value;
	}
	
	private String value;

	public String getValue() {
		return value;
	}
	
	public static Sign parse(String value) {
		for (Sign type : values()) {
			if (type.getValue().equals(value.toUpperCase())) {
				return type;
			}
		}
		return null;
	}

}
