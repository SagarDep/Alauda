package com.gmail.julianrosser91.alauda.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * THis simple class is required for holding the the JSON object array
 */
public class SetArray {

    @SerializedName("objects")
    private List<Set> sets;

    public ArrayList<Set> getSets() {
        return (ArrayList<Set>) sets;
    }

    public List<Set> getSetsAsList() {
        return sets;
    }
}
