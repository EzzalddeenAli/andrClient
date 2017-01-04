package ee.ttu.a103944.shopandr.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import ee.ttu.a103944.shopandr.R;
import ee.ttu.a103944.shopandr.model.Catalog;
import ee.ttu.a103944.shopandr.model.CatalogListDTO;
import ee.ttu.a103944.shopandr.model.PrepCtlg;
import ee.ttu.a103944.shopandr.network.loader.CatalogListLoader;
import ee.ttu.a103944.shopandr.network.response.AbstractResponse;
import ee.ttu.a103944.shopandr.ui.activity.MainActivity;
import ee.ttu.a103944.shopandr.ui.activity.ProductListActivity;
import ee.ttu.a103944.shopandr.ui.adapter.CatalogMenuAdapter;
import ee.ttu.a103944.shopandr.ui.listener.ListCatalogClickListener;


public class MainCatalogFragment extends AbstractFragment implements ListCatalogClickListener.onItemClickListener {

    public static String TAG = "MainCatalogFragment";
    private final String ARG_CATALOGS_LIST = "catalogs";

    ListView cataItemsLV;
    CatalogMenuAdapter catalogMenuAdapter;
    private PrepCtlg prepCtlgs;
    private final LoaderManager.LoaderCallbacks<AbstractResponse> mCatalogListLoaderCallsbacks =
            new LoaderManager.LoaderCallbacks<AbstractResponse>() {
                @Override
                public Loader<AbstractResponse> onCreateLoader(int id, Bundle args) {
                    Log.d(TAG, "onCreateLoader ");
                    switch (id) {
                        case R.id.catalogList_loader: {
                            return new CatalogListLoader(getActivity(), "");
                        }
                        default: {
                            return null;
                        }
                    }
                }

                @Override
                public void onLoadFinished(Loader<AbstractResponse> loader, AbstractResponse response) {
                    Log.d(TAG, "onLoadFinished ");
                    int id = loader.getId();
                    if (id == R.id.catalogList_loader) {
                        switch (response.getRequestStatus()) {
                            case SUCCUSS:
                                CatalogListDTO plDTO = response.getResponse();
                                prepCtlgs = plDTO.getPrepCtlgs();
                                catalogMenuAdapter.swapCtlgs(prepCtlgs.getCatalogs());
                                break;
                            case ERROR:
                                Toast.makeText(getActivity(), "CatalogList.error!", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }


                    }
                    getActivity().getSupportLoaderManager().destroyLoader(id);
                }

                @Override
                public void onLoaderReset(Loader<AbstractResponse> loader) {

                }
            };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        if (savedInstanceState != null) {
            prepCtlgs = savedInstanceState.getParcelable(ARG_CATALOGS_LIST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (prepCtlgs == null) {
            getActivity().getSupportLoaderManager().initLoader(R.id.catalogList_loader, Bundle.EMPTY, mCatalogListLoaderCallsbacks);
            prepCtlgs = new PrepCtlg();
        }
        catalogMenuAdapter = new CatalogMenuAdapter(getActivity(), prepCtlgs.getCatalogs());

        return inflater.inflate(R.layout.fragment_nav_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cataItemsLV = findView(R.id.navlist);
        cataItemsLV.setOnItemClickListener(new ListCatalogClickListener(this));
        cataItemsLV.setAdapter(catalogMenuAdapter);
    }

    @Override
    public void onItemClick(Catalog catalog, int pos) {
        ((MainActivity) getActivity()).closeDrawer();
        ProductListActivity.launch(getActivity(), catalog.getUrlname());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.catalogue_drawer:
                ((MainActivity) getActivity()).openDrawer(GravityCompat.END);
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(ARG_CATALOGS_LIST, prepCtlgs);
        super.onSaveInstanceState(outState);
    }

}
