package ee.ttu.a103944.shopandr.utils;

import android.content.Context;
import android.util.Log;

import ee.ttu.a103944.shopandr.model.CartInfo;
import ee.ttu.a103944.shopandr.network.service.OrderService;
import ee.ttu.a103944.shopandr.network.service.ServiceCreator;
import ee.ttu.a103944.shopandr.ui.fragment.BasketSummaryFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BasketUtils {

    public static void updateBasket(final BasketSummaryFragment basketSummaryFragment, Context context){
        OrderService orderService = ServiceCreator.createService(OrderService.class,context);
        Call<CartInfo> call =orderService.getCartInfo();
        call.enqueue(new Callback<CartInfo>() {
            @Override
            public void onResponse(Call<CartInfo> call, Response<CartInfo> response) {
                CartInfo cartInfo = response.body();
                if(cartInfo!=null && cartInfo.getTotalPrice()!=null)
                basketSummaryFragment
                        .updateSummary(""+cartInfo.getTotalItems()
                                ,cartInfo.getTotalPrice().toString()
                                ,basketSummaryFragment);
            }

            @Override
            public void onFailure(Call<CartInfo> call, Throwable t) {

            }
        });
    }
}
