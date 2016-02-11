package com.example.joao.myapplication.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.joao.myapplication.R;
import com.example.joao.myapplication.activity.UserDescActivity;
import com.example.joao.myapplication.adapter.UserAdapter;
import com.example.joao.myapplication.domain.User;
import com.example.joao.myapplication.domain.UserService;
import com.example.joao.myapplication.process.Search;

import java.io.IOException;
import java.util.List;


public class FragmentUserDisplay extends android.app.Fragment{
    protected RecyclerView recyclerView;
    private List<User> users;
    private LinearLayoutManager mLayoutManager;
    private String url = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_display, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        url = args.getString("URL");
        try {
            taskUsers(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void taskUsers(String url) throws IOException {
        // Search Users
        new SearchUserTask().execute(url);
    }

    private UserAdapter.UserOnClickListener onClickUser() {
        return new UserAdapter.UserOnClickListener() {
            @Override
            public void onClickUser(View view, int idx) {

                User user = users.get(idx);
                //Toast.makeText(getActivity(), "Usuario: " + user.getNome(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), UserDescActivity.class);
                intent.putExtra("URL", user.getUserUrl());
                startActivity(intent);
            }
        };
    }
    private class SearchUserTask extends AsyncTask<String,Void,String> {
        ProgressDialog dialog;
        @Override protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(getActivity(), "Wait", "Searching Users...");

        }

        @Override
        protected String doInBackground(String... params){
            String getResponse=null;
            try{
                Search search = new Search();
                getResponse = search.doGetRequest(params[0]);
            }catch (Exception e){
                Log.e("teste", e.getMessage(), e);
            }

            return getResponse;
        }
        protected void onPostExecute(String json){

            if(json!=null){
                UserService service = new UserService();
                try {
                    users =  service.parserJSON(getActivity(),json);
                    if(users!= null){
                        recyclerView.setAdapter(new UserAdapter(getActivity(), users, onClickUser()));
                    }else
                        Toast.makeText(getActivity(), "No user was found", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();

            }
        }

    }



}
