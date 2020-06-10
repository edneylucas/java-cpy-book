package br.com.itau.cpy.model;

import br.com.itau.cpy.model.impls.CpyContainer;

public class Choice extends CpyContainer {
	
	private static final long serialVersionUID = 256298696560864910L;

	private String depends;

	public String getDepends() {
		return this.depends;
	}

	public void setDepends(String depends) {
		this.depends = depends;
	}
}
