package com.thoughtworks.webstub;

import static com.thoughtworks.webstub.StubServer.newServer;

public class WebConsoleTest {
    public static void main(String[] args) {
        newServer(9000).withWebConsole().start();
    }
}
