package com.example.joao.myapplication.domain;

/**
 * Created by joao on 10/02/2016.
 */
import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RepService {
    private static final boolean LOG_ON = false;
    private static final String TAG = "RepService";

    public List<Repository> parserJSON(Context context, String json) throws IOException {
        List<Repository> reps = new ArrayList<Repository>();
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
               Repository rep = new Repository();
               JSONObject jsonUser = jsonArray.getJSONObject(i);
               rep.setName(jsonUser.getString("name"));
               rep.setOwner(jsonUser.getJSONObject("owner").getString("login"));
               rep.setRepUrl(jsonUser.getString("html_url"));
               rep.setDescription(jsonUser.getString("description"));
               rep.setAvatarUrl(jsonUser.getJSONObject("owner").getString("avatar_url"));
               reps.add(rep);
            }

        } catch (JSONException e) {
            throw new IOException(e.getMessage(), e);
        }
        return reps;
    }
}
