package com.oge.contrax.party.domain.model.user;

import *;

public class UserStatus {

    private VmsUserId userId;

    private DateTime lastOpenTaskVisit;

    public UserStatus(VmsUserId userId) {
        this.userId = userId;
    }

    public UserStatus(UserStatus copyOf) {
        this.userId = copyOf.userId;
        this.lastOpenTaskVisit = copyOf.lastOpenTaskVisit;
    }

    public DateTime getLastOpenTaskVisit() {
        return lastOpenTaskVisit;
    }

    /**
     * should only be used by repository, else changes wont affect in repository
     * @param lastOpenTaskVisit
     * @see com.oge.contrax.party.domain.repository.UserStatusRepository#updateLastOpenTaskVisit
     */
    public void setLastOpenTaskVisit(DateTime lastOpenTaskVisit) {
        this.lastOpenTaskVisit = lastOpenTaskVisit;
    }

    /**
     * used by repository
     * @see com.oge.contrax.party.domain.repository.UserStatusRepository#clone
     */
    public UserStatus clone() {
        return new UserStatus(this);
    }
}
