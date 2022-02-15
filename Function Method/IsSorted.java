 // checks if given list is empty
  public boolean isEmpty() {
    return true;
  }

  public boolean isSorted() {
    return true;
  }

  public String getFirst() {
    return null;
  }







 // checks if given list is empty
  public boolean isEmpty() {
    return false;
  }

  public boolean isSorted() {
    if (this.isEmpty()) {
      return true;
    }
    else if (this.first == this.sort().getFirst()) {
      return this.rest.isSorted();
    }
    else {
      return false;
    }
  }
