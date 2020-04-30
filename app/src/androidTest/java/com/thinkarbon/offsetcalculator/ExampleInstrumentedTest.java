package com.thinkarbon.offsetcalculator;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.thinkarbon.offsetcalculator.model.service.EmissionService;

import org.junit.Before;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    Context appContext;
    EmissionService es;
    @Before
    public void setup() {
        // Context of the app under test.
         appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
         assertEquals("com.example.offsetcalculator", appContext.getPackageName());
    }

}
