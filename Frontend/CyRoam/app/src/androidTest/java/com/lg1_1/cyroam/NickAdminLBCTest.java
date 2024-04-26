package com.lg1_1.cyroam;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

public class NickAdminLBCTest {

    private static final int SIMULATED_DELAY_MS = 1000;

    @Rule
    public ActivityScenarioRule<AdminLeaderBoardControl> activityScenarioRule = new ActivityScenarioRule<>(AdminLeaderBoardControl.class);

    @Test
    public void LeaderBoardControlTest() {
        String testString = "NickTextCase";
        String password = "123";
        String success = "success";
        onView(withId(R.id.signup_username_edt)).perform(typeText(testString));
        onView(withId(R.id.emailthe43t1st)).perform(typeText(testString));
        onView(withId(R.id.adminscorebutton)).perform(click());

        //onView(withId(R.id.checker)).check(matches(matchesBoolean(true)));
        //onView(withId(R.id.submit)).perform(click());
        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        // Verify that volley returned the correct value
        onView(withId(R.id.textView8)).check(matches(withText("Admin Leaderboard")));

    }
}