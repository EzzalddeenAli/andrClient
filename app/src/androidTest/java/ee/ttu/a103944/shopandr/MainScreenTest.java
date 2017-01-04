package ee.ttu.a103944.shopandr;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.simonvt.numberpicker.NumberPicker;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.AnyOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ee.ttu.a103944.shopandr.ui.activity.MainActivity;
import ee.ttu.a103944.shopandr.ui.activity.ProductListActivity;
import ee.ttu.a103944.shopandr.ui.fragment.BasketListFragment;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainScreenTest {


    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<MainActivity>(MainActivity.class) {
    };



    private void rotateScreen() {
        Context context = InstrumentationRegistry.getTargetContext();
        int orientation = context.getResources().getConfiguration().orientation;

        Activity activity = activityRule.getActivity();
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        /*activity.setRequestedOrientation(
                (orientation == Configuration.ORIENTATION_PORTRAIT) ?
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        */
    }

    @Test
    public void testOrder() {

        onView(withId(R.id.drawerLayout)).perform(DrawerActions.open(GravityCompat.START));
        SystemClock.sleep(1000);
        onView(withId(R.id.drawerLayout)).perform(DrawerActions.open(GravityCompat.END));
        SystemClock.sleep(2000);
        onView(withText("mobile phones")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("smartphone")).perform(click());
        SystemClock.sleep(1000);
        /*
        onView(withId(R.id.prod_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        SystemClock.sleep(1000);
        onView(withId(R.id.order_container)).perform(click());
        SystemClock.sleep(1000);
        Espresso.pressBack();
        */
//        onView(withId(R.id.observable_scroll_view))
//                .perform(swipeUp());
        onView(withId(R.id.prod_list)).perform(swipeUp());
        onView(withId(R.id.prod_list)).perform(swipeUp());
        //onView(withId(R.id.prod_list)).perform(swipeUp());
        //onView(withId(R.id.prod_list)).perform(swipeUp());

        SystemClock.sleep(1000);
        /*
        onView(withId(R.id.view_button_filter)).perform(click());
        SystemClock.sleep(2000);
        onView(withId(R.id.filter_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        SystemClock.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.filtervar_list)).atPosition(2).perform(click());
        SystemClock.sleep(1000);
        onView(withContentDescription("Navigate up")).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.filter_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        SystemClock.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.filtervar_list)).atPosition(1).perform(click());
        onView(withContentDescription("Navigate up")).perform(click());
        onView(withContentDescription("Navigate up")).perform(click());
        SystemClock.sleep(1000);
        */

        /*onView(withId(R.id.prod_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        SystemClock.sleep(1000);
        onView(withId(R.id.order_container)).perform(click());
        Espresso.pressBack();
        SystemClock.sleep(1000);
        */

        /*
        onView(withId(R.id.view_button_filter)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.button_reset)).perform(click());
        SystemClock.sleep(1000);
        onView(withContentDescription("Navigate up")).perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.prod_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        SystemClock.sleep(1000);
        onView(withId(R.id.order_container)).perform(click());

        Espresso.pressBack();
        */

        /*

        onView(withId(R.id.drawerLayout)).perform(DrawerActions.open(GravityCompat.END));
        SystemClock.sleep(2000);
        onView(withText("monitors")).perform(click());

        SystemClock.sleep(2000);
         */
        onView(withId(R.id.view_catalog_sort)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.layout_sort_by_price_desc)).perform(click());
        SystemClock.sleep(1000);
        onView(withContentDescription("Navigate up")).perform(click());
        SystemClock.sleep(2000);

        onView(withId(R.id.view_button_filter)).perform(click());
        SystemClock.sleep(2000);
        onView(withId(R.id.filter_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        SystemClock.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.filtervar_list)).atPosition(2).perform(click());
        SystemClock.sleep(1000);
        onView(withContentDescription("Navigate up")).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.filter_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        SystemClock.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.filtervar_list)).atPosition(1).perform(click());
        onView(withContentDescription("Navigate up")).perform(click());
        onView(withContentDescription("Navigate up")).perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.prod_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        SystemClock.sleep(2000);
        onView(withId(R.id.product_detail_container)).perform(swipeUp());
        SystemClock.sleep(2000);
        onView(withId(R.id.order_container)).perform(click());
        SystemClock.sleep(1000);
        Espresso.pressBack();
        Espresso.pressBack();
        Espresso.pressBack();


        SystemClock.sleep(1000);

        onView(withId(R.id.drawerLayout)).perform(DrawerActions.open(GravityCompat.START));
        SystemClock.sleep(2000);
        onView(withId(R.id.navrv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        SystemClock.sleep(1000);
        onView(withId(R.id.nick)).perform(clearText(),typeText("test5"));
        onView(withId(R.id.pwd)).perform(clearText(),typeText("123456"));
        onView(withId(R.id.login)).perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.imageView2)).perform(click());
        SystemClock.sleep(2000);
        //onView(withId(R.id.input_item_count)).perform(click());

//        onView(withId(R.id.basket_list))
//                .perform(RecyclerViewActions.actionOnItem())
//                .perform(RecyclerViewActions.actionOnHolderItem(matcher,))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
//        onView(withId(R.id.basket_list))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click(onView(withId(R.id.input_item_count))) ));



        onView(withIndex(withId(R.id.input_item_count), 0)).perform(click());
        ViewInteraction numPicker = onView(withClassName(Matchers.equalTo(NumberPicker.class.getName())));
        numPicker.perform(setNumber(5));

        //onView(withIndex(withId(R.id.input_item_count))).perform(click());
        SystemClock.sleep(2000);
        //onData(anything()).inAdapterView(withId(R.id.sdl_list)).atPosition(1).perform(click());
        //onView(withId(R.id.sdl_list)).perform(swipeUp());
        onView(withId(R.id.sdl_button_positive)).perform(click());

        onView(withId(R.id.fname)).perform(clearText(),typeText("a"));
        onView(withId(R.id.lname)).perform(clearText(),typeText("b"));
        onView(withId(R.id.phone)).perform(clearText(),typeText("c"));
        onView(withId(R.id.address)).perform(clearText(),typeText("d"));
        onView(withId(R.id.email)).perform(clearText(),typeText("softy2005z@yahoo.com"));
        onView(withId(R.id.order)).perform(click());
        SystemClock.sleep(1000);
        onView(withContentDescription("Navigate up")).perform(click());

        SystemClock.sleep(1000);
        onView(withId(R.id.drawerLayout)).perform(DrawerActions.open(GravityCompat.START));
        SystemClock.sleep(1000);
        onView(withId(R.id.navrv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        SystemClock.sleep(1000);
        onView(withText("1000014")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("TESTNAME737376")).perform(click());
        SystemClock.sleep(1000);
        Espresso.pressBack();
        Espresso.pressBack();

    }

    public static ViewAction setNumber(final int num){
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(NumberPicker.class);
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public void perform(UiController uiController, View view) {
                NumberPicker np = (NumberPicker) view;
                np.setValue(num);
            }
        };
    }

    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }


}
