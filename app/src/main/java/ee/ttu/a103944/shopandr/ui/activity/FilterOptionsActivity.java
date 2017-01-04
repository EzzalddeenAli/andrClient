package ee.ttu.a103944.shopandr.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.Iterator;

import ee.ttu.a103944.shopandr.R;
import ee.ttu.a103944.shopandr.model.Filter;
import ee.ttu.a103944.shopandr.model.FilterBox;
import ee.ttu.a103944.shopandr.model.Variant;
import ee.ttu.a103944.shopandr.model.VariantList;


public class FilterOptionsActivity extends AbstractActivity {

    public static String ARG_FILTERBOX = "filterbox";
    public static String ARG_FILTERNAME = "filtername";
    public static String ARG_CHECKEDVAL = "checkedval";
    public final String TAG = "FilterOptionsActivity";
    ListView filterVarLV;
    int cPos = -1;
    String checked = "";
    boolean preSet = true;
    private String ARG_CPOS = "cpos";
    private String ARG_CHECKED = "checked";
    private String ARG_PRESET = "preset";
    private FilterBox filterBox;
    private String filterName;

    public static void startForRes(Activity activity, FilterBox filterBox, String filterName, int req_code) {
        Intent intent = new Intent(activity, FilterOptionsActivity.class);
        intent.putExtra(ARG_FILTERBOX, filterBox);
        intent.putExtra(ARG_FILTERNAME, filterName);
        activity.startActivityForResult(intent, req_code);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_filter_options);

        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Options");

        if (savedInstanceState != null) {
            cPos = savedInstanceState.getInt(ARG_CPOS);
            checked = savedInstanceState.getString(ARG_CHECKED);
            preSet = savedInstanceState.getBoolean(ARG_PRESET);
            filterBox = savedInstanceState.getParcelable(ARG_FILTERBOX);
            filterName = savedInstanceState.getString(ARG_FILTERNAME);
        } else {
            filterBox = getIntent().getParcelableExtra(ARG_FILTERBOX);
            filterName = getIntent().getStringExtra(ARG_FILTERNAME);
        }

        OptionAdapter optionAdapter = new OptionAdapter(getLayoutInflater());
        optionAdapter.setVariantList(getVariantListByFilter(filterName));

        filterVarLV = findView(R.id.filtervar_list);

        filterVarLV.setAdapter(optionAdapter);
        filterVarLV.setOnItemClickListener(new onClickListener(filterVarLV));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_FILTERBOX, filterBox);
        outState.putString(ARG_FILTERNAME, filterName);
        Log.d(TAG, "onSaveInstanceState");
        outState.putInt(ARG_CPOS, cPos);
        outState.putString(ARG_CHECKED, checked);
        outState.putBoolean(ARG_PRESET, preSet);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    private VariantList getVariantListByFilter(String name) {
        Iterator<Filter> iterator = filterBox.getFilters().keySet().iterator();
        Filter filter = null;
        while (iterator.hasNext()) {
            filter = iterator.next();
            if (filter.getName().equalsIgnoreCase(name))
                return filterBox.getFilters().get(filter);
        }

        return null;
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
        intent.putExtra(ARG_FILTERNAME, filterName);
        intent.putExtra(ARG_CHECKEDVAL, checked);
        setResult(RESULT_OK, intent);
        finish();

    }

    class onClickListener implements AdapterView.OnItemClickListener {

        ListView listView;


        public onClickListener(ListView listView) {
            this.listView = listView;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            checked = ((Variant) adapterView.getItemAtPosition(position)).getFilter_variant().getValue();
            if (cPos == position) {
                if (listView.isItemChecked(cPos)) {
                    listView.setItemChecked(position, false);
                    checked = "";
                } else {
                }
            } else {
            }
            cPos = listView.getCheckedItemPosition();
        }
    }

    private class OptionAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        private VariantList variantList;

        public OptionAdapter(LayoutInflater layoutInflater) {
            this.layoutInflater = layoutInflater;
        }

        public void setVariantList(VariantList variantList) {
            this.variantList = variantList;
        }

        @Override
        public int getCount() {
            return variantList.getVariants().size() - 1;
        }

        @Override
        public Object getItem(int i) {
            return variantList.getVariants().get(i + 1);
        }

        @Override
        public long getItemId(int i) {
            return (long) i;
        }


        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.v_filter_option, viewGroup, false);
            }
            ((CheckBox) convertView).setText(variantList.getVariants().get(i + 1).getFilter_variant().getValue());
            String name = variantList.getVariants().get(i + 1).getFilter_variant().getValue();
            if (variantList.getVariants().get(i + 1).isCurrent()) {
                if (preSet) {
                    checked = name;
                    cPos = i;
                    filterVarLV.setItemChecked(i, true);
                    preSet = false;
                }
            }
            if (checked.equalsIgnoreCase(name)) {
                filterVarLV.setItemChecked(i, true);
            }
            return convertView;
        }
    }


}
