package template.appendix;

import *;

public abstract class AbstractNotificationService implements NotificationService {

    @InjectLogger
    protected Logger logger;

    @Inject
    protected transient UserQueryService userQueryService;

    // TODO @Inject with Atmosphere v2.3, when released
    protected AtmosphereFramework atmosphere() {
        return (AtmosphereFramework) ServletContextFactory.getDefault()
                .getServletContext().getAttribute("atmosphereServlet");
    }

    protected BroadcasterFactory broadcasterFactory() {
        return atmosphere().getBroadcasterFactory();
    }

    protected AtmosphereResourceSessionFactory sessionFactory() {
        return AtmosphereResourceSessionFactory.getDefault();
    }
    // end of to-do


    /**
     * publish event for specified broadcaster
     *
     * @param b
     * @param event
     */
    protected void broadcast(Broadcaster b, AtmosphereEvent event) {
        try {
            b.broadcast(event.toJSON());
        } catch (IOException e) {
            logger.error("Could not map to JSON", e);
        }
    }

    /**
     * publish event only for given resource using the specified broadcaster\n
     * TODO broadcaster may not necessary - cause default broadcaster is stored in resource
     *
     * @param b
     * @param event
     * @param resource
     */
    protected void broadcast(Broadcaster b, AtmosphereEvent event, AtmosphereResource resource) {
        try {
            b.broadcast(event.toJSON(), resource);
        } catch (IOException e) {
            logger.error("Could not map to JSON", e);
        }
    }
}
