package com.thoughtworks.test.matchers;

class EAddress {
    private String email;

    public EAddress(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "EAddress {email=" + email + "}";
    }
}
