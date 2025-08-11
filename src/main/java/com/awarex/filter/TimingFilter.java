package com.awarex.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class TimingFilter implements Filter, ContainerRequestFilter, ContainerResponseFilter {
    private static final String START_TIME = "startTime";
    private static final Logger LOG = Logger.getLogger(TimingFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME, startTime);
        
        try {
            chain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            LOG.info(String.format("Request to %s took %d ms", 
                httpRequest.getRequestURI(), duration));
        }
    }

    @Override
    public void destroy() {
        // Cleanup if needed
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        requestContext.setProperty(START_TIME, System.currentTimeMillis());
    }

    @Override
    public void filter(ContainerRequestContext requestContext, 
                      ContainerResponseContext responseContext) {
        Long startTime = (Long) requestContext.getProperty(START_TIME);
        if (startTime != null) {
            long duration = System.currentTimeMillis() - startTime;
            String path = requestContext.getUriInfo().getPath();
            LOG.info(String.format("JAX-RS Request to %s took %d ms", path, duration));
        }
    }
}