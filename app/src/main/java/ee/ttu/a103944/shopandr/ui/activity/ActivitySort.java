package ee.ttu.a103944.shopandr.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RadioGroup;

import java.util.Iterator;

import ee.ttu.a103944.shopandr.R;
import ee.ttu.a103944.shopandr.model.Filter;
import ee.ttu.a103944.shopandr.model.FilterBox;
import ee.ttu.a103944.shopandr.model.Variant;
import ee.ttu.a103944.shopandr.model.VariantList;


public class ActivitySort extends AbstractActivity {

    public static String ARG_FILTERBOX = "filterbox";
    public final String TAG = "ActivitySort";
    private FilterBox filterBox;

    public static void startForRes(Activity activity, FilterBox filterBox, int req_code) {
        Intent intent = new Intent(activity, ActivitySort.class);
        intent.putExtra(ARG_FILTERBOX, filterBox);

        activity.startActivityForResult(intent, req_code);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sorting);

        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Sort");

        RadioGroup radioGroup = findView(R.id.radio_group_sort);
        radioGroup.setOnCheckedChangeListener(new OncheckedListener());

        if (savedInstanceState != null) {
            filterBox = savedInstanceState.getParcelable(ARG_FILTERBOX);
        } else {
            filterBox = getIntent().getParcelableExtra(ARG_FILTERBOX);
        }
        updateFB(true);
    }

    private void updateFB(boolean asc) {
        Iterator<Filter> iterator = filterBox.getFilters().keySet().iterator();
        while (iterator.hasNext()) {
            Filter filter = iterator.next();
            if (filter.getName().equalsIgnoreCase("price")) {
                VariantList variantList = filterBox.getFilters().get(filter);
                for (Variant variant : variantList.getVariants()) {
                    if (variant.getFilter_variant().getValue().equalsIgnoreCase("all")) {
                        variant.setCurrent(false);
                    }
                    if (variant.getFilter_variant().getValue().equalsIgnoreCase("0")) {
                        if (asc)
                            variant.setCurrent(true);
                        else
                            variant.setCurrent(false);
                    }
                    if (variant.getFilter_variant().getValue().equalsIgnoreCase("1")) {
                        if (!asc)
                            variant.setCurrent(true);
                        else
                            variant.setCurrent(false);
                    }
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_FILTERBOX, filterBox);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                saveRes();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveRes() {
        Intent intent = new Intent();
        intent.putExtra(ARG_FILTERBOX, filterBox);
        setResult(RESULT_OK, intent);
        finish();

    }

    private class OncheckedListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int id) {
            switch (id) {
                case R.id.layout_sort_by_price_asc:
                    updateFB(true);
                    break;
                case R.id.layout_sort_by_price_desc:
                    updateFB(false);
                    break;
                default:
                    break;
            }
        }
    }


}


