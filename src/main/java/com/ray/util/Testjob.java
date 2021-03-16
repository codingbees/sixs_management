package com.ray.util;

import java.util.Date;

    public class Testjob implements Runnable {

        public void run() {
            System.out.println("Current system time: " + new Date());
            System.out.println("Another minute ticked away...");
        }

    }


