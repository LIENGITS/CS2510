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



// list first: cons A B C D
// List secoond: cons E F G H
// return a new list that its 1,3,5 place is from "list first", then its 2,4,6 is from the" second list"
// New list: cons A E B F C G D H 
