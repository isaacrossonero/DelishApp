package com.example.delishapp.models;

import java.util.ArrayList;
//Step java model sub-class storing needed objects for the API responses
public class Step {
    public int number;
    public String step;
    public ArrayList<Ingredient> ingredients;
    public ArrayList<Equipment> equipment;
    public Length length;
}
