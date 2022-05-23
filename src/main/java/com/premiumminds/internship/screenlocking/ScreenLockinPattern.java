package com.premiumminds.internship.screenlocking;

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
  private Map<Integer, Integer[]> neighbours = new HashMap<>();
  Boolean[] visited = new Boolean[NUM_ELEMS];

  public ScreenLockinPattern() {
    Arrays.fill(visited, Boolean.FALSE);
    for (Integer i = 1; i <= NUM_ELEMS; i++) {
      if(i.equals(1) || i.equals(3) || i.equals(7) || i.equals(9)) {
        neighbours.put(i, new Integer[] { 2, 4, 5, 6, 8 }); 
      } else if (i.equals(2) || i.equals(8)) {
        neighbours.put(i, new Integer[] { 1, 3, 4, 5, 6, 7, 9 });
      } else if (i.equals(4) || i.equals(6)) {
        neighbours.put(i, new Integer[]{ 1, 2, 3, 5, 7, 8, 9 });
      } else if (i.equals(5)) {
        neighbours.put(i, new Integer[]{ 1, 2, 3, 4, 6, 7, 8, 9 });
      }
    }
  }

  public Integer countPatterns(int firstPoint, int length) {
    if (length < 0 || length > 9) {
      //TODO: check if exception makes sense here
      return 0;
    } else if (length == 0 || length == 1) {
      return length;
    }
    int numPattterns = 0;
    visited[firstPoint - 1] = true;
    Integer[] neighs = neighbours.get(firstPoint);
    for (int i = 0; i < neighs.length; i++) {
      if (!visited[neighs[i] - 1]) {
        numPattterns += countPatterns(neighs[i], length-1);
      }
    }
    return numPattterns;
  }

 /**
  * Method to count patterns from firstPoint with the length
  * @param firstPoint initial matrix position
  * @param length the number of points used in pattern
  * @return number of patterns
  */
 public Future<Integer> countPatternsFrom(int firstPoint, int length) {
    return executor.submit(() -> {
      return countPatterns(firstPoint, length);
    });
  };
}
