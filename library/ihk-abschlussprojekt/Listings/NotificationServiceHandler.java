package com.oge.contrax.shared.infrastructure.async.atmosphere;

import *;

/**
 * general notification service endpoint \n
 * 13.03.15 : no need of different connection handling for different events
 */
@AtmosphereHandlerService(
        path = "/notify",
        supportSession = true,
        broadcasterCache = UUIDBroadcasterCache.class,
        interceptors = {
                AtmosphereResourceLifecycleInterceptor.class,
                AtmosphereResourceStateRecovery.class,
                OnDisconnectInterceptor.class,
                SessionCreationInterceptor.class,
                TrackMessageSizeInterceptor.class,
                WebSocketMessageSuspendInterceptor.class,
                IdleResourceInterceptor.class,
                HeartbeatInterceptor.class,
                JavaScriptProtocol.class})
public class NotificationServiceHandler
        extends AbstractReflectorAtmosphereHandler
        implements AtmosphereHandler, AtmosphereServletProcessor {

    private transient Logger logger = LoggerFactory.getLogger(NotificationServiceHandler.class);
    private transient BroadcasterFactory broadcasterFactory;
    private transient AtmosphereResourceSessionFactory sessionFactory;
    private Map<UserRole, Broadcaster> userRoleBroadcasterMap;

    @Override
    public void init(AtmosphereConfig config) throws ServletException {
        broadcasterFactory = config.getBroadcasterFactory();
        sessionFactory = AtmosphereResourceSessionFactory.getDefault();

        // init a list of broadcaster per any existing user role
        userRoleBroadcasterMap = new HashMap<>();
        List<UserRole> allRoles = UserRole.ALL_TENANT_USERS.asList();
        allRoles.addAll(UserRole.ALL_SYSTEM_USERS.asList());
        allRoles.addAll(UserRole.ALL_CUSTOMER_USERS.asList());
        for (UserRole role : allRoles) {
            Broadcaster b = lookupBroadcaster(role.getKey());
            userRoleBroadcasterMap.put(role, b);
        }
    }

    private Broadcaster lookupBroadcaster(String pathInfo) {
        String[] decodedPath = pathInfo.split("/");
        Broadcaster b = broadcasterFactory.lookup(decodedPath[decodedPath.length - 1], true);
        return b;
    }

    @Override
    public void destroy() {
    }

    /**
     * is called for every request of service endpoint ("/notify")
     * @param resource represent a http connection wrapped by atmosphere
     * @throws IOException
     */
    @Override
    public void onRequest(AtmosphereResource resource) throws IOException {
        AtmosphereRequest request = resource.getRequest();
        AtmosphereResponse response = resource.getResponse();
        String method = request.getMethod();
        VmsUser user = SecurityUtils.getCurrentUser();

        if ("GET".equalsIgnoreCase(method)) {
            resource.addEventListener(new WebSocketEventListenerAdapter());
            response.setContentType("application/json");

            // after this the actual connection will be suspended and receive a heartbeat per interval
            resource.suspend();

            // every resource have a session - lives as long a connection is alive
            // and stores user id for later identification of an resource
            AtmosphereResourceSession resourceSession = sessionFactory.getSession(resource);
            resourceSession.setAttribute("vmsUserId", SecurityUtils.getCurrentUserId());

            for (UserRole role : SecurityUtils.getCurrentUserRoles()) {
                Broadcaster b = userRoleBroadcasterMap.get(role);
                if (b != null) {
                    b.addAtmosphereResource(resource);
                }
            }

            logger.trace("User {} with browser {} requested {}",
                    new String[]{user.getUsername(), resource.uuid(), request.getPathInfo()});
        } else if ("POST".equalsIgnoreCase(method)) {
            logger.debug("(No action done) Browser {} has send '{}'", resource.uuid(), request.getReader().readLine().trim());
        }
    }

    // if control of connection handling is needed - working example implementation
    /*
	    ...
    */
}
