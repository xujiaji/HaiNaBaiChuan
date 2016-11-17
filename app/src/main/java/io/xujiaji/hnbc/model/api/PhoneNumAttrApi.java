package io.xujiaji.hnbc.model.api;

import io.xujiaji.hnbc.model.entity.PhoneNumAttr;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jiana on 16-11-15.
 */

public interface PhoneNumAttrApi {
    @GET("mobile/get")
    Observable<PhoneNumAttr> search(@Query("phone") String phoneNum, @Query("key") String key);
}
