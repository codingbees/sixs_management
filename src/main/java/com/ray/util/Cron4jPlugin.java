package com.ray.util;


import it.sauronsoftware.cron4j.InvalidPatternException;
import it.sauronsoftware.cron4j.Scheduler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import com.jfinal.plugin.IPlugin;

public class Cron4jPlugin implements IPlugin {

    // 任务调度器
    private static Scheduler scheduler = null;

    // 任务
    private static List<JobBean> jobs = new ArrayList<JobBean>();

    // 任务配置文件
    private String config = "job.properties";
    private Properties properties;

    public Cron4jPlugin(String config) {
        this.config = config;
    }

    public Cron4jPlugin() {

    }

    /**
     * 启动任务调度器
     */
    public boolean start() {
        scheduler = new Scheduler();
        // 加载配置文件
        loadProperties();
        Enumeration enums = properties.keys();

        // 解析配置文件
        while (enums.hasMoreElements()) {
            String key = enums.nextElement() + "";
            if (!key.endsWith("job")) {
                continue;
            }
            String cronKey = key.substring(0, key.indexOf("job")) + "cron";
            String enable = key.substring(0, key.indexOf("job")) + "enable";
            String name = key.substring(0, key.indexOf("job")) + "name";
            // 判断是否自启动
            if (isDisableJob(enable)) {
                continue;
            }
            String jobClassName = properties.get(key) + "";
            String jobCronExp = properties.getProperty(cronKey) + "";
            String jobName = properties.getProperty(name) + "";
            Class clazz;
            try {
                clazz = Class.forName(jobClassName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                // 添加一个调度任务;任务创建成功后会返回一个String类型的id,后续可以通过这个id操作这个任务
                String jobId = scheduler.schedule(jobCronExp, (Runnable) clazz.newInstance());
                jobs.add(createJob(jobId,jobClassName,jobCronExp,jobName));
            } catch (InvalidPatternException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        // 启动调度器
        scheduler.start();

        return true;
    }

    private void loadProperties() {
        properties = new Properties();
        InputStream is = Cron4jPlugin.class.getClassLoader()
                .getResourceAsStream(config);
        try {
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isDisableJob(String enable) {
        return Boolean.valueOf(properties.get(enable) + "") == false;
    }

    /**
     * 创建job
     * @param id id
     * @param className 类名
     * @param cron 时间表达式
     * @param name 任务
     * @return
     */
    private JobBean createJob(String id,String className,String cron,String name){
        JobBean job = new JobBean();
        job.setJobId(id);
        job.setJobClass(className);
        job.setCronExpression(cron);
        job.setJobName(name);
        return job;
    }


    /**
     * 停止任务
     * @param name 任务名
     * @return
     */
    public static boolean stopJob(String name) {

        for (JobBean job : jobs) {
            if(name.equals(job.getJobName())){
                scheduler.deschedule(job.getJobId());
                return true;
            }
        }
        return false;
    }

    /**
     * 停止任务调度器
     */
    public boolean stop() {
        scheduler.stop();
        return true;
    }

}
