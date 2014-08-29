package com.thoughtworks.webstub.context.matcher;


import com.thoughtworks.webstub.config.HttpConfiguration;
import org.apache.oro.text.GlobCompiler;
import org.apache.oro.text.regex.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class UriMatcher extends RequestPartMatcher {
    public UriMatcher(HttpServletRequest request) {
        super(request, SC_NOT_FOUND);
    }

    @Override
    public boolean matches(HttpConfiguration configuration) throws IOException {
        try {
            String configuredUri = getUri(configuration);
            return new GlobPattern(configuredUri).matches(request.getServletPath());
        } catch (MalformedPatternException e) {
            throw new IOException(e);
        }
    }

    private String getUri(HttpConfiguration configuration) {
        return configuration.request().uri().split("\\?")[0];
    }

    private class GlobPattern {
        private PatternCompiler compiler = new GlobCompiler();
        private PatternMatcher matcher = new Perl5Matcher();
        private Pattern pattern;

        public GlobPattern(String patternString) throws MalformedPatternException {
            this.pattern = compiler.compile(patternString);
        }

        public boolean matches(String input) {
            return matcher.matches(input, pattern);
        }
    }
}
