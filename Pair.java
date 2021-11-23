public class Pair<X, Y> {
  private X first;
  private Y second;

  public Pair(X first, Y second) throws IllegalArgumentException {
    this.first = InputValidation.ensureNotNull(first);
    this.second = InputValidation.ensureNotNull(second);
  }

  public X getFirst() {
    return this.first;
  }

  public Y getSecond() {
    return this.second;
  }

  @Override
  public String toString() {
    return String.format("(%s, %s)", this.first, this.second);
  }
}
