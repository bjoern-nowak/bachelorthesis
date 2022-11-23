package com.oge.contrax.task.view;

import *;

@Controller("openTaskList")
@Scope("view")
public class OpenTaskListController extends AbstractTaskListController {

    private static final long serialVersionUID = -5685323931682143251L;

    @Inject
    private transient OpenTaskFilter filter;

    @Inject
    private transient UserStatusRepository userStatusRepository;

    @Inject
    private transient NotificationController notificationController;

    @PostConstruct
    @Override
    protected void init() {
        super.init();
    }

    public String showOnlyActionTasks() {
        OpenTaskFilter newFilter = new OpenTaskFilter(SecurityUtils.getCurrentUser(), SecurityUtils.getCurrentUserRoles());
        newFilter.setIncludeUserAction(true);
        newFilter.setIncludeError(false);
        newFilter.setIncludeInfo(false);
        newFilter.setIncludeBlacklisted(false);
        newFilter.setIncludeResubmissions(false);
        filter.setPriorFilter(newFilter);
        return View.TASK_LIST_OPEN_VIEW.redirect().outcome();
    }

    public String showOnlyErrorTasks() {
        OpenTaskFilter newFilter = new OpenTaskFilter(SecurityUtils.getCurrentUser(), SecurityUtils.getCurrentUserRoles());
        newFilter.setIncludeUserAction(false);
        newFilter.setIncludeError(true);
        newFilter.setIncludeInfo(false);
        newFilter.setIncludeBlacklisted(false);
        newFilter.setIncludeResubmissions(false);
        filter.setPriorFilter(newFilter);
        return View.TASK_LIST_OPEN_VIEW.redirect().outcome();
    }

    public void visitedOpenTasks() {
        DateTime visitedAt = TimeUtils.now();
        userStatusRepository.updateLastOpenTaskVisit(SecurityUtils.getCurrentUserId(), visitedAt);
        notificationController.updateModel();

        logger.debug("User " + SecurityUtils.getCurrentUsername() + "(" + SecurityUtils.getCurrentUserId()
                + ") visited open tasks list at " + visitedAt);
    }

    public DateTime getLastVisitOpenTask() {
        // exclude requests by ajax i.e.
        if (FacesContext.getCurrentInstance().isPostback()) {
            return null;
        }
        return userStatusRepository.getLastOpenTaskVisit(SecurityUtils.getCurrentUserId());
    }

    @Override
    public TaskFilter getFilter() {
        return filter.get();
    }
}
