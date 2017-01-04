package ee.ttu.a103944.shopandr.network.service;

import ee.ttu.a103944.shopandr.model.AccInfoDTO;
import ee.ttu.a103944.shopandr.model.RegDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface UserService {

    @FormUrlEncoded
    @POST("api/login_process")
    Call<String> login(
            @Field("nick") String nick,
            @Field("passwd") String passwd
    );


    @GET("api/userinfo")
    Call<AccInfoDTO> getUserInfo(
    );

    @GET("logout")
    Call<Void> logout(
    );

    @POST("api/register")
    Call<RegDTO> register(
            @Body RegDTO regDTO
    );

}
