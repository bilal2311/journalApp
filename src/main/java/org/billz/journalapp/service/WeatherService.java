package org.billz.journalapp.service;

import org.billz.journalapp.api.response.WeatherResponse;
import org.billz.journalapp.cache.AppCache;
import org.billz.journalapp.constants.PlaceHolders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    RedisService redisService;

    public WeatherResponse getWeather(String city){
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if (weatherResponse != null) {
            return weatherResponse;
        }else{
            String finalAPI=appCache.appCache.get(AppCache.keys.WEATHER_API.toString())
                    .replace(PlaceHolders.CITY,city)
                    .replace(PlaceHolders.API_KEY,apiKey);
            ResponseEntity<WeatherResponse> response=restTemplate.exchange(finalAPI, HttpMethod.GET,null, WeatherResponse.class);
            WeatherResponse body=response.getBody();
            if (body != null) {
                redisService.set("weather_of_" + city, body, 300l);
            }
            return body;
        }
    }
}
