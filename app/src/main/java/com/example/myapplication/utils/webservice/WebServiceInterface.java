package com.example.myapplication.utils.webservice;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by duyjack on 2/24/17.
 */

public interface WebServiceInterface {
  @GET("tikivn/android-home-test/v2/keywords.json")
  Call<ArrayList<String>> getKeyWords();
}
