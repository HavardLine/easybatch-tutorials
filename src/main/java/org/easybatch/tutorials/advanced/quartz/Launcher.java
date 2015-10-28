/*
 * The MIT License
 *
 *  Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package org.easybatch.tutorials.advanced.quartz;

import org.easybatch.core.job.Job;
import org.easybatch.core.job.JobBuilder;
import org.easybatch.core.reader.StringRecordReader;
import org.easybatch.extensions.quartz.JobScheduler;
import org.easybatch.tutorials.common.TweetProcessor;

import java.util.Date;

/**
 * Main class to run the Hello World tutorial repeatedly every minute using quartz extension module.<br/>
 *
 * The {@link org.easybatch.extensions.quartz.JobScheduler} API lets you schedule easy batch executions as follows :
 * <ul>
 * <li>At a fixed point of time using {@link org.easybatch.extensions.quartz.JobScheduler#scheduleAt(Job, java.util.Date)}</li>
 * <li>Repeatedly with predefined interval using {@link org.easybatch.extensions.quartz.JobScheduler#scheduleAtWithInterval(Job, java.util.Date, int)}</li>
 * <li>Using unix cron-like expression with {@link org.easybatch.extensions.quartz.JobScheduler#scheduleCron(Job, String)}</li>
 * </ul>
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class Launcher {

    public static void main(String[] args) throws Exception {

        // Create the data source
        String dataSource =
                "1,foo,easy batch rocks! #EasyBatch\n" +
                "2,bar,@foo I do confirm :-)";

        // Build a batch job
        Job job = new JobBuilder()
                .reader(new StringRecordReader(dataSource))
                .processor(new TweetProcessor())
                .build();

        // Schedule the job to start now and run every 10 seconds
        JobScheduler scheduler = JobScheduler.getInstance();
        scheduler.scheduleAtWithInterval(job, new Date(), 10);
        scheduler.start();

        System.out.println("Hit enter to stop the application");
        System.in.read();
        scheduler.stop();
    }

}
