package br.com.itau.cpy.model.impls;

import java.io.Serializable;

public abstract class CpyElement implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private CpyContainer parent;

	private String name;

	private int hn;

	public CpyElement() {
		this.parent = null;
		this.name = null;
	}

	public CpyElement(String newName) {
		this.name = newName;
	}

	public CpyContainer getParent() {
		return this.parent;
	}

	public void setParent(CpyContainer newParent) {
		this.parent = newParent;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		this.name = newName;
	}

	public int getHn() {
		return this.hn;
	}

	public void setHn(int hn) {
		this.hn = hn;
	}
}
