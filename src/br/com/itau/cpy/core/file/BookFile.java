package br.com.itau.cpy.core.file;

import java.io.BufferedReader;
import java.io.IOException;

import br.com.itau.cpy.exception.CopybookException;

public class BookFile {

	public static String getFullLine(BufferedReader bufferedReader) throws CopybookException {
		String result = null;
		try {
			boolean end = false;
			String aux = null;
			result = "";
			while (!end) {
				aux = bufferedReader.readLine();
				if (aux == null) {
					end = true;
					if (result.equals("")) {
						return null;
					}
					return result;
				} else {
					if (aux.trim().length() > 0 && aux.trim().charAt(0) == '*') {
						continue;
					}
					result = result.concat(aux);
					if (result.trim().length() <= 0 || result.trim().charAt(result.trim().length() - 1) != '.') {
						continue;
					}
					end = true;
				}
			}
		} catch (IOException ioe) {
			return null;
		}
		return result;
	}

}
