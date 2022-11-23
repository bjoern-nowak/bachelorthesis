package com.oge.contrax.shared.infrastructure.config;

import *;

/**
 * Code-based alternative to web.xml for use within Servlet 3.0+ environments. See
 * {@link WebApplicationInitializer} Javadoc for complete details.
 */
public class VmsWebAppInitializer implements WebApplicationInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(VmsWebAppInitializer.class);
    public static final EnumSet<DispatcherType> ALL_DISPATCHERS = EnumSet.allOf(DispatcherType.class);


    /**
     * Register and configure all Servlet container components necessary to power the
     * Contrax web application.
     */
    @Override
    public void onStartup(ServletContext sc) throws ServletException {
        LOG.debug("{}.onStartup()", getClass().getName());

        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setServletContext(sc);

        // ...

        Dynamic atmosphereRegistration = registerServlet(sc, AtmosphereServlet.class, 2, "/notify/*");
        AtmosphereConfig.init(atmosphereRegistration);
    }

    private FilterRegistration.Dynamic addFilter(ServletContext sc, String filterName, Filter filter) {
        FilterRegistration.Dynamic dynamic = sc.addFilter(filterName, filter);
        dynamic.setAsyncSupported(true);
        return dynamic;
    }

    private FilterRegistration.Dynamic addFilter(ServletContext sc, String filterName, Class<? extends Filter> filterClass) {
        FilterRegistration.Dynamic dynamic = sc.addFilter(filterName, filterClass);
        dynamic.setAsyncSupported(true);
        return dynamic;
    }

    // ...
}
