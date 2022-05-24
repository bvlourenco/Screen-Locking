package com.premiumminds.internship.screenlocking;

import java.util.ArrayList;

public class Neighbours {
    private ArrayList<Integer> directNeighbours;
    private ArrayList<Integer> usedPointNeighbours = new ArrayList<Integer>();

    public Neighbours(ArrayList<Integer> dirNeighbours) {
        directNeighbours = dirNeighbours;
    }

    public ArrayList<Integer> getAllNeighbours() {
        ArrayList<Integer> allNeighbours = new ArrayList<Integer>();
        allNeighbours.addAll(directNeighbours);
        allNeighbours.addAll(usedPointNeighbours);
        return allNeighbours;
    }

    public void addUsedPointNeighbour(Integer usedPointNeighbour) {
        if (!usedPointNeighbours.contains(usedPointNeighbour)) {
            usedPointNeighbours.add(usedPointNeighbour);
        }
    }

    public void emptyUsedPointNeighbour() {
        usedPointNeighbours.clear();
    }

}
