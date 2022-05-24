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
  private Map<Integer, ArrayList<Integer>> neighbours = new HashMap<>();
  Boolean[] visited = new Boolean[NUM_ELEMS];

  public ScreenLockinPattern() {
    Arrays.fill(visited, Boolean.FALSE);
    for (Integer i = 1; i <= NUM_ELEMS; i++) {
      if (i.equals(1) || i.equals(3) || i.equals(7) || i.equals(9)) {
        neighbours.put(i, new ArrayList<>(Arrays.asList(2, 4, 5, 6, 8)));
      } else if (i.equals(2) || i.equals(8)) {
        neighbours.put(i, new ArrayList<>(Arrays.asList(1, 3, 4, 5, 6, 7, 9)));
      } else if (i.equals(4) || i.equals(6)) {
        neighbours.put(i, new ArrayList<>(Arrays.asList(1, 2, 3, 5, 7, 8, 9 )));
      } else if (i.equals(5)) {
        neighbours.put(i, new ArrayList<>(Arrays.asList(1, 2, 3, 4, 6, 7, 8, 9 )));
      }
    }
  }

  public void addUsedPointNeighbours() {
    for (Integer i = 1; i <= NUM_ELEMS; i++) {
      if (i.equals(1) || i.equals(3) || i.equals(7) || i.equals(9)) {
        neighbours.put(i, new ArrayList<>(Arrays.asList(2, 4, 5, 6, 8)));
      } else if (i.equals(2) || i.equals(8)) {
        neighbours.put(i, new ArrayList<>(Arrays.asList(1, 3, 4, 5, 6, 7, 9)));
      } else if (i.equals(4) || i.equals(6)) {
        neighbours.put(i, new ArrayList<>(Arrays.asList(1, 2, 3, 5, 7, 8, 9 )));
      } else if (i.equals(5)) {
        neighbours.put(i, new ArrayList<>(Arrays.asList(1, 2, 3, 4, 6, 7, 8, 9 )));
      }
    }
    if (visited[1]) {
      ArrayList<Integer> nn = neighbours.get(1);
      if (!nn.contains(3)) {
        nn.add(3);
      }
      neighbours.put(1, nn);

      nn = neighbours.get(3);
      if (!nn.contains(1)) {
        nn.add(1);
      }
      neighbours.put(3, nn);
    }
    
    if (visited[3]) {
      ArrayList<Integer> nn = neighbours.get(1);
      if (!nn.contains(7)) {
        nn.add(7);
      }
      neighbours.put(1, nn);

      nn = neighbours.get(7);
      if (!nn.contains(1)) {
        nn.add(1);
      }
      neighbours.put(7, nn);
    }

    if (visited[4]) {
      ArrayList<Integer> nn = neighbours.get(1);
      if (!nn.contains(9)) {
        nn.add(9);
      }
      neighbours.put(1, nn);

      nn = neighbours.get(2);
      if (!nn.contains(8)) {
        nn.add(8);
      }
      neighbours.put(2, nn);

      nn = neighbours.get(3);
      if (!nn.contains(7)) {
        nn.add(7);
      }
      neighbours.put(3, nn);

      nn = neighbours.get(4);
      if (!nn.contains(6)) {
        nn.add(6);
      }
      neighbours.put(4, nn);

      nn = neighbours.get(6);
      if (!nn.contains(4)) {
        nn.add(4);
      }
      neighbours.put(6, nn);

      nn = neighbours.get(7);
      if (!nn.contains(3)) {
        nn.add(3);
      }
      neighbours.put(7, nn);

      nn = neighbours.get(8);
      if (!nn.contains(2)) {
        nn.add(2);
      }
      neighbours.put(8, nn);

      nn = neighbours.get(9);
      if (!nn.contains(1)) {
        nn.add(1);
      }
      neighbours.put(9, nn);
    }
    
    if (visited[5]) {
      ArrayList<Integer> nn = neighbours.get(3);
      if (!nn.contains(9)) {
        nn.add(9);
      }
      neighbours.put(3, nn);

      nn = neighbours.get(9);
      if (!nn.contains(3)) {
        nn.add(3);
      }
      neighbours.put(9, nn);
    }
    
    if (visited[7]) {
      ArrayList<Integer> nn = neighbours.get(7);
      if (!nn.contains(9)) {
        nn.add(9);
      }
      neighbours.put(7, nn);

      nn = neighbours.get(9);
      if (!nn.contains(7)) {
        nn.add(7);
      }
      neighbours.put(9, nn);
    }
  }

  public Integer countPatterns(int firstPoint, int length) {
    if (length < 0 || length > 9) {
      return 0;
    } else if (length == 0 || length == 1) {
      return length;
    }

    addUsedPointNeighbours();
    ArrayList<Integer> neighs = neighbours.get(firstPoint);

    // System.out.print(firstPoint + "-");
    Integer numPattterns = 0;
    visited[firstPoint - 1] = true;
    for (int i = 0; i < neighs.size(); i++) {
      if (!visited[neighs.get(i) - 1]) {
        System.out.println(firstPoint + "--> countPatterns(" + neighs.get(i) + ")");
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
