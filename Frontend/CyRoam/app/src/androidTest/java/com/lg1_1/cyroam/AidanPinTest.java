package com.lg1_1.cyroam;

import static androidx.test.espresso.Espresso.onIdle;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.Manifest;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AidanPinTest {

    @Rule
    public ActivityScenarioRule<MapsActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MapsActivity.class);
    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION);
    @Test
    public void testCreateNewPin(){
        navigateToNewPinActivity();
        onIdle();
        onView(withId(R.id.longitude_edt)).perform(replaceText("10"));
        onView(withId(R.id.latitude_edt)).perform(replaceText("10"));
        onView(withId(R.id.pin_name_edt)).perform(replaceText("SystemTest"));
        onView(withId(R.id.new_pin_btn)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        testWait(1000);
        onView(withId(R.id.new_pin_btn)).perform(click());

        //check to see if navigation happened
        testWait(3000);
        onView(withId(R.id.portalButton)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }
    private void navigateToNewPinActivity(){
        onView(withId(R.id.portalButton)).perform(click());
        testWait(500);
        onView(withId(R.id.portalButton2)).perform(click());
        testWait(1000);
    }
    private void testWait(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void CheckAllVisibilityPinActivity(){
        navigateToNewPinActivity();
        onIdle();
        onView(withId(R.id.longitude_edt)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.latitude_edt)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.pin_name_edt)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.new_pin_btn)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void testPinActivityUsingInvalidInputs(){
        navigateToNewPinActivity();
        onIdle();
        onView(withId(R.id.longitude_edt)).perform(replaceText("abcdefg"));
        onView(withId(R.id.latitude_edt)).perform(replaceText("hijklmnop"));
        onView(withId(R.id.pin_name_edt)).perform(replaceText("SystemTest"));
        onView(withId(R.id.new_pin_btn)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.new_pin_btn)).perform(click());

        //check to make sure navigation did not happen
        onView(withId(R.id.new_pin_btn)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void NavigateToLeaderboard(){
        onView(withId(R.id.portalButton)).perform(click());
        testWait(500);
        onView(withId(R.id.portalButton4)).perform(click());
        testWait(1000);

        onView(withId(R.id.LeaderBoardInsertText)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

    }

    @Test
    public void NavigateToNewPin(){
        onView(withId(R.id.portalButton)).perform(click());
        testWait(500);
        onView(withId(R.id.portalButton2)).perform(click());
        testWait(1000);
    }

    @Test
    public void TestPortalActuation(){
        onView(withId(R.id.portalButton)).perform(click());
        testWait(500);
        onView(withId(R.id.portalButton1)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.portalButton2)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.portalButton3)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.portalButton4)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.portalButton5)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.portalButton)).perform(click());
        testWait(500);
        onView(withId(R.id.portalButton1)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        onView(withId(R.id.portalButton2)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        onView(withId(R.id.portalButton3)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        onView(withId(R.id.portalButton4)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        onView(withId(R.id.portalButton5)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));

    }
}
