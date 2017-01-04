package ee.ttu.a103944.shopandr.network.service;

import android.content.Context;

import java.io.IOException;

import ee.ttu.a103944.shopandr.utils.Preference;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceCreator {

    public static final String API_ENDPOINT =
            "http://10.0.2.2:8080/shop12/";


    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create());


    public static <S> S createService(Class<S> serviceClass, Context context) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new AddCookiesInterceptor(context));
        client.addInterceptor(new ReceivedCookiesInterceptor(context));


        Retrofit retrofit = builder.client(client.build()).build();

        return retrofit.create(serviceClass);
    }

    private static class AddCookiesInterceptor implements Interceptor {

        private Context context;
        private Preference preference;

        public AddCookiesInterceptor(Context context) {
            this.context = context;
            preference = new Preference(context);
        }

        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();

            String cookie = (String) preference.getCookies();
            builder.addHeader("Cookie", cookie);

            return chain.proceed(builder.build());
        }
    }


    private static class ReceivedCookiesInterceptor implements Interceptor {
        private Context context;
        private Preference preference;

        public ReceivedCookiesInterceptor(Context context) {
            this.context = context;
            preference = new Preference(context);
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());

            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                String realCookie = originalResponse.headers("Set-Cookie").get(0);

                preference.saveCookies(realCookie);

            }
            return originalResponse;
        }
    }
}
