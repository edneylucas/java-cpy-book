package br.com.itau.cpy.core.parser.field;

import br.com.itau.cpy.core.parser.book.model.CpyBookLine;
import br.com.itau.cpy.exception.CopybookException;
import br.com.itau.cpy.model.Field;
import br.com.itau.cpy.model.enuns.DependsType;
import br.com.itau.cpy.model.enuns.Sign;
import br.com.itau.cpy.model.enuns.Type;
import br.com.itau.cpy.model.enuns.Usage;

public class FieldParser {

	public static Field parser(CpyBookLine value) {
		try {
			Field field = new Field();
			field.setName(value.getName());
			field.setLength(calculateLength(value.getPicture()));
			field.setBytes(calculateBytes(value.getUsage(), field.getLength()));
			field.setMinOccurs(value.getMinOccurs());
			field.setDependsOccurs(value.getDependingOn());
			field.setType(calculateType(value.getPicture()));
			field.setDecimals(calculateDecimals(value.getPicture()));
			field.setValue(value.getValue());
			field.setPIC(value.getPicture());
			field.setHn(value.getHierarchyLevel());
			field.setDependsType(DependsType.NUMBER);
			field.setSign(calculateSign(value.getPicture()));
			field.setUsage(Usage.parse(value.getUsage()));
			
			if (value.getMaxOccurs() < value.getMinOccurs()) {
				field.setMaxOccurs(field.getMinOccurs());
			} else {
				field.setMaxOccurs(value.getMaxOccurs());
			}
			return field;
		} catch (Exception e) {
			return null;
		}
	}

	private static int calculateLength(final String pic) throws CopybookException {
		int length = 0;
		final int picLength = pic.trim().length();
		int count = 0;
		StringBuilder num = null;
		while (count < picLength) {
			final char c = pic.charAt(count);
			if (num != null && c != ')') {
				num.append(c);
			} else {
				switch (c) {
				case '-':
				case '9':
				case 'A':
				case 'X': {
					if (count == picLength - 1 || pic.charAt(count + 1) != '(') {
						++length;
						break;
					}
					break;
				}
				case '(': {
					num = new StringBuilder();
					break;
				}
				case ')': {
					try {
						length += Integer.parseInt(num.toString());
					} catch (Exception ex) {
						throw new CopybookException("Error parsing integer ", (Throwable) ex);
					}
					num = null;
					break;
				}
				}
			}
			++count;
		}
		return length;
	}

	private static int calculateBytes(final String usage, final int length) {
		int bytes = 0;
		if ("DISPLAY".equalsIgnoreCase(usage) || "INPUT-STREAM".equalsIgnoreCase(usage) || "RAW-TEXT".equalsIgnoreCase(usage)) {
			bytes = length;
		} else if ("COMP-3".equalsIgnoreCase(usage) || "PACKED-DECIMAL".equalsIgnoreCase(usage)) {
			bytes = length / 2 + 1;
		} else if ("BINARY".equalsIgnoreCase(usage) || "COMP".equalsIgnoreCase(usage)) {
			if (length < 5) {
				bytes = 2;
			} else if (length < 10) {
				bytes = 4;
			} else if (length < 19) {
				bytes = 8;
			}
		}
		return bytes;
	}

	private static Type calculateType(final String pic) {
		if (pic.charAt(0) == 'S' || pic.charAt(0) == '9' || pic.lastIndexOf("9(") > -1) {
			return Type.NUMERIC;
		} else if (pic.lastIndexOf(88) > -1) {
			return Type.ALPHANUM;
		} else if (pic.lastIndexOf(65) > -1) {
			return Type.ALPHABETIC;
		}
		return null;
	}

	public static int calculateDecimals(String pic) throws CopybookException {
		int decimals = 0;
		if (pic.indexOf("V") == -1) {
			return decimals;
		}
		pic = pic.substring(pic.indexOf("V") + 1);
		final int len = pic.trim().length();
		int count = 0;
		StringBuilder num = null;
		while (count < len) {
			final char c = pic.charAt(count);
			if (num != null && c != ')') {
				num.append(c);
			} else {
				switch (c) {
				case '9': {
					if (count == len - 1 || pic.charAt(count + 1) != '(') {
						++decimals;
						break;
					}
					break;
				}
				case '(': {
					num = new StringBuilder();
					break;
				}
				case ')': {
					try {
						decimals += Integer.parseInt(num.toString());
					} catch (Exception ex) {
						throw new CopybookException("Error parsing integer ", (Throwable) ex);
					}
					num = null;
					break;
				}
				}
			}
			++count;
		}
		return decimals;
	}

	public static Sign calculateSign(final String pic) {
		if (pic.charAt(0) == 'S') {
			return Sign.IMPLICIT;
		} else if (pic.endsWith("-")) {
			return Sign.EXPLICIT;
		} else {
			return Sign.NONE;
		}
	}

}
