package com.example.delishapp.models;

import com.example.delishapp.Listeners.SimilarRecipesListener;

import java.util.List;
//SimilarRecipeResponse java model sub-class storing needed objects for the API response
public  class SimilarRecipeResponse {
    public int id;
    public String title;
    public String imageType;
    public int readyInMinutes;
    public int servings;
    public String sourceUrl;
}
