package com.example.delishapp.Listeners;

import com.example.delishapp.models.SimilarRecipeResponse;

import java.util.List;
//interface for the listener for API response, SimilarRecipeResponse
public interface SimilarRecipesListener {
    //methods
     void didFetch(List<SimilarRecipeResponse> response, String message);
     void didError(String message);
}
