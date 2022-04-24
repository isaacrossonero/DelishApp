package com.example.delishapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delishapp.Adapters.IngredientsAdapter;
import com.example.delishapp.Adapters.InstructionsAdapter;
import com.example.delishapp.Adapters.SimilarRecipeAdapter;
import com.example.delishapp.Listeners.InstructionsListener;
import com.example.delishapp.Listeners.RecipeClickListener;
import com.example.delishapp.Listeners.RecipeDetailsListener;
import com.example.delishapp.Listeners.SimilarRecipesListener;
import com.example.delishapp.models.InstructionsResponse;
import com.example.delishapp.models.RecipeDetailsResponse;
import com.example.delishapp.models.SimilarRecipeResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity {
    //objects
    //recipe id
    int id;
    //according to activity_recipe_details.xml
    TextView textView_meal_name, textView_meal_source,textView_meal_summary;
    ImageView imageView_meal_image;
    RecyclerView recycler_meal_ingredients, recycler_meal_similar, recycler_meal_instructions;

    RequestManager manager;
    ProgressDialog dialog;
    //Adapters
    IngredientsAdapter ingredientsAdapter;
    SimilarRecipeAdapter similarRecipeAdapter;
    InstructionsAdapter instructionsAdapter;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        findViews();
        //capturing id from intent
        id = Integer.parseInt(getIntent().getStringExtra("id"));
        //adding APIs
        manager = new RequestManager(this);
        manager.getRecipeDetails(recipeDetailsListener, id);
        manager.getSimilarRecipes(similarRecipesListener, id);
        manager.getInstructions(instructionsListener, id);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading Details...");
        dialog.show();
    }

    private void findViews() {
        //initialising objects
        textView_meal_name = findViewById(R.id.textView_meal_name);
        textView_meal_source = findViewById(R.id.textView_meal_source);
        textView_meal_summary = findViewById(R.id.textView_meal_summary);
        imageView_meal_image = findViewById(R.id.imageView_meal_image);
        recycler_meal_ingredients = findViewById(R.id.recycler_meal_ingredients);
        recycler_meal_similar = findViewById(R.id.recycler_meal_similar);
        recycler_meal_instructions = findViewById(R.id.recycler_meal_instructions);
    }

    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {
            dialog.dismiss();
            //showing all data inside the views
            //recipe name
            textView_meal_name.setText(response.title);
            //recipe source
            textView_meal_source.setText("Source: "+response.sourceName);
            //recipe summary
            textView_meal_summary.setText(response.summary);
            //recipe image
            Picasso.get().load(response.image).into(imageView_meal_image);

            recycler_meal_ingredients.setHasFixedSize(true);
            recycler_meal_ingredients.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
            //instance of IngredientsAdapter
            ingredientsAdapter = new IngredientsAdapter(RecipeDetailsActivity.this, response.extendedIngredients);
            recycler_meal_ingredients.setAdapter(ingredientsAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final SimilarRecipesListener similarRecipesListener = new SimilarRecipesListener(){
        @Override
        public void didFetch(List<SimilarRecipeResponse> response, String message){
        recycler_meal_similar.setHasFixedSize(true);
        recycler_meal_similar.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
        similarRecipeAdapter = new SimilarRecipeAdapter(RecipeDetailsActivity.this, response, recipeClickListener);
        recycler_meal_similar.setAdapter(similarRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
    //listener used for recipe details
    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
    @Override
    public void onRecipeClicked(String id) {
        //loading activity with new intent passing recipe id
        startActivity(new Intent(RecipeDetailsActivity.this, RecipeDetailsActivity.class)
        .putExtra("id", id));
    }
};
    //adding listener
    private final InstructionsListener instructionsListener = new InstructionsListener() {
    @Override
    public void didFetch(List<InstructionsResponse> response, String message) {
        //adding data to recycler view
        recycler_meal_instructions.setHasFixedSize(true);
        recycler_meal_instructions.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.VERTICAL, false));
        instructionsAdapter = new InstructionsAdapter(RecipeDetailsActivity.this, response);
        //attaching adapter
        recycler_meal_instructions.setAdapter(instructionsAdapter);
    }

    @Override
    public void didError(String message) {
    }
};

}
