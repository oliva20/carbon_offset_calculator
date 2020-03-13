package com.example.offsetcalculator;
import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.testing.TestWorkerBuilder;

import com.example.offsetcalculator.model.workers.LocationWorker;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@RunWith(AndroidJUnit4.class)
public class WorkerTest {
    private Context mContext;
    private Executor mExecutor;

    @Before
    public void setUp() {
        mContext = ApplicationProvider.getApplicationContext();
        mExecutor = Executors.newSingleThreadExecutor();
    }

    @Test
    public void testSleepWorker() {
        Data inputData = new Data.Builder()
                .putLong("SLEEP_DURATION", 10_000L)
                .build();

        LocationWorker worker =
                (LocationWorker) TestWorkerBuilder.from(mContext,
                        LocationWorker.class,
                        mExecutor)
                        .setInputData(inputData)
                        .build();

        Worker.Result result = worker.doWork();
        assertThat(result, is(Worker.Result.success()));
    }

}

