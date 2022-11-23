package com.oge.contrax.task.domain.query;

import *;

public interface TaskQueryService extends QueryService<Task, TaskNumber> {

    // ...

    /**
     * count tasks per specified types for given user and checks if there are new tasks since given lastVisit
     * @param user filter for specific user
     * @return the statistical figures for the ui task notification
     */
    TaskNotificationStatisticsModel getTaskNotificationModel(VmsUser user, DateTime lastVisit);

    // ...
}
