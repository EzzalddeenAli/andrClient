package ee.ttu.a103944.shopandr.network.loader;

import android.content.Context;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import ee.ttu.a103944.shopandr.model.Filter;
import ee.ttu.a103944.shopandr.model.FilterBox;
import ee.ttu.a103944.shopandr.model.ProductListDTO;
import ee.ttu.a103944.shopandr.model.Variant;
import ee.ttu.a103944.shopandr.model.VariantList;
import ee.ttu.a103944.shopandr.network.response.AbstractResponse;
import ee.ttu.a103944.shopandr.network.response.RequestStatus;
import ee.ttu.a103944.shopandr.network.service.ProductService;
import ee.ttu.a103944.shopandr.network.service.ServiceCreator;
import retrofit2.Call;
import retrofit2.Response;


public class ProductListLoader extends AbstractLoader {

    private String TAG = "ProductListLoader";

    private String catalog;
    private int limit;
    private int page;
    private FilterBox filterBox;
    private Context context;

    public ProductListLoader(Context context, String catalog, int page, int limit, FilterBox fb) {
        super(context);
        this.catalog = catalog;
        this.context = context;
        this.page = page;
        this.limit = limit;
        this.filterBox = fb;
    }

    @Override
    public AbstractResponse loadInBackground() {
        try {
            ProductService service = ServiceCreator.createService(ProductService.class, context);
            Call<ProductListDTO> call = null;

            Map<String, String> options = getFilterMap(filterBox);
            options.put("on_page", "" + limit);
            call = service.getMoreProductsByCatalog(catalog, page, options);


            /*
            Log.d(TAG,"sleep "+Thread.currentThread().getId());
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(TAG,"awake "+Thread.currentThread().getId());
            */

            Response<ProductListDTO> resp = call.execute();

            int code = resp.code();

            ProductListDTO plDTO = resp.body();

            return new AbstractResponse()
                    .setRequestStatus(code >= 200 && code <= 299 ? RequestStatus.SUCCUSS : RequestStatus.ERROR)
                    .setResponse(plDTO);
        } catch (IOException e) {
            return new AbstractResponse();
        }
    }

    public Map<String, String> getFilterMap(FilterBox filterBox) {
        Map<String, String> filters = new LinkedHashMap<>();
        if (filterBox != null) {
            Iterator<Filter> iterator = filterBox.getFilters().keySet().iterator();
            while (iterator.hasNext()) {
                Filter filter = iterator.next();
                VariantList variantList = filterBox.getFilters().get(filter);
                for (Variant variant : variantList.getVariants()) {
                    if (variant.isCurrent() && !variant.getFilter_variant().getValue().equalsIgnoreCase("all")) {
                        filters.put(filter.getName(), variant.getFilter_variant().getValue());
                    }
                }
            }
        }
        return filters;
    }


}
