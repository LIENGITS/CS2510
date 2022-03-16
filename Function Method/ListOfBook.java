import tester.*;

class Book {
  String title;
  String author;
  int price;

  Book(String title, String author, int price) {
    this.title = title;
    this.author = author;
    this.price = price;
  }
}

interface ILoBook {
  // count the books in this list
  int count();

  // compute the total price of the list of the books
  int totalPrice();

  // find the book that is cheaper than the given price
  ILoBook cheaperThan(int price);
  
  // insert a book to return a list of book
  ILoBook insert(Book that);
  
  /*
   * // produce the list of book published by the given date ILoBook allBefore(int
   * year);
   * 
   * // compute the total sale price of all books in this list for a given
   * discount int salePrice(int discount);
   * */
    // produce the list of book in this list that sort by the price ILoBook
    ILoBook sort();
   
}

class MtLoBook implements ILoBook {
  MtLoBook() {
  }

  public int count() {
    return 0;
  }

  public int totalPrice() {
    return 0;
  }

//find the book that is cheaper than the given price
  public ILoBook cheaperThan(int price) {
    return this;
  }

  public ILoBook insert(Book that) {
    return new ConsLoBook(that, this);
  }
  
  public ILoBook sort() {
    return this;
  }
}
 
  
  
  
class ConsLoBook implements ILoBook {
  Book first;
  ILoBook rest;

  ConsLoBook(Book first, ILoBook rest) {
    this.first = first;
    this.rest = rest;
  }

  public int count() {
    return 1 + rest.count();
  }

  public int totalPrice() {
    return this.first.price + this.rest.totalPrice();
  }

  public ILoBook cheaperThan(int price) {
    if (this.first.price <= price) {
      return new ConsLoBook(this.first, this.rest.cheaperThan(price));
    }
    else {
      return this.rest.cheaperThan(price);
    }
  }
  
  public ILoBook insert(Book that) {
    if (this.first.price < that.price)
      return new ConsLoBook(this.first, this.rest.insert(that));
    else {
      return new ConsLoBook(that, this);
    }
  }
  
  
  public ILoBook sort() {
    return this.rest.sort().insert(this.first);    //this.rest.sort()就是不断地分解rest里的值到empty，
    }                                              //到empty.insert(first)                     
      
  
}

class ILOBooksExample {
  ILOBooksExample() {
  }

  Book hp1 = new Book("HP1", "JKR", 20);
  Book hp2 = new Book("HP2", "JKR", 30);
  Book hp3 = new Book("HP3", "JKR", 40);
  
  Book hp4 = new Book("HP4", "JKR", 54540);
  Book hp5 = new Book("HP5", "JKR", 310);
  Book hp6 = new Book("HP6", "JKR", 60021451);
  

  ILoBook emptyList1 = new MtLoBook();
  ILoBook b1 = new ConsLoBook(hp6, emptyList1);
  ILoBook b2 = new ConsLoBook(hp5, b1);
  ILoBook b3 = new ConsLoBook(hp4, b2);
  
  // test the sort method
  boolean testSort(Tester t) {
    return t.checkExpect(b3.sort(), new ConsLoBook(hp5,
        new ConsLoBook(hp4, new ConsLoBook(hp6, new MtLoBook()))));
  }
  
  

  ILoBook emptyList = new MtLoBook();
  ILoBook hpList1 = new ConsLoBook(hp1, new MtLoBook());
  ILoBook hpList2 = new ConsLoBook(hp1, new ConsLoBook(hp2, new MtLoBook()));
  ILoBook hpList3 = new ConsLoBook(hp1, new ConsLoBook(hp2, new ConsLoBook(hp3, new MtLoBook())));
  ILoBook hpList4 = new ConsLoBook(hp1, this.hpList3);

  // test the method Count on the list of book class
  boolean testILoBookCount(Tester t) {
    return t.checkExpect(this.hpList3.count(), 3) && t.checkExpect(this.emptyList.count(), 0)
        && t.checkExpect(hpList1.count(), 1) && t.checkExpect(hpList4.count(), 4);
  }

  // test the method TotalPrice on the books in the list class
  boolean testILoBooktotalprice(Tester t) {
    return t.checkExpect(this.hpList1.totalPrice(), 20)
        && t.checkExpect(this.hpList2.totalPrice(), 50)
        && t.checkExpect(this.hpList3.totalPrice(), 90)
        && t.checkExpect(this.hpList4.totalPrice(), 110)
        && t.checkExpect(this.emptyList.totalPrice(), 0);
  }

  // test the method isCheaper on the books in the list class
  boolean testILoBookcheaperThen(Tester t) {
    return t.checkExpect(this.emptyList.cheaperThan(10), emptyList)
        && t.checkExpect(this.hpList1.cheaperThan(10), new MtLoBook())
        && t.checkExpect(this.hpList2.cheaperThan(30), this.hpList2)
        && t.checkExpect(this.hpList3.cheaperThan(30), this.hpList2)
        && t.checkExpect(this.hpList4.cheaperThan(30), new ConsLoBook( hp1,
            new ConsLoBook( hp1, new ConsLoBook( hp2, new MtLoBook())))); 
    
    
    
    
    
  }

}

























