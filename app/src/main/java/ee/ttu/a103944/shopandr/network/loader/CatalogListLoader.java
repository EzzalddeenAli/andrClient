package ee.ttu.a103944.shopandr.network.loader;

import android.content.Context;

import java.io.IOException;

import ee.ttu.a103944.shopandr.model.CatalogListDTO;
import ee.ttu.a103944.shopandr.network.response.AbstractResponse;
import ee.ttu.a103944.shopandr.network.response.RequestStatus;
import ee.ttu.a103944.shopandr.network.service.CatalogtListService;
import ee.ttu.a103944.shopandr.network.service.ServiceCreator;
import retrofit2.Call;
import retrofit2.Response;


public class CatalogListLoader extends AbstractLoader {

    private String mCatalog;
    private Context context;

    public CatalogListLoader(Context context, String catalog) {
        super(context);
        this.context = context;
        mCatalog = catalog;
    }

    @Override
    public AbstractResponse loadInBackground() {
        try {
            CatalogtListService service = ServiceCreator.createService(CatalogtListService.class, context);

            Call<CatalogListDTO> call = null;
            if (mCatalog.length() > 0)
                call = service.getCatalogsChilds(mCatalog);
            else
                call = service.getCatalogListMain();
            Response<CatalogListDTO> resp = call.execute();
            int code = resp.code();

            CatalogListDTO ctlDTO = resp.body();
            return new AbstractResponse()
                    .setRequestStatus(code >= 200 && code <= 299 ? RequestStatus.SUCCUSS : RequestStatus.ERROR)
                    .setResponse(ctlDTO);
        } catch (IOException e) {
            return new AbstractResponse();
        }
    }
}
