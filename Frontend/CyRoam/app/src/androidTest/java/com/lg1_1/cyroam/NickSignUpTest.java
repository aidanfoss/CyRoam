package com.lg1_1.cyroam;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest   // large execution time

public class NickSignUpTest {
    private static final int SIMULATED_DELAY_MS = 1000;

    @Rule
    public ActivityScenarioRule<SignupActivity> activityScenarioRule = new ActivityScenarioRule<>(SignupActivity.class);

    @Test
    public void loginButtonText(){
        String testString = "NickTextCase";
        String password = "123";
        String success = "success";
        onView(withId(R.id.signup_username_edt)).perform(typeText(testString));
        onView(withId(R.id.signup_password_edt)).perform(typeText(password));
        onView(withId(R.id.signup_confirm_edt)).perform(typeText(password));
        onView(withId(R.id.signup_login_btn)).perform(click());
        //onView(withId(R.id.checker)).check(matches(matchesBoolean(true)));
        //onView(withId(R.id.submit)).perform(click());
        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}

        // Verify that volley returned the correct value
        onView(withId(R.id.LogInTitleView)).check(matches(withText(" CyRoam             LogIn")));

    }

    @Test
    public void signInButtonTest(){
        String testString = "NickTextCase";
        String password = "123";
        String success = "success";
        onView(withId(R.id.signup_username_edt)).perform(typeText(testString));
        onView(withId(R.id.signup_password_edt)).perform(typeText(password));
        onView(withId(R.id.signup_confirm_edt)).perform(typeText(password));
        onView(withId(R.id.signup_signup_btn)).perform(click());
        //onView(withId(R.id.checker)).check(matches(matchesBoolean(true)));
        //onView(withId(R.id.submit)).perform(click());
        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}

        // Verify that volley returned the correct value
        onView(withId(R.id.textViewTitleMainLogin)).check(matches(withText("CyRoam")));

    }


    /**
    @Test
    public void reverseDefault2String(){
        String testString = "bossf";
        String resultString = "ryan";
        Friend free = new Friend("ryan", 0, 10000);
        onView(withId(R.id.FriendSearch)).perform(typeText("Steve"));
        onView(withId(R.id.buttonProblem)).perform(click());
        //onView(withId(R.id.checker)).check(matches(matchesBoolean(true)));
    }
    */



}
