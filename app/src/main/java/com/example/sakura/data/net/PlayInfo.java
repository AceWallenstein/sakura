package com.example.sakura.data.net;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface PlayInfo {
    @GET()
    Observable<String> getPlayInfo();
}
