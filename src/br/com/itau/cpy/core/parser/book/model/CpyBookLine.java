package br.com.itau.cpy.core.parser.book.model;

public class CpyBookLine {

	private Integer hierarchyLevel;

	private String name;

	private String picture;

	private String value;

	private int minOccurs = 1;

	private int maxOccurs = 1;

	private String dependingOn;

	private String indexedBy;

	private String redefines;

	private String usage = "DISPLAY";

	public String getDependingOn() {
		return this.dependingOn;
	}

	public void setDependingOn(String pDependingOn) {
		this.dependingOn = pDependingOn;
	}

	public Integer getHierarchyLevel() {
		return this.hierarchyLevel;
	}

	public void setHierarchyLevel(Integer pHn) {
		this.hierarchyLevel = pHn;
	}

	public String getIndexedBy() {
		return this.indexedBy;
	}

	public void setIndexedBy(String pIndexedBy) {
		this.indexedBy = pIndexedBy;
	}

	public int getMaxOccurs() {
		return this.maxOccurs;
	}

	public void setMaxOccurs(int pMaxOccurs) {
		this.maxOccurs = pMaxOccurs;
	}

	public int getMinOccurs() {
		return this.minOccurs;
	}

	public void setMinOccurs(int pMinOccurs) {
		this.minOccurs = pMinOccurs;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String pName) {
		this.name = pName;
	}

	public String getPicture() {
		return this.picture;
	}

	public void setPicture(String pPicture) {
		this.picture = pPicture;
	}

	public String getRedefines() {
		return this.redefines;
	}

	public void setRedefines(String pRedefines) {
		this.redefines = pRedefines;
	}

	public String getUsage() {
		return this.usage;
	}

	public void setUsage(String pUsage) {
		this.usage = pUsage;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String pValue) {
		this.value = pValue;
	}
}
