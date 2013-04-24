package com.thoughtworks.webstub.stub.matcher;

import com.thoughtworks.webstub.config.HttpConfiguration;
import com.thoughtworks.webstub.config.Header;
import com.thoughtworks.webstub.utils.Predicate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;

import static com.thoughtworks.webstub.utils.CollectionUtils.exists;
import static javax.servlet.http.HttpServletResponse.SC_PRECONDITION_FAILED;

public class HeadersMatcher extends RequestPartMatcher {
    public HeadersMatcher(HttpServletRequest request) {
        super(request, SC_PRECONDITION_FAILED);
    }

    @Override
    public boolean matches(HttpConfiguration configuration) throws IOException {
        return !exists(getHeaders(configuration), new Predicate<Header>() {
            @Override
            public boolean satisfies(Header configuredHeader) {
                return headerAbsentFromRequest(configuredHeader, request);
            }
        });
    }

    private Collection<Header> getHeaders(HttpConfiguration configuration) {
        return configuration.request().headers();
    }

    private boolean headerAbsentFromRequest(Header configuredHeader, HttpServletRequest request) {
        String found = request.getHeader(configuredHeader.name());
        return (found == null) || !found.equals(configuredHeader.value()) ;
    }
}
