package com.oge.contrax.task.domain.query.impl;

import *;

@Service
@Transactional(readOnly = true, value = TenantDataConfig.TX_MANAGER_NAME)
public class TaskQueryServiceImpl extends QueryServiceSupportJpa<Task, TaskNumber> implements TaskQueryService {

    // ...
    
    /**
     * {@inheritDoc}
     */
    @Override
    public TaskNotificationStatisticsModel getTaskNotificationModel(VmsUser user, DateTime lastVisit) {
        TaskNotificationStatisticsModel model = new TaskNotificationStatisticsModel();

        if (user != null) {
            Query query = em().createNativeQuery(
                    "SELECT 'errorTaskCount', COUNT(t.PK) FROM Task t "
                        + " WHERE (t.OWNERGROUP_PK IN (:roles) "
                            + " OR t.EDITOR_UID = :user) "
                            + " AND t.closedAt IS NULL "
                            + " AND t.category = 0 "
                    + " UNION "
                    + " SELECT 'userActionTaskCount', COUNT(t.PK) FROM Task t "
                        + " WHERE (t.OWNERGROUP_PK IN (:roles) "
                            + " OR t.EDITOR_UID = :user) "
                            + " AND t.closedAt IS NULL "
                            + " AND t.category = 1 "
                    + " UNION "
                    + " SELECT 'hasNewUserActionTask', COUNT(t.PK) FROM Task t "
                        + " WHERE (t.OWNERGROUP_PK IN (:roles) "
                            + " OR t.EDITOR_UID = :user) "
                            + " AND t.closedAt IS NULL "
                            + " AND t.category = 1 "
                            + " AND t.created_At > :lastVisit "
                    + " UNION "
                    + " SELECT 'hasNewErrorTask', COUNT(t.PK) FROM Task t "
                        + " WHERE (t.OWNERGROUP_PK IN (:roles) "
                            + " OR t.EDITOR_UID = :user) "
                            + " AND t.closedAt IS NULL "
                            + " AND t.category = 0 "
                            + " AND t.created_At > :lastVisit ");

            List<String> roles = new ArrayList<>();
            for (UserRole userRole : user.getRoles()) {
                roles.add(userRole.name());
            }
            query.setParameter("user", user.getUserId().getValue());
            query.setParameter("roles", roles);
            query.setParameter("lastVisit", new Timestamp(lastVisit.getMillis()));

            List<Object[]> result = (List<Object[]>) query.getResultList();
            for (Object[] entry : result) {
                String type = (String) entry[0];
                int count = ((BigDecimal) entry[1]).intValue();
                boolean value = count > 0;

                switch (type) {
                    case "errorTaskCount":
                        model.setErrorTaskCount(count);
                        break;
                    case "userActionTaskCount":
                        model.setUserActionTaskCount(count);
                        break;
                    case "hasNewUserActionTask":
                        model.setHasNewUserActionTasks(value);
                        break;
                    case "hasNewErrorTask":
                        model.setHasNewErrorTasks(value);
                        break;
                }
            }
        }
        return model;
    }

    // ...
}
