package com.thoughtworks.webstub.stub;

import com.thoughtworks.webstub.stub.matcher.ConfigurationMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class StubFilter implements Filter {
    private Collection<? extends ConfigurationMatcher> matchers;

    public StubFilter(Collection<? extends ConfigurationMatcher> matchers) {
        this.matchers = matchers;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        for (ConfigurationMatcher matcher : matchers) {
            if (!matcher.matches(request)) {
                response.sendError(matcher.failedResponseCode());
                return;
            }
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
