package ee.ttu.a103944.shopandr.network.service;

import ee.ttu.a103944.shopandr.model.BasketListDTO;
import ee.ttu.a103944.shopandr.model.CartInfo;
import ee.ttu.a103944.shopandr.model.OrderDTO;
import ee.ttu.a103944.shopandr.model.OrdersDTO;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface OrderService {

    @FormUrlEncoded
    @POST("checkout/cart/add")
    Call<CartInfo> addToBasket(
            @Field("product_id") String prodid,
            @Field("quantity") String qty
    );

    @GET("api/cartinfo/")
    Call<CartInfo> getCartInfo(
    );

    @GET("api/checkout/cart")
    Call<BasketListDTO> getCartDetails(
    );

    @FormUrlEncoded
    @POST("api/checkout/cart/update")
    Call<BasketListDTO> updateCart(
            @Field("product_id") String prodid,
            @Field("quantity") String qty
    );


    @FormUrlEncoded
    @POST("api/checkout/cart/del")
    Call<BasketListDTO> delFromCart(
            @Field("product_id") String prodid
    );

    @FormUrlEncoded
    @POST("api/checkout/cart")
    Call<String> createOrder(
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("phone") String phone,
            @Field("email") String email,
            @Field("address") String address
    );


    @GET("api/orders")
    Call<OrdersDTO> getOrders(
    );

    @GET("api/order/{orderid}")
    Call<OrderDTO> getOrderById(
            @Path("orderid") String orderid
    );
}
