class MtLoString implements ILoString {
public ILoString sort() {
    return this;
  }
  
  public ILoString toLowerCase() {
    return this;
  }
  
   public ILoString insert(String given) {
    return new ConsLoString(given, this);
  }
  }
  



  class ConsLoString implements ILoString {
  
    public ILoString sort() {
    return this.rest.sort().insert(this.first.toLowerCase());

  }

  // to lowerCase
  public ILoString toLowerCase() {
    return new ConsLoString(this.first.toLowerCase(), this.rest.toLowerCase());
  }

  // insert the given string into the list
  public ILoString insert(String given) {
    if (given.compareTo(this.first) <= 0) {
      return new ConsLoString(given, this);
    }
    else {
      return new ConsLoString(this.first, this.rest.insert(given));
    }
  }
