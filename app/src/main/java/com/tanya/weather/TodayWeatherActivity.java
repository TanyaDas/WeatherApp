package com.tanya.weather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.appbar.MaterialToolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodayWeatherActivity extends AppCompatActivity {
    private static final String API_KEY = "https://api.openweathermap.org/data/2.5/onecall?lat=12.9082847623315&lon=77.65197822993314&units=imperial&appid=b143bb707b2ee117e62649b358207d3e";
    Context context;
    MaterialToolbar materialToolbar;
    TextView pageTitle, tempTxt, weatherTxt, weatherDetailTxt, humidityTxt, windSpeedTxt;
    ImageView backImg;
    CardView logoutCv;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private WeatherApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_weather);
        init();
        pageTitle.setText("Weather");
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        logoutCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
        fetchWeather();
    }

    private void fetchWeather() {
        apiService = RetrofitClient.getClient().create(WeatherApiService.class);

        Call<WeatherModal> call = apiService.getCurrentWeather(API_KEY);

        call.enqueue(new Callback<WeatherModal>() {
            @Override
            public void onResponse(Call<WeatherModal> call, Response<WeatherModal> response) {

                if (response.isSuccessful()) {
                    WeatherModal weatherResponse = response.body();
                    if (weatherResponse != null) {
                        // Access weather data from the weatherResponse object
                        float temp = weatherResponse.getTemp();
                        String description = weatherResponse.getDescription();
                        int humidity = weatherResponse.getHumidity();
                        String main = weatherResponse.getMain();
                        float windSpeed = weatherResponse.getWind_speed();
                        windSpeedTxt.setText("Wind Speed :-" + windSpeed);
                        tempTxt.setText("Temp :- " + temp);
                        weatherTxt.setText("Weather :-" + main);
                        humidityTxt.setText("Humidity :-" + humidity);
                        weatherDetailTxt.setText("Weather Details :-" + description);
                    }
                } else {
                    // Handle error response
                    Toast.makeText(TodayWeatherActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherModal> call, Throwable t) {
                // Handle network errors or API call failures
            }
        });
    }


    public void logoutUser() {
        editor.clear();
        editor.apply();
        Toast.makeText(context, "You have successfully logged out!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void init() {
        context = this;
        sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        materialToolbar = (MaterialToolbar) findViewById(R.id.toolbar);
        pageTitle = (TextView) findViewById(R.id.toolbarTitle);
        backImg = (ImageView) findViewById(R.id.back_toolbar);
        logoutCv = (CardView) findViewById(R.id.logoutCv);
        tempTxt = (TextView) findViewById(R.id.temp_txt);
        weatherTxt = (TextView) findViewById(R.id.weatherTxt);
        weatherDetailTxt = (TextView) findViewById(R.id.weatherDetailsTxt);
        humidityTxt = (TextView) findViewById(R.id.humidityTxt);
        windSpeedTxt = (TextView) findViewById(R.id.windSpeedTxt);
    }
}