package io.xujiaji.hnbc.model.net;

import io.xujiaji.hnbc.model.api.PhoneNumAttrApi;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jiana on 16-11-15.
 */

public class ApiNet {
    private static PhoneNumAttrApi phoneNumAttrApi = null;
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    public static PhoneNumAttrApi phoneNumAttrApiInstance() {
        if (phoneNumAttrApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://apis.juhe.cn/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            phoneNumAttrApi = retrofit.create(PhoneNumAttrApi.class);
        }
        return phoneNumAttrApi;
    }
}
