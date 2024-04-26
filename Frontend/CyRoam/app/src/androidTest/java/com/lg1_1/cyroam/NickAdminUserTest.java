package com.lg1_1.cyroam;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest   // large execution time
public class NickAdminUserTest {

    private static final int SIMULATED_DELAY_MS = 1000;

    @Rule
    public ActivityScenarioRule<AdminUserControl> activityScenarioRule = new ActivityScenarioRule<>(AdminUserControl.class);

    @Test
    public void needstomakeRequestfirst(){


    }

}
