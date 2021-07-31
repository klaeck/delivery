package enums;

import exceptions.ErrorPageException;

public enum Role {
    user,
    manager;

    public static Role getRole(String role) throws ErrorPageException {
        if (role.equals("user")) {
            return user;
        } else if (role.equals("manager")){
            return manager;
        } else throw new ErrorPageException();
    }
}
