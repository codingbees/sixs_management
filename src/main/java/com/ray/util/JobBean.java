package com.ray.util;


    public class JobBean {

        /** id */
        private String jobId;

        /** 类名 */
        private String jobClass;

        /** 时间表达式 */
        private String cronExpression;

        /** 任务名称 */
        private String jobName;

        public String getJobId() {
            return jobId;
        }

        public void setJobId(String jobId) {
            this.jobId = jobId;
        }

        public String getJobClass() {
            return jobClass;
        }

        public void setJobClass(String jobClass) {
            this.jobClass = jobClass;
        }

        public String getCronExpression() {
            return cronExpression;
        }

        public void setCronExpression(String cronExpression) {
            this.cronExpression = cronExpression;
        }

        public String getJobName() {
            return jobName;
        }

        public void setJobName(String jobName) {
            this.jobName = jobName;
        }

        public JobBean(String jobId, String jobClass, String cronExpression, String jobName) {
            this.jobId = jobId;
            this.jobClass = jobClass;
            this.cronExpression = cronExpression;
            this.jobName = jobName;
        }

        public JobBean() {
            super();
        }

    }

