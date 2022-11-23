package com.oge.contrax.party.domain.repository;

import *;

@Singleton
@Component
public class UserStatusRepository {

    private Map<VmsUserId, UserStatus> repository;

    public UserStatusRepository() {
        this.repository = new HashMap<>();
    }

    public UserStatus get(VmsUserId vmsUserId) {
        UserStatus userStatus = fetch(vmsUserId);
        return clone(userStatus);
    }

    public UserStatus remove(VmsUserId vmsUserId) {
        UserStatus userStatus = repository.remove(vmsUserId);
        return clone(userStatus);
    }

    private UserStatus store(VmsUserId vmsUserId) {
        repository.put(vmsUserId, new UserStatus(vmsUserId));
        return get(vmsUserId);
    }

    private UserStatus fetch(VmsUserId vmsUserId) {
        UserStatus userStatus = repository.get(vmsUserId);
        if (userStatus == null) {
            userStatus = store(vmsUserId);
        }
        return userStatus;
    }

    private UserStatus clone(UserStatus userStatus) {
        UserStatus clone = null;
        if (userStatus != null) {
            clone = userStatus.clone();
        }
        return clone;
    }

    // UserStatus getter and setter

    public void updateLastOpenTaskVisit(VmsUserId vmsUserId, DateTime visit) {
        UserStatus userStatus = fetch(vmsUserId);
        userStatus.setLastOpenTaskVisit(visit);
    }

    public DateTime getLastOpenTaskVisit(VmsUserId vmsUserId) {
        DateTime lastOpenTaskVisit = get(vmsUserId).getLastOpenTaskVisit();
        if (lastOpenTaskVisit == null) {
            lastOpenTaskVisit = TimeUtils.BEGIN_OF_TIME;
        }
        return lastOpenTaskVisit;
    }

}
