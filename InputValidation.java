/**
 * Represents a utilities class used for input validation
 */
public class InputValidation {

  /**
   * Ensures that the desired input is strictly greater than the given lower bound
   *
   * @param input      The desired input to be validated
   * @param lowerBound The strict lower bound
   * @param message    The error message to be thrown if the condition is not met
   * @return The desired input if condition is met.
   * @throws IllegalArgumentException if the desired input is greater than the lower bound
   */
  public static int ensureGreaterThan(int input, int lowerBound, String message)
      throws IllegalArgumentException {
    if (input < lowerBound) {
      throw new IllegalArgumentException(message);
    } else {
      return input;
    }
  }

  /**
   * Ensures that the desired input is strictly less than the given upper bound
   *
   * @param input      The desired input to be validated
   * @param upperBound The strict upper bound
   * @param message    The error message to be thrown if the condition is not met
   * @return The desired input if condition is met
   * @throws IllegalArgumentException if the desired input is less than the upper bound
   */
  public static int ensureLessThan(int input, int upperBound, String message)
      throws IllegalArgumentException {
    if (input > upperBound) {
      throw new IllegalArgumentException(message);
    } else {
      return input;
    }
  }

  /**
   * Ensures that the desired input is strictly greater than the given lower bound AND strictly less
   * than the given upper bound
   *
   * @param input      The desired input to be validated
   * @param lowerBound The strict lower bound
   * @param upperBound The strict upper bound
   * @param message    The error message to be throws if all conditions are not met
   * @return The desired input if all conditions are met
   * @throws IllegalArgumentException if the desired input is strictly greater than the lower bound
   *                                  or strictly less than the upper bound
   */
  public static int ensureWithin(int input, int lowerBound, int upperBound, String message)
      throws IllegalArgumentException {
    if (input > upperBound || input < lowerBound) {
      throw new IllegalArgumentException(message);
    } else {
      return input;
    }
  }

  public static <X> X ensureNotNull(X input) throws IllegalArgumentException {
    if (input == null) {
      throw new IllegalArgumentException("Input cannot be null");
    } else {
      return input;
    }
  }
}
