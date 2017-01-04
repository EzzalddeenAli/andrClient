package ee.ttu.a103944.shopandr.ui.manager;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

import ee.ttu.a103944.shopandr.model.Catalog;
import ee.ttu.a103944.shopandr.model.Filter;
import ee.ttu.a103944.shopandr.model.FilterBox;
import ee.ttu.a103944.shopandr.model.PagesInfo;
import ee.ttu.a103944.shopandr.model.PreparedProduct;
import ee.ttu.a103944.shopandr.model.ProductListDTO;
import ee.ttu.a103944.shopandr.model.Variant;
import ee.ttu.a103944.shopandr.model.VariantList;
import ee.ttu.a103944.shopandr.network.response.AbstractResponse;
import ee.ttu.a103944.shopandr.ui.activity.ProductListActivity;


public class productListManager {

    private IView view;

    public void setView(IView view) {
        this.view = view;
    }

    public void setLoaderResult(final AbstractResponse response, final ProductListActivity productListActivity) {
        switch (response.getRequestStatus()) {

            case SUCCUSS:
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... voids) {
                        Log.d("productListManager", "doInBackground");
                        final ProductListDTO plDTO = response.getResponse();

                        productListActivity.plDTO.setPages(plDTO.getPages());
                        productListActivity.plDTO.getPpList().setTotalProds(plDTO.getPpList().getTotalProds());
                        productListActivity.plDTO.setCartInfo(plDTO.getCartInfo());

                        productListActivity.plDTO.setFilterBox(updateSort(plDTO.getFilterBox()));
                        if (plDTO.getFilterBox().getFilters() != null) {
                            productListActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    view.invalidateOptionsMenu();
                                }
                            });

                        }

                        productListActivity.plDTO.setPrepCtlgs(plDTO.getPrepCtlgs());


                        productListActivity.plDTO.getPpList().getpProdsForPage().addAll(
                                plDTO.getPpList().getpProdsForPage());

                        final List<Catalog> childs = getCatalogsChilds(plDTO.getPrepCtlgs().getSelected(),
                                plDTO.getPrepCtlgs().getCatalogs());
                        productListActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view.addCatalogChilds(childs);
                                view.addProducts(plDTO.getPpList().getpProdsForPage(), childs,
                                        plDTO.getPages());
                            }
                        });

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                    }
                }.execute();

                break;
            case ERROR:
                Toast.makeText(productListActivity, "prods .error!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private FilterBox updateSort(FilterBox filterBox) {
        Iterator<Filter> iterator = filterBox.getFilters().keySet().iterator();
        while (iterator.hasNext()) {
            Filter filter = iterator.next();
            if (filter.getName().equalsIgnoreCase("price")) {
                VariantList variantList = filterBox.getFilters().get(filter);
                for (Variant variant : variantList.getVariants()) {
                    if (variant.getFilter_variant().getValue().equalsIgnoreCase("all")) {
                        variant.setCurrent(false);
                    }
                    if (variant.getFilter_variant().getValue().equalsIgnoreCase("cheaper first")) {
                        variant.getFilter_variant().setValue("0");
                    }
                    if (variant.getFilter_variant().getValue().equalsIgnoreCase("expensive first")) {
                        variant.getFilter_variant().setValue("1");
                    }
                }
            }
        }
        return filterBox;
    }

    public List<Catalog> getCatalogsChilds(String selected, List<Catalog> catalogs) {
        for (Catalog cata : catalogs) {
            if (cata.getUrlname().equals(selected))
                return cata.getChilds();
        }
        return null;
    }

    public interface IView {
        void addCatalogChilds(List<Catalog> childs);

        void addProducts(List<PreparedProduct> items, List<Catalog> childs, PagesInfo pagesInfo);

        void invalidateOptionsMenu();
    }

}
