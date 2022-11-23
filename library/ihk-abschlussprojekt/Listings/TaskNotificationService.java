package com.oge.contrax.shared.infrastructure.async.atmosphere.domain.service.impl;

import *;

/**
 * service to publish task events
 */
@Service
public class TaskNotificationService extends AbstractNotificationService {
    private final String mainBroadcasterPath = "/notify";

    @Override
    public void pushNotification(VmsUserId targetUserId) {
        Broadcaster b = broadcasterFactory().lookup(mainBroadcasterPath, false);
        if (b != null) {
            for (AtmosphereResource resource : b.getAtmosphereResources()) {
                AtmosphereResourceSession resourceSession = sessionFactory().getSession(resource, false);
                if (resourceSession != null) {
                    VmsUserId resourceUserId = resourceSession.getAttribute("vmsUserId", VmsUserId.class);
                    if (resourceUserId.equals(targetUserId)) {
                        broadcast(b, new TaskNotificationEvent(), resource);
                        return;
                    }
                }
                logger.debug("There is no session for {}", targetUserId);
            }
        } else {
            logger.debug("There is no broadcaster for \"" + mainBroadcasterPath + "\"");
        }
    }

    @Override
    public void pushNotification(VmsUser targetUser) {
        pushNotification(targetUser.getUserId());
    }

    @Override
    public void pushNotification(UserRole role) {
        Broadcaster b = broadcasterFactory().lookup(role.getKey(), false);
        if (b != null) {
            broadcast(b, new TaskNotificationEvent());
        } else {
            logger.debug("There is no broadcaster for role {}", role.getKey());
        }
    }

    // example push services for event including data
    /*
	    ...
    */
}
