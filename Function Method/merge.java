  // insert the given string into the list
  public ILoString insert(String given) {
    return new ConsLoString(given, this);
  }

public ILoString merge(ILoString given) {
    return given;
  }
 
/*Design the method merge that takes this sorted 
list of Strings and a given sorted list of Strings, 
and produces a sorted list of Strings that contains all items in both original lists, 
including duplicates. (This is not the same computation as for interleave, but the two methods are similar.*/






// insert the given string into the list
  public ILoString insert(String given) {
    if (given.compareTo(this.first) <= 0) {
      return new ConsLoString(given, this);
    }
    else {
      return new ConsLoString(this.first, this.rest.insert(given));
    }
  }


public ILoString merge(ILoString given) {
    if (given instanceof MtLoString) {
      return this;
    }
    else {
      return this.rest.merge(given.insert(this.first));
    }
  }



/*Design the method merge that takes this sorted 
list of Strings and a given sorted list of Strings, 
and produces a sorted list of Strings that contains all items in both original lists, 
including duplicates. (This is not the same computation as for interleave, but the two methods are similar.*/
