package com.example.delishapp.Listeners;

import com.example.delishapp.models.RecipeDetailsResponse;
//interface for the listener for API response, RecipeDetailsResponse
public interface RecipeDetailsListener {
    //methods
    void didFetch(RecipeDetailsResponse response, String message);
    void didError(String message);
}
