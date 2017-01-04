package ee.ttu.a103944.shopandr.network.service;

import java.util.Map;

import ee.ttu.a103944.shopandr.model.ProductInfoDTO;
import ee.ttu.a103944.shopandr.model.ProductListDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;


public interface ProductService {

    @GET("api/{url}")
    Call<ProductInfoDTO> getProductsInfo(
            @Path("url") String url
    );

    @GET("api/{catalog}/")
    Call<ProductListDTO> getProductsByCatalog(
            @Path("catalog") String catalog
    );

    @GET("api/{catalog}/items-list-{page}/")
    Call<ProductListDTO> getMoreProductsByCatalog(
            @Path("catalog") String catalog,
            @Path("page") int page,
            @QueryMap Map<String, String> options
    );
}
