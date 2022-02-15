Design the method isDoubledList that determines 
if this list contains pairs of identical strings,
that is, the first and second strings are the same, the third and fourth are the same, 
the fifth and sixth are the same, etc


public String getFirst() {
    return null;
  }
public ILoString getRest() {
    return this;
  }

  public boolean isDoubledList() {
    return true;
  }









public String getFirst() {
    return this.first;
  }


public ILoString getRest() {
    return this.rest;
  }

public boolean isDoubledList() {
    if (this.first == this.rest.getFirst()) {
      return this.rest.getRest().isDoubledList();
    }
    else {
      return false;
    }
  }
