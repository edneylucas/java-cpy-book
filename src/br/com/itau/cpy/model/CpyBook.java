package br.com.itau.cpy.model;

import br.com.itau.cpy.model.impls.CpyContainer;

public class CpyBook extends CpyContainer {
	
	private static final long serialVersionUID = -4957181541612636461L;

	private int totalBytes = 0;

	public int getTotalBytes() {
		return this.totalBytes;
	}

	public void setTotalBytes(int totalBytes) {
		this.totalBytes = totalBytes;
	}
}
