package com.premiumminds.internship.screenlocking;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by aamado on 05-05-2022.
 */
class ScreenLockinPattern implements IScreenLockinPattern {
  private static final int NUM_ELEMS = 9;

  private ExecutorService executor = Executors.newSingleThreadExecutor();
  private Boolean[] visited = new Boolean[NUM_ELEMS + 1];

  public ScreenLockinPattern() {
    Arrays.fill(visited, Boolean.FALSE);
  }

  public Boolean validPattern(Integer firstPoint, Integer point) {
    Boolean isValidPattern = true;
    switch (firstPoint) {
      case 1:
        isValidPattern = (point != 3 || visited[2]) && (point != 7 || visited[4]) && (point != 9 || visited[5]);
        break;
      case 2:
        isValidPattern = (point != 8 || visited[5]);
        break;
      case 3:
        isValidPattern = (point != 1 || visited[2]) && (point != 7 || visited[5]) && (point != 9 || visited[6]);
        break;
      case 4:
        isValidPattern = (point != 6 || visited[5]);
        break;
      case 5:
        break;
      case 6:
        isValidPattern = (point != 4 || visited[5]);
        break;
      case 7:
        isValidPattern = (point != 1 || visited[4]) && (point != 3 || visited[5]) && (point != 9 || visited[8]);
        break;
      case 8:
        isValidPattern = (point != 2 || visited[5]);
        break;
      case 9:
        isValidPattern = (point != 1 || visited[5]) && (point != 3 || visited[6]) && (point != 7 || visited[8]);
        break;
    }
    return isValidPattern;
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
      return 1;
    }

    Integer numPattterns = 0;
    visited[firstPoint] = true;
    for (int point = 1; point <= NUM_ELEMS; point++) {
      if (!visited[point] && validPattern(firstPoint, point)) {
        numPattterns += countPatterns(point, length - 1);
      }
    }
    visited[firstPoint] = false;
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
