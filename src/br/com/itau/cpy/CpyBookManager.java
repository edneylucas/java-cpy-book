package br.com.itau.cpy;

import br.com.itau.cpy.exception.CopybookException;
import br.com.itau.cpy.model.CpyBook;

public final class CpyBookManager extends ManagementCpyBookParser {

	private static ManagementCpyBookParser management = new ManagementCpyBookParser();
	
	public static CpyBook generate(String value, String name) throws CopybookException {
		try {
			return management.execute(value, name);
		} catch (Exception e) {
			throw new CopybookException(e);
		}
	}
}
