package calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class StringCalcTest {

  @Test
  public void calculateEmptyString() {
    assertEquals(0, StringCalc.add(""));
  }

  @Test
  public void calculateSingleNumber() {
    assertEquals(1, StringCalc.add("1"));
  }

  @Test
  public void calculateAdditionOfTwoNumbers() {
    assertEquals(3, StringCalc.add("2,1"));
  }

  @Test
  public void calculateAdditionWithNewlines() {
    assertEquals(3, StringCalc.add("2\n1"));
  }

  @Test
  public void calculateMultiplicationWithCustomDelimiter() {
    assertEquals(3, StringCalc.add("//[x]\n2x1"));
  }

  @Test
  public void calculateMultiplicationWithCustomDelimiterWithLengthTwo() {
    assertEquals(3, StringCalc.add("//[xx]\n2xx1"));
  }

  @Test
  public void calculateMultiplicationWithTwoCustomDelimiter() {
    assertEquals(6, StringCalc.add("//[x][y]\n2x1y3"));
  }

  @Test
  public void calculateSingleNegativeValueException() {
    try {
      StringCalc.add("-1");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("negatives not allowed (-1)", e.getMessage());
    }
  }

  @Test
  public void calculateDoubleNegativeValueException() {
    try {
      StringCalc.add("-1,-2");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("negatives not allowed (-1,-2)", e.getMessage());
    }
  }

  @Test
  public void calculateTwoToOneThousandOne_Two() {
    assertEquals(2, StringCalc.add("2,1001"));
  }
}
