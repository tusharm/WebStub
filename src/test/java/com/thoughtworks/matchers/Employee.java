package com.thoughtworks.matchers;

class Employee {
    private long id;
    private String name;
    private EAddress emailAddress;

    public Employee(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.emailAddress = new EAddress(email);
    }

    @Override
    public String toString() {
        return "Employee {id=" + id + "name=" + name + ", eaddress=" + emailAddress + "}";
    }
}
