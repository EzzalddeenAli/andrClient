package ee.ttu.a103944.shopandr.network.service;

import ee.ttu.a103944.shopandr.model.CatalogListDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface CatalogtListService {

    @GET("api/categories/")
    Call<CatalogListDTO> getCatalogListMain(
    );

    @GET("api/category/{catalog}/categories/")
    Call<CatalogListDTO> getCatalogsChilds(
            @Path("catalog") String catalog
    );
}
