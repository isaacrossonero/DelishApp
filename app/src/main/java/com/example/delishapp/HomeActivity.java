package com.example.delishapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delishapp.Adapters.RandomRecipeAdapter;
import com.example.delishapp.Listeners.RandomRecipeResponseListener;
import com.example.delishapp.Listeners.RecipeClickListener;
import com.example.delishapp.models.RandomRecipeApiResponse;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    //objects
    ProgressDialog dialog;
    RequestManager manager;
    RandomRecipeAdapter randomRecipeAdapter;
    RecyclerView recyclerView;
    Spinner spinner;
    //ArrayList
    List<String> tags = new ArrayList<>();
    //searchview object to search for specific meal or specific type of meal with its ingredients
    SearchView searchView;
    //storing logged in username
    TextView username;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //displaying username
        username=(TextView)findViewById(R.id.username);

        name= getIntent().getStringExtra("value");

        username.setText(name);
        //initializing dialog
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...");
        //initializing searchView
        searchView = (SearchView) findViewById(R.id.searchView_home);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            //when user inserts text in the searchview
            public boolean onQueryTextSubmit(String query) {
                tags.clear();
                tags.add(query);
                //passing randomRecipeResponseListener object to getRandomRecipes adapter
                manager.getRandomRecipes(randomRecipeResponseListener, tags);
                dialog.show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //spinner which includes tags with different meal types
        spinner = findViewById(R.id.spinner_tags);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.tags,
                R.layout.spinner_text
        );
        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(spinnerSelectedListener);
        //attaching spinner selected listener to spinner item
        manager = new RequestManager(this);
    }

    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            //dismissing dialog
            dialog.dismiss();
            //initialising recycler view
            recyclerView = findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(HomeActivity.this, 1));
            //creating adapter
            randomRecipeAdapter = new RandomRecipeAdapter(HomeActivity.this, response.recipes, recipeClickListener);
            //passing randomRecipeAdapter
            recyclerView.setAdapter(randomRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final AdapterView.OnItemSelectedListener spinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
       //Methods
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            tags.clear();
            tags.add(adapterView.getSelectedItem().toString());
            //passing randomRecipeResponseListener object to getRandomRecipes adapter
            manager.getRandomRecipes(randomRecipeResponseListener, tags);
            dialog.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            //creating a new intent with id of recipe
            startActivity(new Intent(HomeActivity.this, RecipeDetailsActivity.class)
                    .putExtra("id", id));
        }
    };

}
