package br.com.itau.cpy.model.impls;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class CpyContainer extends CpyElement {
	
	private static final long serialVersionUID = 1L;

	private List<CpyElement> elements;

	public CpyContainer() {
		this.elements = new ArrayList<CpyElement>();
	}

	public CpyContainer(String name) {
		super(name);
		this.elements = new ArrayList<CpyElement>();
	}

	public void addElement(CpyElement element) {
		element.setParent(this);
		this.elements.add(element);
	}

	public void addElements(List<CpyElement> elements) {
		for (int i = 0; i < elements.size(); i++) {
			CpyElement element = elements.get(i);
			addElement(element);
		}
	}

	public CpyElement getElement(String name) {
		CpyElement element = null;
		Iterator<CpyElement> itElements = this.elements.iterator();
		while (element == null && itElements.hasNext()) {
			element = itElements.next();
			if (!element.getName().equals(name))
				element = null;
		}
		return element;
	}

	public List<CpyElement> getElements() {
		return this.elements;
	}

	public void removeElement(CpyElement element) {
		this.elements.remove(element);
	}
}
