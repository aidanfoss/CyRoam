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
public class NickAddFriendsTest {
    private static final int SIMULATED_DELAY_MS = 1000;

    @Rule
    public ActivityScenarioRule<AddFriends> activityScenarioRule = new ActivityScenarioRule<>(AddFriends.class);

    @Test
    public void sendFriendRequest(){
        String testString = "bossf";
        onView(withId(R.id.FriendSearch)).perform(typeText(testString));
        onView(withId(R.id.searchbutton)).perform(click());

        //onView(withId(R.id.checker)).check(matches(matchesBoolean(true)));
        //onView(withId(R.id.submit)).perform(click());
        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        // Verify that volley returned the correct value
        onView(withId(R.id.TitletextView)).check(matches(withText("Send Friend Invite")));

    }

    @Test
    public void ExitAddFriends(){
        String testString = "bossf";
        //onView(withId(R.id.FriendSearch)).perform(typeText(testString));
        onView(withId(R.id.addBackButton)).perform(click());

        //onView(withId(R.id.checker)).check(matches(matchesBoolean(true)));
        //onView(withId(R.id.submit)).perform(click());
        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        // Verify that volley returned the correct value
        onView(withId(R.id.textViewTitle)).check(matches(withText("Friends")));

    }

}
