//Design the method reverse that produces a new list of Strings containing the same elements as this list of Strings, but in reverse order.

//Hint: The cleanest solution to this problem uses a helper method, in a style seen already in this problem.
ILoString reverse();

  ILoString reverseHelper(ILoString given);




public ILoString reverse() {
    return this.rest.reverseHelper(new ConsLoString(this.first, new MtLoString()));
  }

  public ILoString reverseHelper(ILoString given) {
    return this.rest.reverseHelper(new ConsLoString(this.first, given));
  }
