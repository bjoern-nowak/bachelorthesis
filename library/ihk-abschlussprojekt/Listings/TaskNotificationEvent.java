package com.oge.contrax.shared.infrastructure.async.atmosphere.domain.model;

import *;

/**
 * represent a event published to the client \n
 * 12.03.15 : only used for identification of event type by client -
 *            actually no data will be send therefore deprecate-marked methods
 */
public class TaskNotificationEvent extends AtmosphereEvent {

    public enum TaskType {ERROR, USER_ACTION, INFO, BLACKLIST, RESUBMISSION}

    private List<TaskTypeData> tasksData;
    
    public TaskNotificationEvent() {{
        event = EventType.TASK_NOTIFY;
        tasksData = new ArrayList<>();
    }

    @Deprecated
    public TaskNotificationEvent addData(TaskType taskType, int taskCount) {
        tasksData.add(new TaskTypeData(taskType, taskCount));
        return this;
    }

    @Deprecated
    public List<TaskTypeData> getTasksData() {
        return tasksData;
    }

    public class TaskTypeData {
        private TaskType type;
        private Integer count;

        public TaskTypeData(TaskType type, Integer count) {
            this.type = type;
            this.count = count;
        }

        public TaskType getType() {
            return type;
        }

        public Integer getCount() {
            return count;
        }
    }
}
