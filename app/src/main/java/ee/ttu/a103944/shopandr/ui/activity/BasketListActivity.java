package ee.ttu.a103944.shopandr.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import ee.ttu.a103944.shopandr.R;
import ee.ttu.a103944.shopandr.ui.fragment.BasketListFragment;


public class BasketListActivity extends AbstractActivity {

    private static String TAG = "BasketListActivity";


    public static void start(Activity activity) {
        Intent intent = new Intent(activity, BasketListActivity.class);
        activity.startActivityForResult(intent, 0);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket_list);

        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Basket");

        getSupportFragmentManager().beginTransaction().replace(R.id.content,
                new BasketListFragment(),
                BasketListFragment.class.toString()).commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}
