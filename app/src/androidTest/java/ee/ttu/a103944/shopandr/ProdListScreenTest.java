package ee.ttu.a103944.shopandr;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ee.ttu.a103944.shopandr.ui.activity.ProductListActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProdListScreenTest {

    @Rule
    public ActivityTestRule<ProductListActivity> activityTestRule
            = new ActivityTestRule<ProductListActivity>(ProductListActivity.class,true,false){};

    @Test
    public void testSwipe(){
        Bundle bundle = new Bundle();
        bundle.putString(ProductListActivity.ARG_CATALOG, "monitorid");
        Intent intent = new Intent();
        intent.putExtras(bundle);
        activityTestRule.launchActivity(intent);

        onView(withId(R.id.prod_list)).perform(swipeUp());
        onView(withId(R.id.prod_list)).perform(swipeUp());
        onView(withId(R.id.prod_list)).perform(swipeUp());
        onView(withId(R.id.prod_list)).perform(swipeUp());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
