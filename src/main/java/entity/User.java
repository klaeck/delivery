package entity;

import enums.Role;

public class User {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String phone;
    private String password;
    private final Role role;

    public User(int id, String firstName, String lastName, String phone, String password, Role role) {
        this.id = id;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("User: %s\nFirst name: %s\nLast name: %s\nrole: %s", phone, firstName, lastName, role);
    }
}
