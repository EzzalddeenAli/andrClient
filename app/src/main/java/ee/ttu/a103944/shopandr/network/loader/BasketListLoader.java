package ee.ttu.a103944.shopandr.network.loader;

import android.content.Context;

import java.io.IOException;

import ee.ttu.a103944.shopandr.model.BasketListDTO;
import ee.ttu.a103944.shopandr.network.response.AbstractResponse;
import ee.ttu.a103944.shopandr.network.response.RequestStatus;
import ee.ttu.a103944.shopandr.network.service.OrderService;
import ee.ttu.a103944.shopandr.network.service.ServiceCreator;
import retrofit2.Call;
import retrofit2.Response;


public class BasketListLoader extends AbstractLoader {

    private Context context;

    public BasketListLoader(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public AbstractResponse loadInBackground() {
        try {
            OrderService service = ServiceCreator.createService(OrderService.class, context);

            Call<BasketListDTO> call = null;
            call = service.getCartDetails();
            Response<BasketListDTO> resp = call.execute();
            int code = resp.code();

            BasketListDTO btlDTO = resp.body();
            return new AbstractResponse()
                    .setRequestStatus(code >= 200 && code <= 299 ? RequestStatus.SUCCUSS : RequestStatus.ERROR)
                    .setResponse(btlDTO);
        } catch (IOException e) {
            return new AbstractResponse();
        }
    }
}
