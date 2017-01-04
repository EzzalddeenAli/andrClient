package ee.ttu.a103944.shopandr.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import ee.ttu.a103944.shopandr.R;
import ee.ttu.a103944.shopandr.ui.fragment.BasketSummaryFragment;
import ee.ttu.a103944.shopandr.ui.fragment.ItemDetailFragment;


public class ItemActivity extends AbstractActivity implements
        BasketSummaryFragment.Listener {

    public static String ARG_URL = "url";
    private String TAG = "ItemActivity";
    private String url;


    public static void start(Activity activity, String url) {
        Intent intent = new Intent(activity, ItemActivity.class);
        intent.putExtra(ARG_URL, url);
        activity.startActivityForResult(intent, 999);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("");

        if (savedInstanceState != null) {
            url = savedInstanceState.getString("ARG_URL");
        } else {
            url = getIntent().getStringExtra(ARG_URL);
        }

        ItemDetailFragment itemDetailFragment = ItemDetailFragment.getInstance(url);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.product_detail_container, itemDetailFragment, ItemDetailFragment.TAG)
                .commit();
    }

    @Override
    public void onStartCheckoutProcess() {
        Toast.makeText(this, "Check out", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_URL, url);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
