package org.liberty.android.fantastischmemo.integrationtest;

import android.Manifest;
import android.Manifest.permission;
import android.content.ComponentName;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import androidx.test.rule.GrantPermissionRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.liberty.android.fantastischmemo.R;
import org.liberty.android.fantastischmemo.ui.AnyMemo;
import org.liberty.android.fantastischmemo.ui.StudyActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

public class AnyMemoActivityTest {
    TestHelper testHelper;
    public ActivityTestRule<AnyMemo> mActivityRule = new IntentsTestRule<>(AnyMemo.class);

    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(permission.READ_EXTERNAL_STORAGE, permission.WRITE_EXTERNAL_STORAGE);

    @Rule
    public RuleChain chain = RuleChain.outerRule(permissionRule).around(mActivityRule);

    @Before
    public void setUp() {
        testHelper = new TestHelper(InstrumentationRegistry.getInstrumentation());
        testHelper.clearPreferences();
        testHelper.markNotFirstTime();
    }

    @Test
    @LargeTest
    @Ignore
    public void testOpenActivityWithSampleDB() {
        onView(allOf(withText(TestHelper.SAMPLE_DB_NAME), withId(R.id.recent_item_filename)))
                .check(matches(isDisplayed()));
    }

    @Test
    @LargeTest
    @Ignore
    public void testOpenStudyActivity() {
        onView(allOf(withText(TestHelper.SAMPLE_DB_NAME), withId(R.id.recent_item_filename)))
                .perform(click());
        intended(hasComponent(new ComponentName(InstrumentationRegistry.getTargetContext(), StudyActivity.class)));
        onView(allOf(withText(TestHelper.SAMPLE_DB_NAME), withId(R.id.card_text_view)))
                .check(matches(isDisplayed()));
        pressBack();
    }

    @Test
    @LargeTest
    @Ignore
    public void testOpenStudyActivityFromAction() {
        onView(allOf(withText(TestHelper.SAMPLE_DB_NAME), withId(R.id.recent_item_filename)))
                .perform(longClick());
        onView(withText(R.string.study_text)).perform(click());
        intended(hasComponent(new ComponentName(InstrumentationRegistry.getTargetContext(), StudyActivity.class)));
        onView(allOf(withText(TestHelper.SAMPLE_DB_NAME), withId(R.id.card_text_view)))
                .check(matches(isDisplayed()));
        pressBack();
    }

    @After
    public void tearDown() throws Exception {
        Thread.sleep(2000);
    }
}
