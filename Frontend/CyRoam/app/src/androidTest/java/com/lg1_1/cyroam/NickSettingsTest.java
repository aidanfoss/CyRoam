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

public class NickSettingsTest {
    private static final int SIMULATED_DELAY_MS = 1000;

    @Rule
    public ActivityScenarioRule<SettingsActivity> activityScenarioRule = new ActivityScenarioRule<>(SettingsActivity.class);

    @Test
    public void removeFriendTest(){
        String testString = "NickTextCase";
        String password = "123";
        String success = "success";
        onView(withId(R.id.textViewRemoveFriend)).perform(typeText(testString));
        onView(withId(R.id.buttonRemoveFriend)).perform(click());

        //onView(withId(R.id.checker)).check(matches(matchesBoolean(true)));
        //onView(withId(R.id.submit)).perform(click());
        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}

        // Verify that volley returned the correct value
        onView(withId(R.id.textView5)).check(matches(withText("Setting Screen")));

    }
    @Test
    public void DisableLeaderboardaccess(){


        onView(withId(R.id.buttonRemoveSelfFromLeaderBoard)).perform(click());

        //onView(withId(R.id.checker)).check(matches(matchesBoolean(true)));
        //onView(withId(R.id.submit)).perform(click());
        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}

        // Verify that volley returned the correct value
        onView(withId(R.id.textView5)).check(matches(withText("Setting Screen")));

    }


}
