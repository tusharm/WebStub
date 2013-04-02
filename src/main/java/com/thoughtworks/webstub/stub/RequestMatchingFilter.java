package com.thoughtworks.webstub.stub;

import com.thoughtworks.webstub.stub.matcher.ContentMatcher;
import com.thoughtworks.webstub.stub.matcher.HeadersMatcher;
import com.thoughtworks.webstub.stub.matcher.MethodMatcher;
import com.thoughtworks.webstub.stub.matcher.UriMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestMatchingFilter implements Filter {
    public static final String CONFIGURATION_ATTRIBUTE_NAME = "stub.configuration";

    private final Configurations configurations;

    RequestMatchingFilter(Configurations configurations) {
        this.configurations = configurations;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;

        try {
            Configurations filtered = configurations
                    .filterBy(new MethodMatcher(request))
                    .filterBy(new UriMatcher(request))
                    .filterBy(new HeadersMatcher(request))
                    .filterBy(new ContentMatcher(request));

            req.setAttribute(CONFIGURATION_ATTRIBUTE_NAME, filtered.all().get(0));
            chain.doFilter(req, resp);

        } catch (ConfigurationNotFoundException e) {
            ((HttpServletResponse) resp).setStatus(e.getFailedMatcher().failedResponseCode());
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
