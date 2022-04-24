package com.example.delishapp;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.delishapp.Listeners.InstructionsListener;
import com.example.delishapp.Listeners.RandomRecipeResponseListener;
import com.example.delishapp.Listeners.RecipeDetailsListener;
import com.example.delishapp.Listeners.SimilarRecipesListener;
import com.example.delishapp.models.InstructionsResponse;
import com.example.delishapp.models.RandomRecipeApiResponse;
import com.example.delishapp.models.RecipeDetailsResponse;
import com.example.delishapp.models.SimilarRecipeResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
//API calling class
public class RequestManager {
    //context object
    Context context;
    //retrofit object
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    //constructor
    public RequestManager(Context context){
        this.context = context;
    }

    //method which gets random recipes, using the RandomRecipeResponseListener
    public void getRandomRecipes(RandomRecipeResponseListener listener, List<String> tags){
        //instance of CallRandomRecipes
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
        //Call object with API key and total number of random recipes to be called, as parameters
        Call<RandomRecipeApiResponse> call = callRandomRecipes.callRandomRecipe(context.getString(R.string.api_key), "10", tags);
        call.enqueue(new Callback<RandomRecipeApiResponse>() {
        @Override
        public void onResponse(@NonNull Call<RandomRecipeApiResponse> call, @NonNull Response<RandomRecipeApiResponse> response) {
            if(!response.isSuccessful()) {
                listener.didError(response.message());
                return;
            }
            listener.didFetch(response.body(), response.message());
        }

        @Override
        public void onFailure(@NonNull Call<RandomRecipeApiResponse> call, Throwable t) {
            listener.didError(t.getMessage());
        }
    });
    }
    //pass RecipeDetailsListener and id
    public void getRecipeDetails(RecipeDetailsListener listener, int id){
        CallRecipeDetails callRecipeDetails = retrofit.create(CallRecipeDetails.class);
        //id and API key as parameters
        Call<RecipeDetailsResponse> call = callRecipeDetails.callRecipeDetails(id, context.getString(R.string.api_key));
        //creating call back
        call.enqueue(new Callback<RecipeDetailsResponse>() {
            @Override
            public void onResponse(Call<RecipeDetailsResponse> call, Response<RecipeDetailsResponse> response) {
                //not successful
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                //successful
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RecipeDetailsResponse> call,  Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getSimilarRecipes(SimilarRecipesListener listener, int id){
        CallSimilarRecipes callSimilarRecipes = retrofit.create(CallSimilarRecipes.class);
        //id and API key as parameters
        Call<List<SimilarRecipeResponse>> call= callSimilarRecipes.callSimilarRecipe(id, "4", context.getString(R.string.api_key));
        call.enqueue(new Callback<List<SimilarRecipeResponse>>() {
            @Override
            public void onResponse(Call<List<SimilarRecipeResponse>> call, Response<List<SimilarRecipeResponse>> response) {
                //not successful
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                //successful
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(@NonNull Call<List<SimilarRecipeResponse>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }
    //calling method (listener in parameter)
    public void getInstructions(InstructionsListener listener, int id){
        //creatng an instance of method
        CallInstructions callInstructions = retrofit.create(CallInstructions.class);
        Call<List<InstructionsResponse>> call = callInstructions.callInstructions(id, context.getString(R.string.api_key));
        call.enqueue(new Callback<List<InstructionsResponse>>() {
            @Override
            public void onResponse(Call<List<InstructionsResponse>> call, Response <List<InstructionsResponse>> response) {
                //unsuccessful response
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                //successful response
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call <List<InstructionsResponse>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }
    //interface for random recipe API call
    private interface CallRandomRecipes{
        //GET method, using website URL
        @GET("recipes/random")
        Call<RandomRecipeApiResponse> callRandomRecipe(
                //parameters according to API documentation
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("tags") List<String> tags
        );
    }
        //interface for recipe details API call
        private interface CallRecipeDetails{
            //GET method, using website URL according to API documentation
        @GET("recipes/{id}/information")
            Call<RecipeDetailsResponse> callRecipeDetails(
                    @Path("id") int id,
                    @Query("apiKey") String apiKey
        );
        }
    //interface for similar recipes API call
        private interface CallSimilarRecipes{
        //GET method, using website URL according to API documentation
        @GET("recipes/{id}/similar")
            Call<List<SimilarRecipeResponse>> callSimilarRecipe(
            @Path("id") int id,
                @Query("number") String number,
                @Query("apiKey") String apiKey
        );
        }
        //interface for recipe instructions API call
        private interface CallInstructions{
            //GET method, using website URL according to API documentation
            @GET("recipes/{id}/analyzedInstructions")
                    //method calling list
            Call<List<InstructionsResponse>> callInstructions(
                    @Path("id") int id,
                    @Query("apiKey") String apiKey
            );
        }
}
