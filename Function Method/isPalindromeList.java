Design the method isPalindromeList that determines 
whether this list contains the same words reading the list in either order.
  
  
  
public boolean isEqual(ILoString given) {
    return (given instanceof MtLoString);
  }

 public boolean isPalindromeList() {
    return true;
  }








public boolean isPalindromeList() {
    return (this.reverse().isEqual(this));
  }

  public boolean isEqual(ILoString given) {
    if (given instanceof MtLoString) {
      return false;
    }
    else {
      if (this.first == given.getFirst()) {
        return this.rest.isEqual(given.getRest());
      }
      else {
        return false;
      }
    }
  }
