package com.example.joao.myapplication.domain;

/**
 * Created by joao on 10/02/2016.
 */
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.joao.myapplication.activity.UserActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UserService {
    private static final boolean LOG_ON = false;
    private static final String TAG = "UserService";

    public List<User> parserJSON(Context context, String json) throws IOException {
        List<User> users = new ArrayList<User>();
        try {
            JSONObject root = new JSONObject(json);
            boolean incompleteResults = Boolean.parseBoolean(root.getString("incomplete_results"));
            if(incompleteResults) {
                Toast.makeText(context,"Erro do servidor... \nTente mais tarde",Toast.LENGTH_SHORT).show();
                return null;
            }

            int totalObj = Integer.parseInt(root.getString("total_count"));
            if(totalObj==0){
                return null;
            }
            if(totalObj>30){
                totalObj=29;
            }
            JSONArray jsonArray = root.getJSONArray("items");

            for(int i=0;i<totalObj;i++){
               User user = new User();
               JSONObject jsonUser = jsonArray.getJSONObject(i);
               user.setName(jsonUser.getString("login"));
               user.setUrlFoto(jsonUser.getString("avatar_url"));
               user.setScore(jsonUser.getString("score"));
               user.setUserUrl(jsonUser.getString("html_url"));
               users.add(user);
            }

        } catch (JSONException e) {
            throw new IOException(e.getMessage(), e);
        }
        return users;
    }
}
