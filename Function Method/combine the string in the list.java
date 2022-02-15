
 public String combine() {
    return "";
  }


public String combine() {
    return this.first.concat(this.rest.combine());
  }
