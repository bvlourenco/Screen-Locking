package com.premiumminds.internship.screenlocking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by aamado on 05-05-2022.
 */
class ScreenLockinPattern implements IScreenLockinPattern {
  private static final int NUM_ELEMS = 9;

  private ExecutorService executor = Executors.newSingleThreadExecutor();
  private Map<Integer, Neighbours> neighbours = new HashMap<>();
  Boolean[] visited = new Boolean[NUM_ELEMS];

  public ScreenLockinPattern() {
    Arrays.fill(visited, Boolean.FALSE);
    initNeighbours();
  }

  public void initNeighbours() {
    for (Integer i = 1; i <= NUM_ELEMS; i++) {
      switch (i) {
        case 5:
          neighbours.put(i, new Neighbours(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 6, 7, 8, 9))));
          break;
        case 1:
        case 3:
        case 7:
        case 9:
          neighbours.put(i, new Neighbours(new ArrayList<>(Arrays.asList(2, 4, 5, 6, 8))));
          break;
        case 2:
        case 8:
          neighbours.put(i, new Neighbours(new ArrayList<>(Arrays.asList(1, 3, 4, 5, 6, 7, 9))));
          break;
        case 4:
        case 6:
          neighbours.put(i, new Neighbours(new ArrayList<>(Arrays.asList(1, 2, 3, 5, 7, 8, 9))));
          break;
      }
    }
  }

  public void addUsedPointNeighbours() {
    for (Integer i = 1; i <= NUM_ELEMS; i++) {
      neighbours.get(i).emptyUsedPointNeighbour();
    }

    if (visited[1]) {
      neighbours.get(1).addUsedPointNeighbour(3);
      neighbours.get(3).addUsedPointNeighbour(1);
    }

    if (visited[3]) {
      neighbours.get(1).addUsedPointNeighbour(7);
      neighbours.get(7).addUsedPointNeighbour(1);
    }

    if (visited[4]) {
      neighbours.get(1).addUsedPointNeighbour(9);
      neighbours.get(2).addUsedPointNeighbour(8);
      neighbours.get(3).addUsedPointNeighbour(7);
      neighbours.get(4).addUsedPointNeighbour(6);
      neighbours.get(6).addUsedPointNeighbour(4);
      neighbours.get(7).addUsedPointNeighbour(3);
      neighbours.get(8).addUsedPointNeighbour(2);
      neighbours.get(9).addUsedPointNeighbour(1);
    }

    if (visited[5]) {
      neighbours.get(3).addUsedPointNeighbour(9);
      neighbours.get(9).addUsedPointNeighbour(3);
    }

    if (visited[7]) {
      neighbours.get(7).addUsedPointNeighbour(9);
      neighbours.get(9).addUsedPointNeighbour(7);
    }
  }
  
  public Integer countPatterns(int firstPoint, int length) {
    if (length < 0 || length > 9) {
      return 0;
    } else if (length == 0 || length == 1) {
      return length;
    }

    addUsedPointNeighbours();
    ArrayList<Integer> neighs = neighbours.get(firstPoint).getAllNeighbours();

    Integer numPattterns = 0;
    visited[firstPoint - 1] = true;
    for (int i = 0; i < neighs.size(); i++) {
      if (!visited[neighs.get(i) - 1]) {  
        numPattterns += countPatterns(neighs.get(i), length - 1);
      }
    }
    // After the end of the recursion for a given call, other possibilities of
    // patterns will be explored, so the visited point can be visited again
    // in another combination
    visited[firstPoint - 1] = false;
    return numPattterns;
  }

  /**
   * Method to count patterns from firstPoint with the length
   * 
   * @param firstPoint initial matrix position
   * @param length     the number of points used in pattern
   * @return number of patterns
   */
  public Future<Integer> countPatternsFrom(int firstPoint, int length) {
    return executor.submit(() -> {
      return countPatterns(firstPoint, length);
    });
  };
}
