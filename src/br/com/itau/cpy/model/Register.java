package br.com.itau.cpy.model;

import br.com.itau.cpy.model.enuns.DependsType;
import br.com.itau.cpy.model.impls.CpyContainer;

public class Register extends CpyContainer {

	private static final long serialVersionUID = 8478691796284425143L;

	private int minOccurs = 1;

	private int maxOccurs = 1;

	private String dependsOccurs = null;

	private DependsType dependsType = null;

	private String dependsValues = null;

	public Register() {
	}

	public Register(String name) {
		super(name);
	}

	public int getMinOccurs() {
		return this.minOccurs;
	}

	public void setMinOccurs(int newMinOccurs) {
		this.minOccurs = newMinOccurs;
	}

	public int getMaxOccurs() {
		return this.maxOccurs;
	}

	public void setMaxOccurs(int newMaxOccurs) {
		this.maxOccurs = newMaxOccurs;
	}

	public String getDependsOccurs() {
		return this.dependsOccurs;
	}

	public void setDependsOccurs(String newDependsOccurs) {
		this.dependsOccurs = newDependsOccurs;
	}

	public DependsType getDependsType() {
		return this.dependsType;
	}

	public void setDependsType(DependsType dependsType) {
		this.dependsType = dependsType;
	}

	public String getDependsValues() {
		return this.dependsValues;
	}

	public void setDependsValues(String newDependsValues) {
		this.dependsValues = newDependsValues;
	}
}
