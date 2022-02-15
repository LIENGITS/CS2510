public ILoString interleave(ILoString given) {
    return given;
  }

  public ILoString getRest() {
    return this;
  }




  public ILoString interleave(ILoString given) {
    if (given instanceof MtLoString) {
      return this;
    }
    else {
      return new ConsLoString(this.first,
          new ConsLoString(given.getFirst(), this.rest.interleave(given.getRest())));
    }
  }

  public ILoString getRest() {
    return this.rest;
  }
