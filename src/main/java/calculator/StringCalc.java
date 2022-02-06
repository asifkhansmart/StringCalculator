package calculator;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class StringCalc {

  public static int add (String numbers) {
    StringCalculator calculator = new StringCalculator(numbers);
    return  calculator.add();
  }

  static class StringCalculator {
    private final String input;

    private String delimiter = "[,\n]";
    private String numbersWithDelimiters;
    private final List<String> negativeNumbers = new ArrayList<>();

    private StringCalculator(String input) {
      this.input = input;
    }

    public int add() {
      if(input.length() == 0) {
        return 0;
      }
      parseDelimiterAndFindLineWithNumbersAndDelimiters();
      String[] inputSplittedByDelimiter = splitInputByDelimiter();
      return calculateSum(inputSplittedByDelimiter);
    }

    private void parseDelimiterAndFindLineWithNumbersAndDelimiters() {
      if (hasCustomDelimiter()) {
        parseDelimiter();
        findLineWithNumbersAndDelimiters();
      } else {
        numbersWithDelimiters = input;
      }
    }

    private boolean hasCustomDelimiter() {
      return input.startsWith("//");
    }


    private void findLineWithNumbersAndDelimiters() {
      int firstNewLine = input.indexOf("\n");
      numbersWithDelimiters = input.substring(firstNewLine + 1);
    }

    private void parseDelimiter() {
      delimiter = "";
      addDelimiters();
      delimiter = StringUtils.chop(delimiter);
    }

    private void addDelimiters() {
      int startIndexDelimiter = 0;
      while (true) {
        startIndexDelimiter = input.indexOf("[", startIndexDelimiter) + 1;
        if (startIndexDelimiter == 0) {
          break;
        }
        int endIndexDelimiter = input.indexOf("]", startIndexDelimiter);
        delimiter += input.substring(startIndexDelimiter, endIndexDelimiter) + "|";
      }
    }

    private String[] splitInputByDelimiter() {
      return numbersWithDelimiters.split(delimiter);
    }

    private int calculateSum(String[] inputSplittedByDelimiter) {
      int result = 0;
      for (String token : inputSplittedByDelimiter) {
        result += addSingleToken(token);
      }
      throwExceptionIfNegativeTokensExist();
      return result;
    }

    private void throwExceptionIfNegativeTokensExist() {
      if (hasNegativeTokens()) {
        throw new IllegalArgumentException(String.format("negatives not allowed (%s)", StringUtils
            .join(negativeNumbers, ",")));
      }
    }

    private boolean hasNegativeTokens() {
      return negativeNumbers.size() > 0;
    }

    private int addSingleToken(String token) {
      Integer valueAsInteger = Integer.parseInt(token);
      if (isNegative(valueAsInteger)) {
        negativeNumbers.add(token);
      } else if (inValidRange(valueAsInteger)) {
        return valueAsInteger;
      }
      return 0;
    }

    private boolean isNegative(Integer valueAsInteger) {
      return valueAsInteger < 0;
    }

    private boolean inValidRange(Integer valueAsInteger) {
      return valueAsInteger < 1000;
    }
  }
}
