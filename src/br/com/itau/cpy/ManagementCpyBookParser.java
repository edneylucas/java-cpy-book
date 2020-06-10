package br.com.itau.cpy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import br.com.itau.cpy.core.file.BookFile;
import br.com.itau.cpy.core.parser.book.CobolBookParser;
import br.com.itau.cpy.core.parser.book.model.CpyBookLine;
import br.com.itau.cpy.core.parser.field.FieldParser;
import br.com.itau.cpy.core.parser.register.RegisterParser;
import br.com.itau.cpy.exception.CopybookException;
import br.com.itau.cpy.model.Choice;
import br.com.itau.cpy.model.CpyBook;
import br.com.itau.cpy.model.Field;
import br.com.itau.cpy.model.Register;
import br.com.itau.cpy.model.impls.CpyContainer;
import br.com.itau.cpy.model.impls.CpyElement;

class ManagementCpyBookParser {
	
	private static final String EMPTY_VALUE = "";
	private static final Integer COBOL_COMMENT = Integer.valueOf(88);
	
	public CpyBook execute(String value, String name) throws CopybookException {
		CpyBook copy = null;
		try {
			copy = readCobolString(new BufferedReader(new StringReader(value)));
			copy.setName(name);
			copy.setTotalBytes(calculateTotalBytes(copy));
		} catch (Exception e) {
			throw new CopybookException(e);
		}
		return copy;
	}
	
	private static CpyBook readCobolString(BufferedReader bufferedReader) throws CopybookException, IOException {
		CpyBook root = new CpyBook();

		try {
			CpyContainer parent = (CpyContainer) root;
			CpyElement previousElement = (CpyElement) root;
			
			String line = null;
			do {
				line = BookFile.getFullLine(bufferedReader);
				if (line != null && !EMPTY_VALUE.equals(line.trim())) {
					CpyBookLine cobolBookModel = CobolBookParser.parseCobolBook(line);
					if (COBOL_COMMENT.equals(cobolBookModel.getHierarchyLevel())) {
						continue;
					} else {
						parent = processRedefines(parent, cobolBookModel, previousElement);
						CpyElement element = getElement(cobolBookModel);
						
						element.setParent(parent);
						parent.addElement(element);
						
						previousElement = element;
					}
				}
			}
			while (line != null);
		} catch (Exception e) {
			throw new CopybookException((Throwable) e);
		} finally {
			bufferedReader.close();
		}
		return root;
	}
	
	private static int calculateTotalBytes(CpyBook copy) {
		return parseChildren((CpyContainer) copy);
	}
	
	private static CpyElement getElement(CpyBookLine cobolBookModel) {
		if (cobolBookModel.getPicture() == null) {
			return (CpyElement) RegisterParser.parser(cobolBookModel);
		} else {
			return (CpyElement) FieldParser.parser(cobolBookModel);
		}
	}
	
	private static CpyContainer getTransactionCopyContainer(CpyBookLine cobolBookModel, CpyElement previous, CpyContainer parent) {
		if (cobolBookModel.getHierarchyLevel() > previous.getHn()) {
			return (CpyContainer) previous;
		} else if (cobolBookModel.getHierarchyLevel() < previous.getHn()) {
			return getAncestor(parent, cobolBookModel.getHierarchyLevel(), cobolBookModel.getRedefines());
		}
		return parent;
	}

	private static CpyContainer getAncestor(CpyContainer parent, final int hn, final String redefines) {
		while (parent.getHn() >= hn || (parent instanceof Choice && redefines == null)) {
			parent = parent.getParent();
		}
		return parent;
	}
	
	private static CpyContainer processRedefines(CpyContainer parentH, CpyBookLine cobolBookModel, CpyElement previous) {
		CpyContainer parent = getTransactionCopyContainer(cobolBookModel, previous, parentH);
		if (cobolBookModel.getRedefines() != null) {
			if (!(parent instanceof Choice)) {
				Choice choice = new Choice();
				CpyElement redefined = parent.getElement(cobolBookModel.getRedefines());
				parent.removeElement(redefined);
				choice.setName(redefined.getName());
				choice.setParent(parent);
				parent.addElement((CpyElement) choice);
				redefined.setParent((CpyContainer) choice);
				choice.addElement(redefined);
				parent = (CpyContainer) choice;
			}
		} else if (parent instanceof Choice) {
			parent = parent.getParent();
		}
		return parent;
	}
	
	private static int parseChildren(CpyContainer tcParent) {
		int totalBytes = 0;

		List<CpyElement> children = tcParent.getElements();
		Iterator<CpyElement> it = children.iterator();
		while (it.hasNext()) {
			CpyElement tcChild = (CpyElement) it.next();

			if (tcParent instanceof Choice) {
				int bytes = 0;
				if (tcChild instanceof Register) {
					bytes = parseRegister((Register) tcChild);
				} else if (tcChild instanceof Choice) {
					bytes = parseChoice((Choice) tcChild);
				} else if (tcChild instanceof Field) {
					bytes = parseField((Field) tcChild);
				}
				if (bytes > totalBytes)
					totalBytes = bytes;
				continue;
			}
			if (tcChild instanceof Register) {
				totalBytes += parseRegister((Register) tcChild);
				continue;
			}
			if (tcChild instanceof Choice) {
				totalBytes += parseChoice((Choice) tcChild);
				continue;
			}
			if (tcChild instanceof Field) {
				totalBytes += parseField((Field) tcChild);
			}
		}

		return totalBytes;
	}

	private static int parseRegister(final Register register) {
		final int maxOccurs = register.getMaxOccurs();
		final int bytesChildren = parseChildren((CpyContainer) register);
		return bytesChildren * maxOccurs;
	}

	private static int parseChoice(final Choice choice) {
		return parseChildren((CpyContainer) choice);
	}

	private static int parseField(final Field field) {
		final int bytes = field.getBytes();
		final int maxOccurs = field.getMaxOccurs();
		return bytes * maxOccurs;
	}
}
