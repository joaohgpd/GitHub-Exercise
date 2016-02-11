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

import com.example.joao.myapplication.activity.RepDescActivity;
import com.example.joao.myapplication.adapter.RepAdapter;
import com.example.joao.myapplication.domain.RepService;
import com.example.joao.myapplication.domain.Repository;

import com.example.joao.myapplication.process.Search;

import java.io.IOException;
import java.util.List;


public class FragmentRepDisplay extends android.app.Fragment{
    protected RecyclerView recyclerView;
    private List<Repository> reps;
    private LinearLayoutManager mLayoutManager;
    private String url = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rep_display, container, false);

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
            taskReps(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void taskReps(String url) throws IOException {
        // Search repositories
        new SearchRepTask().execute(url);
    }

    private RepAdapter.RepOnClickListener onClickRep() {
        return new RepAdapter.RepOnClickListener() {
            @Override
            public void onClickRep(View view, int idx) {

                Repository rep = reps.get(idx);
                //Toast.makeText(getActivity(), "Repository: " + rep.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), RepDescActivity.class);
                intent.putExtra("URL", rep.getRepUrl());
                startActivity(intent);
            }
        };
    }
    private class SearchRepTask extends AsyncTask<String,Void,String> {
        ProgressDialog dialog;
        @Override protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(getActivity(), "Wait", "Searching Repositories...");

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
                RepService service = new RepService();
                try {
                    reps =  service.parserJSON(getActivity(),json);
                    if(reps!= null){
                        recyclerView.setAdapter(new RepAdapter(getActivity(), reps, onClickRep()));
                    }else
                        Toast.makeText(getActivity(), "No repository was Found", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();

            }
        }

    }



}
