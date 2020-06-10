package br.com.itau.cpy.model.enuns;

public enum Usage {
	DISPLAY("DISPLAY"), 
	BINARY("BINARY"), 
	COMP("COMP"), 
	COMP_1("COMP-1"),
	COMP_2("COMP-2"), 
	COMP_3("COMP-3"), 
	COMP_4("COMP-4"), 
	COMP_5("COMP-5"), 
	COMP_6("COMP-6"),
	PACKED("PACKED-DECIMAL"),
	INPUT_STREAM("INPUT-STREAM"),
	RAW_TEXT("RAW-TEXT");
	
	private Usage(String value) {
		this.value = value;
	}
	
	private String value;

	public String getValue() {
		return value;
	}
	
	public static Usage parse(String value) {
		for (Usage type : values()) {
			if (type.getValue().equals(value.toUpperCase())) {
				return type;
			}
		}
		return null;
	}
}
