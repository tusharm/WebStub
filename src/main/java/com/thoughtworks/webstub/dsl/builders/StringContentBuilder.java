package com.thoughtworks.webstub.dsl.builders;

public class StringContentBuilder implements ContentBuilder {
    private String content;

    public StringContentBuilder(String content) {
        this.content = content;
    }

    @Override
    public String build() {
        return content;
    }
}
