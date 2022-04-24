package com.example.delishapp.Listeners;

import com.example.delishapp.models.RandomRecipeApiResponse;
//interface for the listener for API response, RandomRecipeApiResponse
public interface RandomRecipeResponseListener {
    void didFetch(RandomRecipeApiResponse response, String message);
    void didError(String message);
}
