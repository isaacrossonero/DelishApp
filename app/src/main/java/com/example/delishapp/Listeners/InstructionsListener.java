package com.example.delishapp.Listeners;

import com.example.delishapp.models.InstructionsResponse;

import java.util.List;

public interface InstructionsListener {
    //interface for the listener for API response, InstructionsResponse
    void didFetch(List<InstructionsResponse> response, String message);
    void didError(String message);
}
