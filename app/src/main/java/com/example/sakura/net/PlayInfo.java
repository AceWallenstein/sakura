package com.example.sakura.net;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface PlayInfo {
    @GET()
    Observable<String> getPlayInfo();
}
