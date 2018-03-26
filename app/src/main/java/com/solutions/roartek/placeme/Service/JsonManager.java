package com.solutions.roartek.placeme.Service;

/**
 * Created by Raghav.Aneja on 10-12-2016.
 */
public interface JsonManager {

    String encodeToJSON(Object object);
    Object decodeFromJSON(String response);
}
