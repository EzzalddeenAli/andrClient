package ee.ttu.a103944.shopandr.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

import ee.ttu.a103944.shopandr.R;
import ee.ttu.a103944.shopandr.model.Catalog;
import ee.ttu.a103944.shopandr.model.CatalogListDTO;
import ee.ttu.a103944.shopandr.model.Filter;
import ee.ttu.a103944.shopandr.model.FilterBox;
import ee.ttu.a103944.shopandr.model.PagesInfo;
import ee.ttu.a103944.shopandr.model.PrepCtlg;
import ee.ttu.a103944.shopandr.model.PreparedProduct;
import ee.ttu.a103944.shopandr.model.ProductListDTO;
import ee.ttu.a103944.shopandr.network.loader.CatalogListLoader;
import ee.ttu.a103944.shopandr.network.loader.ProductListLoader;
import ee.ttu.a103944.shopandr.network.response.AbstractResponse;
import ee.ttu.a103944.shopandr.ui.adapter.CatalogMenuAdapter;
import ee.ttu.a103944.shopandr.ui.adapter.CategoryAdapter;
import ee.ttu.a103944.shopandr.ui.adapter.InfiniteScrollingAdapter;
import ee.ttu.a103944.shopandr.ui.adapter.ProductListAdapter;
import ee.ttu.a103944.shopandr.ui.fragment.BasketSummaryFragment;
import ee.ttu.a103944.shopandr.ui.fragment.ItemDetailFragment;
import ee.ttu.a103944.shopandr.ui.listener.ListCatalogClickListener;
import ee.ttu.a103944.shopandr.ui.manager.productListManager;


