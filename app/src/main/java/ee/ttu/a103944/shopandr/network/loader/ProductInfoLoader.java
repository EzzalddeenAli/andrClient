package ee.ttu.a103944.shopandr.network.loader;

import android.content.Context;

import java.io.IOException;

import ee.ttu.a103944.shopandr.model.ProductInfoDTO;
import ee.ttu.a103944.shopandr.network.response.AbstractResponse;
import ee.ttu.a103944.shopandr.network.response.RequestStatus;
import ee.ttu.a103944.shopandr.network.service.ProductService;
import ee.ttu.a103944.shopandr.network.service.ServiceCreator;
import retrofit2.Call;
import retrofit2.Response;


public class ProductInfoLoader extends AbstractLoader {

    private String TAG = "ProductInfoLoader";

    private String url;
    private Context context;

    public ProductInfoLoader(Context context, String url) {
        super(context);
        this.context = context;
        this.url = url;
    }

    @Override
    public AbstractResponse loadInBackground() {
        try {
            ProductService service = ServiceCreator.createService(ProductService.class, context);
            Call<ProductInfoDTO> call = null;

            call = service.getProductsInfo(url);
            Response<ProductInfoDTO> resp = call.execute();

            int code = resp.code();

            ProductInfoDTO piDTO = resp.body();

            return new AbstractResponse()
                    .setRequestStatus(code >= 200 && code <= 299 ? RequestStatus.SUCCUSS : RequestStatus.ERROR)
                    .setResponse(piDTO);
        } catch (IOException e) {
            return new AbstractResponse();
        }
    }
}
