package com.example.joao.myapplication.activity;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joao.myapplication.R;
import com.example.joao.myapplication.fragment.FragmentUserSearch;
import com.example.joao.myapplication.fragment.FragmentUserDisplay;

public class UserActivity extends AppCompatActivity {
    private static final String URL = "https://api.github.com/search/users?q=";
    private FragmentUserSearch userSearch;
    private FragmentUserDisplay userDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        overridePendingTransition(R.anim.filho_entrando,R.anim.pai_saindo);
        if (savedInstanceState == null) {
            this.userSearch = new FragmentUserSearch();
            this.userDisplay = new FragmentUserDisplay();
            android.app.FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.layout_user, this.userSearch);
            ft.commit();

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Search User");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(getApplication(), "No Settings Available", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if(this.userSearch.isVisible()){
             supportFinishAfterTransition();
        }else{
            android.app.FragmentManager fm = getFragmentManager();
            FragmentTransaction ft =fm.beginTransaction();
            ft.setCustomAnimations(R.anim.slide_in_rtl, R.anim.slide_out_rtl);
            ft.replace(R.id.layout_user, this.userSearch);

            ft.commit();

        }
    }
    public void searchUserOnGitHub(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        String name = editText.getText().toString();
        if(name.isEmpty()||name.trim().isEmpty()){
            Toast.makeText(getApplication(),"Please insert a user name for search",Toast.LENGTH_SHORT).show();
        }else {
            String nameSearch = URL + name;
            Bundle bundle = new Bundle();
            bundle.putString("URL", nameSearch);
            this.userDisplay.setArguments(bundle);

            android.app.FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(R.anim.slide_in_ltr, R.anim.slide_out_ltr);
            ft.replace(R.id.layout_user, this.userDisplay);
            ft.commit();
        }



    }



}
