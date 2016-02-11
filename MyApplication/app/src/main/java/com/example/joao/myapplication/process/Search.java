package com.example.joao.myapplication.process;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
/**
 * Created by joao on 10/02/2016.
 */
public class Search {
    OkHttpClient client = new OkHttpClient();

    // code request code here
    public String doGetRequest(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
