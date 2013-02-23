package com.thoughtworks.test.matchers;

class Employee {
    private String name;
    private EAddress emailAddress;

    public Employee(String name, String emailString) {
        this.name = name;
        this.emailAddress = new EAddress(emailString);
    }

    @Override
    public String toString() {
        return "Employee {name=" + name + ", eaddress=" + emailAddress + "}";
    }
}
