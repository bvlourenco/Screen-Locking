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

  /**
   * Method to initialize a map containing each node as key and its neighbours as values
   */
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

  /**
   * Method to add new neighbours to nodes after a given node was visited
   * (in order to follow rule 4 which states that in a pattern we can pass
   *  over used points)
   */
  public void addUsedPointNeighbours() {
    for (Integer i = 1; i <= NUM_ELEMS; i++) {
      neighbours.get(i).emptyUsedPointNeighbour();
    }

    //Visited[1] represents node 2
    if (visited[1]) {
      neighbours.get(1).addUsedPointNeighbour(3);
      neighbours.get(3).addUsedPointNeighbour(1);
    }

    //Visited[3] represents node 4
    if (visited[3]) {
      neighbours.get(1).addUsedPointNeighbour(7);
      neighbours.get(7).addUsedPointNeighbour(1);
    }

    //Visited[4] represents node 5
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

    //Visited[5] represents node 6
    if (visited[5]) {
      neighbours.get(3).addUsedPointNeighbour(9);
      neighbours.get(9).addUsedPointNeighbour(3);
    }

    //Visited[7] represents node 8
    if (visited[7]) {
      neighbours.get(7).addUsedPointNeighbour(9);
      neighbours.get(9).addUsedPointNeighbour(7);
    }
  }

  /**
   * Method to count patterns from firstPoint with the length
   * 
   * @param firstPoint initial matrix position
   * @param length     the number of points used in pattern
   * @return number of patterns
   */
  public Integer countPatterns(int firstPoint, int length) {
    if (length < 1 || length > 9 || firstPoint < 1 || firstPoint > 9) {
      return 0;
    } else if (length == 1) {
      return length;
    }

    /*After a node was visited, check for neighbours of visited nodes
      because a pattern can pass over a used point*/
    addUsedPointNeighbours();
    ArrayList<Integer> neighs = neighbours.get(firstPoint).getAllNeighbours();

    Integer numPattterns = 0;
    visited[firstPoint - 1] = true;
    for (int i = 0; i < neighs.size(); i++) {
      if (!visited[neighs.get(i) - 1]) {  
        numPattterns += countPatterns(neighs.get(i), length - 1);
      }
    }
    /*After the end of the recursion for a given call, other possibilities of
      patterns will be explored, so the visited point can be visited again
      in another combination*/
    visited[firstPoint - 1] = false;
    return numPattterns;
  }

  /**
   * Method to submit a new task to count patterns
   * 
   * @param firstPoint initial matrix position
   * @param length     the number of points used in pattern
   * @return Future that contains the pending result of the task
   */
  public Future<Integer> countPatternsFrom(int firstPoint, int length) {
    return executor.submit(() -> {
      return countPatterns(firstPoint, length);
    });
  };
}
