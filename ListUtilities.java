import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Represents a list utilities class
 */
public class ListUtilities {

  /**
   * Maps a function over the given list to produce a new list. This function does not mutate the
   * original list
   *
   * @param inputList The list to be operated on
   * @param function  The function from X to Y
   * @param <X>       The type of the input list
   * @param <Y>       The type of the output list
   * @return A list in which each element from the input has been transformed by the given function
   */
  public static <X, Y> List<Y> map(List<X> inputList, Function<X, Y> function) {
    List<Y> outputList = new ArrayList<Y>();
    for (int index = 0; index < inputList.size(); index++) {
      X currentInputElement = inputList.get(index);
      Y currentOutputElement = function.apply(currentInputElement);
      outputList.add(currentOutputElement);
    }
    return outputList;
  }
}
