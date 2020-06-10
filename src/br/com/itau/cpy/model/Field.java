package br.com.itau.cpy.model;

import br.com.itau.cpy.model.enuns.DependsType;
import br.com.itau.cpy.model.enuns.Sign;
import br.com.itau.cpy.model.enuns.Type;
import br.com.itau.cpy.model.enuns.Usage;
import br.com.itau.cpy.model.impls.CpyElement;

public class Field extends CpyElement {
	
	private static final long serialVersionUID = -546223039536302035L;

	private String pic = null;

	private Type type = null;

	private Usage usage = null;

	private int length = 0;

	private int bytes = 0;

	private int decimals = 0;

	private int minOccurs = 1;

	private int maxOccurs = 1;

	private String dependsOccurs = null;

	private DependsType dependsType = null;

	private String dependsValues = null;

	private String value = null;

	private Sign sign = null;

	public Field() {
	}

	public Field(String name) {
		super(name);
	}

	public String getPIC() {
		return this.pic;
	}

	public void setPIC(String newPIC) {
		this.pic = newPIC;
	}

	public Usage getUsage() {
		return this.usage;
	}

	public void setUsage(Usage newUsage) {
		this.usage = newUsage;
	}

	public Type getType() {
		return this.type;
	}

	public void setType(Type newType) {
		this.type = newType;
	}

	public int getLength() {
		return this.length;
	}

	public void setLength(int newLength) {
		this.length = newLength;
	}

	public int getBytes() {
		return this.bytes;
	}

	public void setBytes(int newBytes) {
		this.bytes = newBytes;
	}

	public int getDecimals() {
		return this.decimals;
	}

	public void setDecimals(int newDecimals) {
		this.decimals = newDecimals;
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

	public String getValue() {
		return this.value;
	}

	public void setValue(String newValue) {
		this.value = newValue;
	}

	public Sign getSign() {
		return this.sign;
	}

	public void setSign(Sign newSign) {
		this.sign = newSign;
	}
}
