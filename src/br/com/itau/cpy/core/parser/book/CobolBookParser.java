package br.com.itau.cpy.core.parser.book;

import br.com.itau.cpy.core.parser.book.model.CpyBookLine;
import br.com.itau.cpy.exception.CopybookException;

public class CobolBookParser {
	
	public static CpyBookLine parseCobolBook(String line) throws CopybookException {
		line = trimPeriod(line);
		CpyBookLine rec = new CpyBookLine();
		String[] fields = line.split("[\t\\s+]");
		fields = line.split("\\s++");
		if (fields.length < 2) {
			return null;
		}
		int index;
		for (index = 0; fields[index].length() < 1; ++index) {
		}
		rec.setHierarchyLevel(Integer.parseInt(fields[index]));
		++index;
		rec.setName(fields[index]);
		rec.setName(trimPeriod(rec.getName()));
		
		while (index + 1 < fields.length) {
			++index;
			fields[index] = trimPeriod(fields[index]);
			if (index + 1 < fields.length) {
				if (fields[index].equalsIgnoreCase("PIC") || fields[index].equalsIgnoreCase("PICTURE")) {
					++index;
					rec.setPicture(fields[index]);
					rec.setPicture(trimPeriod(rec.getPicture()));
				} else if (fields[index].equalsIgnoreCase("VALUE")) {
					++index;
					if (fields[index].startsWith("'")) {
						int start = line.indexOf("'");
						int end = line.lastIndexOf("'");
						
						rec.setValue(line.substring(start + 1, end));
					} else if (fields[index].startsWith("\"")) {
						int start = line.indexOf("\"");
						int end = line.lastIndexOf("\"");
						
						rec.setValue(line.substring(start + 1, end));
					} else if (fields[index].equalsIgnoreCase("SPACES") || fields[index].equalsIgnoreCase("BRANCOS")) {
						final StringBuilder buffer = new StringBuilder();
						for (int length = calculateLength(rec.getPicture()), i = 0; i < length; ++i) {
							buffer.append(' ');
						}
						rec.setValue(buffer.toString());
					} else if (fields[index].equalsIgnoreCase("ZEROS")) {
						StringBuilder buffer = new StringBuilder();
						for (int length = calculateLength(rec.getPicture()), i = 0; i < length; ++i) {
							buffer.append("0");
						}
						rec.setValue(buffer.toString());
					} else {
						rec.setValue(fields[index]);
						rec.setValue(rec.getValue().replace('\'', ' '));
						rec.setValue(rec.getValue().replace('(', ' '));
						rec.setValue(rec.getValue().replace(')', ' '));
						rec.setValue(rec.getValue().replace('\"', ' '));
						rec.setValue(trimPeriod(rec.getValue()));
						rec.setValue(rec.getValue().trim());
					}
				} else if (fields[index].equalsIgnoreCase("OCCURS")) {
					if (fields.length <= index + 2) {
						++index;
						rec.setMinOccurs(Integer.parseInt(fields[index]));
						++index;
					} else if (index + 3 < fields.length && fields[index + 3].equalsIgnoreCase("DEPENDING")) {
						++index;
						rec.setMaxOccurs(Integer.parseInt(fields[index]));
						rec.setMinOccurs(0);
						++index;
						if (fields[index].equalsIgnoreCase("TIMES")) {
							++index;
						}
						++index;
						++index;
						rec.setDependingOn(trimPeriod(fields[index]));
					} else if (index + 2 <= fields.length && fields[index + 2].equalsIgnoreCase("TO")) {
						++index;
						rec.setMinOccurs(Integer.parseInt(fields[index]));
						++index;
						++index;
						rec.setMaxOccurs(Integer.parseInt(fields[index]));
						++index;
						if (fields[index].equalsIgnoreCase("TIMES")) {
							++index;
						}
						++index;
						++index;
						rec.setDependingOn(trimPeriod(fields[index]));
					} else if (fields[index + 2].equalsIgnoreCase("TIMES")) {
						++index;
						rec.setMinOccurs(Integer.parseInt(fields[index]));
						++index;
					}
					if (index + 4 > fields.length) {
						continue;
					}
					++index;
					if (!fields[index].equalsIgnoreCase("INDEXED")) {
						continue;
					}
					++index;
					++index;
					rec.setIndexedBy(trimPeriod(fields[index]));
					++index;
				} else if (fields[index].equalsIgnoreCase("REDEFINES")) {
					++index;
					rec.setRedefines(fields[index]);
				} else {
					if (fields[index].toUpperCase().indexOf("COMP") != 0
							&& fields[index].toUpperCase().indexOf("BINARY") != 0) {
						continue;
					}
					rec.setUsage(fields[index].toUpperCase());
				}
			} else {
				if (fields[index].toUpperCase().indexOf("COMP") != 0
						&& fields[index].toUpperCase().indexOf("BINARY") != 0) {
					continue;
				}
				rec.setUsage(fields[index].toUpperCase());
			}
		}
		return rec;
	}

	public static int calculateLength(final String pic) throws CopybookException {
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

	public static int calculateBytes(final String usage, final int length) {
		int bytes = 0;
		if ("DISPLAY".equalsIgnoreCase(usage) || "INPUT-STREAM".equalsIgnoreCase(usage)
				|| "RAW-TEXT".equalsIgnoreCase(usage)) {
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

	public static String calculateType(final String pic) {
		String result = null;
		if (pic.charAt(0) == 'S' || pic.charAt(0) == '9' || pic.lastIndexOf("9(") > -1) {
			result = "NUMERIC";
		} else if (pic.lastIndexOf(88) > -1) {
			result = "ALPHANUM";
		} else if (pic.lastIndexOf(65) > -1) {
			result = "ALPHABETIC";
		}
		return result;
	}

	public static String calculateSign(final String pic) {
		String result = null;
		if (pic.charAt(0) == 'S') {
			result = "implicit";
		} else if (pic.endsWith("-")) {
			result = "explicit";
		} else {
			result = "nosign";
		}
		return result;
	}



	private static String trimPeriod(final String in) {
		final int idx = in.lastIndexOf(".");
		if (idx > 0) {
			return in.substring(0, idx);
		}
		return in;
	}
}
