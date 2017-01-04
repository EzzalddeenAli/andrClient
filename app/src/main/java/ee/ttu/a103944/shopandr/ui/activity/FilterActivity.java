package ee.ttu.a103944.shopandr.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;

import ee.ttu.a103944.shopandr.R;
import ee.ttu.a103944.shopandr.model.Filter;
import ee.ttu.a103944.shopandr.model.FilterBox;
import ee.ttu.a103944.shopandr.model.Variant;
import ee.ttu.a103944.shopandr.model.VariantList;
import ee.ttu.a103944.shopandr.ui.fragment.BasketSummaryFragment;
import ee.ttu.a103944.shopandr.utils.BasketUtils;


public class FilterActivity extends AbstractActivity implements BasketSummaryFragment.Listener {

    public static String ARG_FILTERBOX = "filterbox";
    public static int REQ_OPT_CODE = 1;
    public final String TAG = "FilterActivity";
    RecyclerView recyclerView;
    AdapterView.OnItemClickListener onFilterClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            FiltersAdapter.LabelViewHolder item = (FiltersAdapter.LabelViewHolder) adapterView.getAdapter().getItem(i);
        }
    };
    private FilterBox filterBox;
    private FiltersAdapter filtersAdapter;
    private BasketSummaryFragment basketSummaryFragment;

    public static void startForResult(Activity activity, FilterBox filterBox, int req_code) {
        Intent intent = new Intent(activity, FilterActivity.class);
        intent.putExtra(ARG_FILTERBOX, filterBox);
        activity.startActivityForResult(intent, req_code);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_filter);

        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Filter");

        if (savedInstanceState != null) {

            filterBox = savedInstanceState.getParcelable(ARG_FILTERBOX);
        } else {
            filterBox = getIntent().getParcelableExtra(ARG_FILTERBOX);
        }


        if (filterBox == null) {
            //async load
        }
        filtersAdapter = new FiltersAdapter(getLayoutInflater());
        filtersAdapter.setFilterBox(filterBox);
        filtersAdapter.notifyDataSetChanged();


        recyclerView = findView(R.id.filter_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(filtersAdapter);

        basketSummaryFragment = new BasketSummaryFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.basket_summary_frame, basketSummaryFragment, BasketSummaryFragment.TAG)
                .hide(basketSummaryFragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        BasketUtils.updateBasket(basketSummaryFragment, FilterActivity.this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        outState.putParcelable(ARG_FILTERBOX, filterBox);
    }

    public void resetFilters() {
        Iterator<Filter> iterator = filterBox.getFilters().keySet().iterator();
        while (iterator.hasNext()) {
            Filter filter = iterator.next();
            VariantList variantList = filterBox.getFilters().get(filter);
            for (Variant variant : variantList.getVariants()) {
                if (variant.getFilter_variant().getValue().equalsIgnoreCase("all")) {
                    variant.setCurrent(true);
                } else
                    variant.setCurrent(false);
            }

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK && requestCode == REQ_OPT_CODE) {
            String checked = intent.getStringExtra(FilterOptionsActivity.ARG_CHECKEDVAL);
            String filterName = intent.getStringExtra(FilterOptionsActivity.ARG_FILTERNAME);
            if (checked == null || checked.isEmpty())
                checked = "all";
            updateFilterBox(checked, filterName);
            filtersAdapter.notifyDataSetChanged();
        }
    }

    private void updateFilterBox(String checked, String filterName) {
        VariantList variantList = getVariantListByFilter(filterName);
        for (Variant variant : variantList.getVariants()) {
            if (variant.getFilter_variant().getValue().equalsIgnoreCase(checked)) {
                variant.setCurrent(true);
            } else
                variant.setCurrent(false);
        }
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
        intent.putExtra(ARG_FILTERBOX, filterBox);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onStartCheckoutProcess() {

    }

    private class FiltersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final int VIEW_TYPE_LABEL = 0;
        private final int VIEW_TYPE_FOOTER = 1;
        private FilterBox filterBox;
        private LayoutInflater layoutInflater;


        public FiltersAdapter(LayoutInflater layoutInflater) {
            this.layoutInflater = layoutInflater;
        }

        public void setFilterBox(FilterBox filterBox) {
            this.filterBox = filterBox;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == filterBox.getFilters().size())
                return 1;
            else
                return 0;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_LABEL) {
                View view = layoutInflater.inflate(R.layout.v_filter_label, parent, false);
                return new LabelViewHolder(view);
            } else if (viewType == VIEW_TYPE_FOOTER) {
                View footerView = layoutInflater.inflate(R.layout.v_filter_reset, recyclerView, false);
                return new ButtomViewHolder(footerView);
            }

            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder.getItemViewType() == VIEW_TYPE_LABEL) {
                Filter filter = (Filter) filterBox.getFilters().keySet().toArray()[position];
                String caption = getCaption(filterBox.getFilters().get(filter));
                if (caption == null) {
                    caption = getResources().getString(R.string.all);
                }
                LabelViewHolder labelViewHolder = (LabelViewHolder) holder;
                labelViewHolder.label_TV.setText(capitalize(filter.getName()));
                labelViewHolder.value_TV.setText(caption);
            } else if (holder.getItemViewType() == VIEW_TYPE_FOOTER) {

            }
        }

        private String getCaption(VariantList variantList) {
            for (Variant variant : variantList.getVariants()) {
                if (variant.isCurrent())
                    return variant.getFilter_variant().getValue();
            }
            return null;
        }


        @Override
        public int getItemCount() {
            return filterBox.getFilters().size() + 1;
        }

        private String capitalize(String string) {
            return Character.toUpperCase(string.charAt(0)) + string.substring(1);
        }

        public class ButtomViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener {

            Button btn;

            public ButtomViewHolder(View itemView) {
                super(itemView);
                btn = (Button) itemView.findViewById(R.id.button_reset);
                btn.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                resetFilters();
                filtersAdapter.notifyDataSetChanged();
                Toast.makeText(FilterActivity.this, "reset", Toast.LENGTH_SHORT).show();
            }
        }

        public class LabelViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener {

            public TextView label_TV;
            public TextView value_TV;

            public LabelViewHolder(View itemView) {
                super(itemView);
                label_TV = (TextView) itemView.findViewById(R.id.title);
                label_TV.setEnabled(false);
                label_TV.setOnClickListener(null);
                value_TV = (TextView) itemView.findViewById(R.id.filter_values);
                value_TV.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int pos = getLayoutPosition();
                Filter filter = (Filter) filterBox.getFilters().keySet().toArray()[pos];
                FilterOptionsActivity.startForRes(FilterActivity.this, filterBox, filter.getName(), REQ_OPT_CODE);

            }

        }

    }
}
