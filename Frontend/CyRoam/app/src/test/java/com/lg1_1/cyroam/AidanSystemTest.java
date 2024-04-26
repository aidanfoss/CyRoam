package com.lg1_1.cyroam;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import android.content.Intent;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import com.lg1_1.cyroam.aidansActivities.PinInformationActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AidanSystemTest {

    @Rule
    public ActivityTestRule<MapsActivity> activityRule = new ActivityTestRule<>(MapsActivity.class);

    @Before
    public void setup() {
        activityRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void testActivityNotNull() {
        assertThat(activityRule.getActivity(), notNullValue());
    }

    @Test
    public void testMapFragmentDisplayed() {
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }

    @Test
    public void testPortalButtonTriggersVisibility() {
        // Assume portalButton toggles visibility of portalButton1
        onView(withId(R.id.portalButton)).perform(click());
        onView(withId(R.id.portalButton1)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void testNavigationToPinInformationActivity() {
        // This assumes clicking on a marker triggers navigation to PinInformationActivity
        // Mocking the map and marker click might be necessary or using intents
        Intent expectedIntent = new Intent(activityRule.getActivity(), PinInformationActivity.class);
        onView(withId(R.id.map)).perform(click());
        assertThat(getNextStartedActivity(), is(expectedIntent));
    }

    // Helper method to get the next started activity from intents, requires mock setup
    private Intent getNextStartedActivity() {
        // Mocking or capturing intents logic goes here
        return null;
    }
}

