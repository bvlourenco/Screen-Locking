package com.premiumminds.internship.screenlocking;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by aamado on 05-05-2022.
 */
@RunWith(JUnit4.class)
public class ScreenLockinPatternTest {

  /**
   * The corresponding implementations to test.
   *
   * If you want, you can make others :)
   *
   */
  public ScreenLockinPatternTest() {
  }

  public void executeTest(int firstPoint, int length, int expectedResult)
      throws InterruptedException, ExecutionException, TimeoutException {
    Future<Integer> count = new ScreenLockinPattern().countPatternsFrom(firstPoint, length);
    Integer result = count.get(10, TimeUnit.SECONDS);
    assertEquals(result.intValue(), expectedResult);
  }

  @Test
  public void ScreenLockinPatternTestFirst3Length0Test()
      throws InterruptedException, ExecutionException, TimeoutException {
    executeTest(3, 0, 0);
  }

  @Test
  public void ScreenLockinPatternTestFirst3Length1Test()
      throws InterruptedException, ExecutionException, TimeoutException {
    executeTest(3, 1, 1);
  }

  @Test
  public void ScreenLockinPatternTestFirst3LengthNeg1Test()
      throws InterruptedException, ExecutionException, TimeoutException {
    executeTest(3, -1, 0);
  }

  @Test
  public void ScreenLockinPatternTestFirst3Length11Test()
      throws InterruptedException, ExecutionException, TimeoutException {
    executeTest(3, 11, 0);
  }

  @Test
  public void ScreenLockinPatternTestFirstNeg1Length2Test()
      throws InterruptedException, ExecutionException, TimeoutException {
    executeTest(-1, 2, 0);
  }

  @Test
  public void ScreenLockinPatternTestFirst11Length2Test()
      throws InterruptedException, ExecutionException, TimeoutException {
    executeTest(11, 2, 0);
  }

  @Test
  public void ScreenLockinPatternTestFirst3Length2Test()
      throws InterruptedException, ExecutionException, TimeoutException {
    executeTest(3, 2, 5);
  }

  @Test
  public void ScreenLockinPatternTestFirst2Length2Test()
      throws InterruptedException, ExecutionException, TimeoutException {
    executeTest(2, 2, 7);
  }

  @Test
  public void ScreenLockinPatternTestFirst5Length2Test()
      throws InterruptedException, ExecutionException, TimeoutException {
    executeTest(5, 2, 8);
  }

  @Test
  public void ScreenLockinPatternTestFirst3Length3Test()
      throws InterruptedException, ExecutionException, TimeoutException {
    executeTest(3, 3, 31);
  }

  @Test
  public void ScreenLockinPatternTestFirst5Length3Test()
      throws InterruptedException, ExecutionException, TimeoutException {
    executeTest(5, 3, 48);
  }

  @Test
  public void ScreenLockinPatternTestFirst5Length4Test()
      throws InterruptedException, ExecutionException, TimeoutException {
    executeTest(5, 4, 256);
  }
}