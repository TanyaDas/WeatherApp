package com.tanya.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("weather")
    Call<WeatherModal> getCurrentWeather(
            @Query("appid") String apiKey
    );
}
