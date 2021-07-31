package enums;

import exceptions.ErrorPageException;

public enum Type {
    fragile,
    insured,
    regular;

    public static Type getType(String type) throws ErrorPageException {
        if(type.equalsIgnoreCase(regular.name())){
            return regular;
        } else if (type.equalsIgnoreCase(insured.name())){
            return insured;
        } else if (type.equalsIgnoreCase(fragile.name())){
            return fragile;
        }
        throw new ErrorPageException();
    }
}
