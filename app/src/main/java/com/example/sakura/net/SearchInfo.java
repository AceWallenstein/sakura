package com.example.sakura.net;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SearchInfo {
    @FormUrlEncoded
    @POST("search.asp")
    Observable<ResponseBody> search(@Field(value = "searchword", encoded = false) String searchword);
}
