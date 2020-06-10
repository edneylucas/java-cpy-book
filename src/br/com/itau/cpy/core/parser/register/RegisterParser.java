package br.com.itau.cpy.core.parser.register;

import br.com.itau.cpy.core.parser.book.model.CpyBookLine;
import br.com.itau.cpy.model.Register;
import br.com.itau.cpy.model.enuns.DependsType;

public class RegisterParser {

    public static Register parser(CpyBookLine value) {
        try {
	    	Register register = new Register();
	        
	    	register.setName(value.getName());
	        register.setMinOccurs(value.getMinOccurs());
	        register.setDependsOccurs(value.getDependingOn());
	        register.setHn(value.getHierarchyLevel());
	        
	        register.setDependsType(DependsType.NUMBER);
	        
	        if (value.getMaxOccurs() < value.getMinOccurs()) {
	            register.setMaxOccurs(register.getMinOccurs());
	        }
	        else {
	            register.setMaxOccurs(value.getMaxOccurs());
	        }
	        
	        return register;
		} catch (Exception e) {
			return null;
		}
    }
}
