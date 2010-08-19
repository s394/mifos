/*
 * Copyright (c) 2005-2010 Grameen Foundation USA
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * See also http://www.apache.org/licenses/LICENSE-2.0.html for an
 * explanation of the license and how it is applied.
 */

package org.mifos.framework.components.batchjobs.listeners;

import java.sql.Timestamp;

import org.mifos.framework.components.batchjobs.BatchJobListener;
import org.mifos.framework.components.batchjobs.MifosBatchJob;
import org.mifos.framework.components.batchjobs.SchedulerConstants;
import org.mifos.framework.components.batchjobs.business.Task;
import org.mifos.framework.components.batchjobs.helpers.TaskStatus;
import org.mifos.framework.components.batchjobs.persistence.TaskPersistence;
import org.mifos.framework.exceptions.PersistenceException;
import org.mifos.framework.util.DateTimeService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class GlobalBatchJobListener extends BatchJobListener {

    private final String name = SchedulerConstants.GLOBAL_JOB_LISTENER_NAME;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        String jobName = context.getJobDetail().getName();
        long currentTime = new DateTimeService().getCurrentDateTime().getMillis();
        registerBatchJobLaunch(jobName, currentTime);
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException exception) {
        String jobName = context.getJobDetail().getName();
        if(exception == null) {
            registerBatchJobResult(jobName, 0, SchedulerConstants.FINISHED_SUCCESSFULLY, TaskStatus.COMPLETE);
        } else {
            long currentTime = new DateTimeService().getCurrentDateTime().getMillis();
            registerBatchJobResult(jobName, currentTime, exception.getMessage(), TaskStatus.FAILED);
        }
    }

    public final void registerBatchJobLaunch(String batchJobName, long timeInMillis) {
        try {
            MifosBatchJob.batchJobStarted();
            requiresExclusiveAccess();
            Task task = new Task();
            task.setDescription(SchedulerConstants.START);
            task.setTask(batchJobName);
            task.setStatus(TaskStatus.INCOMPLETE);
            if (timeInMillis == 0) {
                task.setStartTime(new Timestamp(new DateTimeService().getCurrentDateTime().getMillis()));
            } else {
                task.setStartTime(new Timestamp(timeInMillis));
            }
            new TaskPersistence().saveAndCommitTask(task);
        } catch (PersistenceException pe) {
            getLogger().error("Failure while registering " + batchJobName + " launch", pe);
        }
    }

    public final void registerBatchJobResult(String batchJobName, long timeInMillis, String description, TaskStatus status) {
        try {
            Task task = new Task();
            task.setTask(batchJobName);
            task.setDescription(description);
            task.setStatus(status);
            if (timeInMillis == 0) {
                task.setEndTime(new Timestamp(new DateTimeService().getCurrentDateTime().getMillis()));
            } else {
                task.setEndTime(new Timestamp(timeInMillis));
            }
            new TaskPersistence().saveAndCommitTask(task);
        } catch (PersistenceException pe) {
            getLogger().error("Failure while registering " + batchJobName + " result", pe);
        } finally {
            MifosBatchJob.batchJobFinished();
        }
    }

    protected void requiresExclusiveAccess() {
        MifosBatchJob.batchJobRequiresExclusiveAccess(true);
    }

}
