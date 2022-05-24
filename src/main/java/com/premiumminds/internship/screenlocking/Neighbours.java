package com.premiumminds.internship.screenlocking;

import java.util.ArrayList;

public class Neighbours {
    private ArrayList<Integer> directNeighbours;
    private ArrayList<Integer> usedPointNeighbours = new ArrayList<Integer>();

    public Neighbours(ArrayList<Integer> dirNeighbours) {
        directNeighbours = dirNeighbours;
    }

    /**
     * Method that joins adjacent neighbours of the node and neighbours
     * of visited nodes and returns its result
     * 
     * @return all neighbours of current node
     */
    public ArrayList<Integer> getAllNeighbours() {
        ArrayList<Integer> allNeighbours = new ArrayList<Integer>();
        allNeighbours.addAll(directNeighbours);
        allNeighbours.addAll(usedPointNeighbours);
        return allNeighbours;
    }

    /**
     * Method that adds a new neighbour of visited node to current node
     */
    public void addUsedPointNeighbour(Integer usedPointNeighbour) {
        if (!usedPointNeighbours.contains(usedPointNeighbour)) {
            usedPointNeighbours.add(usedPointNeighbour);
        }
    }

    /**
     * Method that clears all neighbours of visted nodes
     */
    public void emptyUsedPointNeighbour() {
        usedPointNeighbours.clear();
    }

}
