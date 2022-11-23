package com.oge.contrax.party.view.user;

import *;

@Controller("notifications")
@Scope("session")
public class NotificationController implements Serializable {

    @InjectLogger
    private Logger logger;

    @Inject
    private transient TaskQueryService taskQueryService;

    @Inject
    private transient UserStatusRepository userStatusRepository;

    private TaskNotificationStatisticsModel model;


    public void resetTaskTypesStatistic() {
        model = null;
    }

    public TaskNotificationStatisticsModel getTaskStatistic() {
        if (model == null) {
            updateModel();
        }
        return model;
    }

    /**
     * will be called by OpenTaskListController, when ever a update is needed
     * @see com.oge.contrax.task.view.OpenTaskListController#visitedOpenTasks()
     */
    public void updateModel() {
        model = taskQueryService.getTaskNotificationModel(
                SecurityUtils.getCurrentUser(),
                userStatusRepository.getLastOpenTaskVisit(SecurityUtils.getCurrentUserId()));
    }
}