public class ProductListActivity extends AbstractActivity implements
        BasketSummaryFragment.Listener,
        ListCatalogClickListener.onItemClickListener,
        productListManager.IView {

    public static String TAG = "ProductListActivity";

    public static String ARG_CATALOG = "catalog";
    private final String ARG_PLDTO = "plDTO";
    private final String ARG_CATALOGS_LIST = "catalogs";
    public ProductListDTO plDTO;
    CategoryAdapter categoryAdapter;
    DrawerLayout drawerLayout;
    ListView cataItemsLV;
    CatalogMenuAdapter catalogMenuAdapter;
    private String catalog;
    private ProductListAdapter productListAdapter;
    private RecyclerView productList;
    private int limit = 25;
    private int page = 1;
    private AppCompatSpinner categorySpinner;
    private int req_code_filter = 1;
    private int req_code_sort = 2;
    private BasketSummaryFragment basketSummaryFragment;
    private boolean multiPane = false;
    private PrepCtlg prepCtlgs;
    private final LoaderManager.LoaderCallbacks<AbstractResponse> mCatalogListLoaderCallsbacks =
            new LoaderManager.LoaderCallbacks<AbstractResponse>() {
                @Override
                public Loader<AbstractResponse> onCreateLoader(int id, Bundle args) {
                    Log.d(TAG, "onCreateLoader ");
                    switch (id) {
                        case R.id.catalogList_loader: {
                            return new CatalogListLoader(ProductListActivity.this, "");
                        }
                        default: {
                            return null;
                        }
                    }
                }

                @Override
                public void onLoadFinished(Loader<AbstractResponse> loader, AbstractResponse response) {
                    Log.d(TAG, "onLoadFinished cata");
                    int id = loader.getId();
                    if (id == R.id.catalogList_loader) {
                        switch (response.getRequestStatus()) {
                            case SUCCUSS:
                                CatalogListDTO plDTO = response.getResponse();
                                prepCtlgs = plDTO.getPrepCtlgs();
                                catalogMenuAdapter.swapCtlgs(prepCtlgs.getCatalogs());
                                break;
                            case ERROR:
                                Toast.makeText(ProductListActivity.this, "CatalogList.error!", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                    }
                    getSupportLoaderManager().destroyLoader(id);
                }

                @Override
                public void onLoaderReset(Loader<AbstractResponse> loader) {

                }
            };
    private productListManager plManager;
    private LoaderManager.LoaderCallbacks<AbstractResponse> productListCallbacks = new LoaderManager.LoaderCallbacks<AbstractResponse>() {
        @Override
        public Loader<AbstractResponse> onCreateLoader(int id, Bundle args) {
            switch (id) {
                case R.id.productList_loader: {
                    return new ProductListLoader(ProductListActivity.this, catalog, page, limit, plDTO.getFilterBox());
                }
                default: {
                    return null;
                }
            }
        }


        @Override
        public void onLoadFinished(Loader<AbstractResponse> loader, AbstractResponse response) {
            Log.d(TAG, "onLoadFinished " + page);
            int id = loader.getId();
            if (id == R.id.productList_loader) {
                plManager.setLoaderResult(response, ProductListActivity.this);
            }
            getSupportLoaderManager().destroyLoader(id);
        }

        @Override
        public void onLoaderReset(Loader<AbstractResponse> loader) {

        }
    };
    private ProductListAdapter.ProductlistListener productlistListener = new ProductListAdapter.ProductlistListener() {

        @Override
        public void onProdClick(int pos) {
            PreparedProduct pp = plDTO.getPpList().getpProdsForPage().get(pos);
            String url = pp.getUrl() + "/" + pp.getProduct().getName() + "-" + pp.getProduct().getId();

            if (multiPane) {
                ItemDetailFragment itemDetailFragment = ItemDetailFragment.getInstance(url);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.product_detail_container, itemDetailFragment, ItemDetailFragment.TAG)
                        .commit();
                showSelectProdFrameIfNeed();
            } else {
                ItemActivity.start(ProductListActivity.this, url);
            }

        }

        @Override
        public void onCatalogClick(String catalog) {
            ProductListActivity.launch(ProductListActivity.this, catalog);
        }
    };

    public static void launch(Activity activity, String catalog) {
        Bundle bundle = new Bundle();
        bundle.putString(ProductListActivity.ARG_CATALOG, catalog);
        Intent intent = new Intent(activity, ProductListActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_prodlist);

        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initDrawer(toolbar);


        initDataFromIntent();

        plManager = new productListManager();
        plManager.setView(this);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float dpH = displayMetrics.heightPixels / displayMetrics.density;
        Log.d(TAG, "width is " + dpWidth + " " + dpH + " " + displayMetrics.density);
        if (dpWidth > 400)
            setTitle("Product list");
        else
            setTitle("");

        if (findViewById(R.id.product_detail_container) != null) {
            multiPane = true;
        }

        categorySpinner = findView(R.id.catalog_spinner);
        categoryAdapter = new CategoryAdapter(this);

        productList = findView(R.id.prod_list);
        setupRecyclerView(productList);

        productListAdapter.setOnLoadMoreProductsListener(new InfiniteScrollingAdapter.OnLoadMoreProductsListener() {
            @Override
            public void onLoadMoreProducts() {
                ++page;
                Log.d("onLoadmore ", "on load " + page);
                getSupportLoaderManager().initLoader(R.id.productList_loader, Bundle.EMPTY, productListCallbacks);
            }
        });

        if (savedInstanceState != null) {
            Log.d(TAG, "savedInstanceState!=null");
            plDTO = savedInstanceState.getParcelable(ARG_PLDTO);
            limit = savedInstanceState.getInt("limit");

            page = savedInstanceState.getInt("page");
            prepCtlgs = savedInstanceState.getParcelable(ARG_CATALOGS_LIST);


            categoryAdapter.swapCtlgs(plDTO.getPrepCtlgs().getCatalogs());
            List<Catalog> childs = plManager.getCatalogsChilds(
                    plDTO.getPrepCtlgs().getSelected(),
                    plDTO.getPrepCtlgs().getCatalogs());
            productListAdapter.addItems(plDTO.getPpList().getpProdsForPage(), childs, plDTO.getPages());

        } else {
            Log.d(TAG, "savedInstanceState null");
            plDTO = new ProductListDTO();
            getSupportLoaderManager().initLoader(R.id.productList_loader, Bundle.EMPTY, productListCallbacks);

            getSupportLoaderManager().initLoader(R.id.catalogList_loader, Bundle.EMPTY, mCatalogListLoaderCallsbacks);
            prepCtlgs = new PrepCtlg();
        }
        if (plDTO.getPpList().getTotalProds() == null) {
            Log.d(TAG, "getTotalProds null");
            getSupportLoaderManager().initLoader(R.id.productList_loader, Bundle.EMPTY, productListCallbacks);
        }

        catalogMenuAdapter = new CatalogMenuAdapter(this, prepCtlgs.getCatalogs());
        cataItemsLV = findView(R.id.right_drawer);
        cataItemsLV.setOnItemClickListener(new ListCatalogClickListener(this));
        cataItemsLV.setAdapter(catalogMenuAdapter);


        basketSummaryFragment = new BasketSummaryFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.basket_summary_frame, basketSummaryFragment, BasketSummaryFragment.TAG)
                .hide(basketSummaryFragment).commit();


    }

    private void setupRecyclerView(@NonNull RecyclerView productsRv) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        productsRv.setLayoutManager(gridLayoutManager);
        productsRv.setItemAnimator(itemAnimator);

        productListAdapter = new ProductListAdapter(productsRv, this, this, productlistListener);
        productsRv.setAdapter(productListAdapter);
        showSelectProdFrameIfNeed();

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0,//idle
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int location = viewHolder.getAdapterPosition();
                        productListAdapter.removeItem(location);
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(productsRv);
    }

    private void showSelectProdFrameIfNeed() {
        if (!multiPane) {
            return;
        }
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.select_product_frame);
        if (productListAdapter.getCurrentPosition() == -1 &&
                getSupportFragmentManager().findFragmentByTag(ItemDetailFragment.TAG) == null) {
            if (relativeLayout != null) {
                relativeLayout.setVisibility(View.VISIBLE);
            }
        } else {
            if (relativeLayout != null) {
                relativeLayout.setVisibility(View.GONE);
            }
        }
    }

    public void initDrawer(Toolbar toolbar) {
        drawerLayout = findView(R.id.drawerLayout);
    }

    public void initDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            catalog = bundle.getString(ARG_CATALOG);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        if (plDTO.getFilterBox().getFilters().size() > 0) {
            getMenuInflater().inflate(R.menu.menu_lvl1, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.catalogue_drawer:
                drawerLayout.openDrawer(GravityCompat.END);
                break;
            case android.R.id.home:
                finish();
                return true;
            case R.id.view_button_filter:
                FilterBox prepFB = prepFBForFilter(plDTO.getFilterBox());
                FilterActivity.startForResult(this, prepFB, req_code_filter);
                return true;
            case R.id.view_catalog_sort:
                ActivitySort.startForRes(this, plDTO.getFilterBox(), req_code_sort);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private FilterBox prepFBForFilter(FilterBox filterBox) {
        FilterBox fb = new FilterBox();
        fb.getFilters().putAll(filterBox.getFilters());
        Iterator<Filter> iterator = fb.getFilters().keySet().iterator();
        while (iterator.hasNext()) {
            Filter filter = iterator.next();
            if (filter.getName().equalsIgnoreCase("on_page") || filter.getName().equalsIgnoreCase("price"))
                iterator.remove();
        }
        return fb;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK && requestCode == req_code_filter) {
            FilterBox fb = intent.getParcelableExtra(FilterActivity.ARG_FILTERBOX);
            plDTO.getFilterBox().getFilters().putAll(fb.getFilters());

            page = 1;
            ProductListActivity.this.plDTO.getPpList().getpProdsForPage().clear();
            productListAdapter.delAll();
            getSupportLoaderManager().initLoader(R.id.productList_loader, Bundle.EMPTY, productListCallbacks);
        }

        if (resultCode == RESULT_OK && requestCode == req_code_sort) {
            FilterBox fb = intent.getParcelableExtra(ActivitySort.ARG_FILTERBOX);
            plDTO.getFilterBox().getFilters().putAll(fb.getFilters());
            page = 1;
            ProductListActivity.this.plDTO.getPpList().getpProdsForPage().clear();
            productListAdapter.delAll();
            getSupportLoaderManager().initLoader(R.id.productList_loader, Bundle.EMPTY, productListCallbacks);
        }
    }


    ;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        outState.putParcelable(ARG_PLDTO, plDTO);
        outState.putInt("limit", limit);
        outState.putInt("page", page);
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_CATALOGS_LIST, prepCtlgs);
    }

    @Override
    public void onStartCheckoutProcess() {

    }

    @Override
    public void onItemClick(Catalog catalog, int pos) {
        drawerLayout.closeDrawer(GravityCompat.END);
        ProductListActivity.launch(this, catalog.getUrlname());
    }

    @Override
    public void addCatalogChilds(List<Catalog> childs) {
        categoryAdapter.swapCtlgs(childs);
    }

    @Override
    public void addProducts(List<PreparedProduct> items, List<Catalog> childs, PagesInfo pagesInfo) {
        productListAdapter.addItems(items, childs,
                pagesInfo);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
