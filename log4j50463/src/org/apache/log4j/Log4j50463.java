package org.apache.log4j;

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

/**
 * bug URL: https://issues.apache.org/bugzilla/show_bug.cgi?id=50463 
 * This is a wait-notify deadlock.
 * Reproduce environment: log4j 1.2.14, JDK 1.6.0_33
 * 
 * This test shall not trigger deadlock.
 * 
 * @author Louis Jacomet (axjuz)
 * @collector
 * @modified by Ziyi Lin
 */
public class Log4j50463 {
    private static int BUFFER_SIZE = 10;
    private Logger logger;

    public Log4j50463() {
        AsyncAppender asyncAppender = new AsyncAppender();
        asyncAppender.setBufferSize(BUFFER_SIZE);
        asyncAppender.addAppender(new ConsoleAppender(new SimpleLayout()));
        LogManager.getRootLogger().addAppender(asyncAppender);
        logger = Logger.getLogger(Log4j50463.class);
    }

    public static void main(String[] args) throws InterruptedException {

        Log4j50463 test = new Log4j50463();
        test.logToFillBuffer();
    }

    private void logToFillBuffer() {
        // start several threads to fill up the buffer.
        Thread t=new FillBufferThread();
        t.start();
        logToKillDispatcher();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    private class FillBufferThread extends Thread {
        public void run() {
            for (int i = 0; i < BUFFER_SIZE/2; i++) {
                logger.error("Locking me up");
            }
        }
    }

    private void logToKillDispatcher() {
        logger.error(new Object() {
            public String toString() {
                throw new RuntimeException();
            }
        });
    }
}