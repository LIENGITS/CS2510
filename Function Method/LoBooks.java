import tester.*;

class Book {
  String title;
  Author author;
  int price;
  
  Book(String title, Author author, int price) {
    this.title = title;
    this.author = author;
    this.price = price;
  }
}

class Author{
  String name;
  int yob;
  
  Author(String name, int yob) {
    this.name = name;
    this.yob = yob;
  }
}



interface ILoBook{
  // count the book
  int countBook();
  
  double totalSalesPrice(int discount);
  
  ILoBook publisheHelper(int b);
  
  ILoBook publisheBefore(int authorYob);
  
  ILoBook insert(Book that);
  
  ILoBook sortBook();
  
}

class MtLoBook implements ILoBook{
  MtLoBook(){
}
  public int countBook() {
    return 0;
  }
  
  public double totalSalesPrice(int discount) {
    return 0;
  }
  
  public ILoBook publisheHelper(int b) {
    return this;
  }
  
  public ILoBook publisheBefore(int authorYob) {
    return this;
  }
  
  public ILoBook insert(Book that) {
    return new ConsLoBook(that, this);
  }
  
  public ILoBook sortBook() {
    return this;
    
  }
  
}

class ConsLoBook implements ILoBook{
  Book first;
  ILoBook rest;
  
  ConsLoBook(Book first, ILoBook rest){
    this.first = first;
    this.rest = rest;
  }
  
  public int countBook() {
    return 1 + rest.countBook();
  }
  
  public double totalSalesPrice(int discount) {
    return this.first.price - this.first.price * (1.0/discount) + rest.totalSalesPrice(discount);
  }
  
  
  
  public ILoBook publisheHelper(int b) {
    if (this.first.author.yob < b) {
      return new ConsLoBook(this.first, rest.publisheBefore(b));  
    }
    else {
      return rest.publisheBefore(b);
      }
  }
  
  public ILoBook publisheBefore(int authorYob) {
    return this.publisheHelper(authorYob);
  }
  
  
  public ILoBook insert(Book that) {
    if (this.first.price < that.price) {
      return new ConsLoBook(this.first, rest.insert(that));
    }
    else {
      return new ConsLoBook(that, this);
    }
  }
   
  
  public ILoBook sortBook() {
    return rest.sortBook().insert(first);
    
  }
  
}




class examplesPractiseBooks{
  Author a1 = new Author("Peter", 1992);
  Author a2 = new Author("John", 1828);
  Author a3 = new Author("Wang", 1545);
  Author a4 = new Author("Denial", 1145);
  Author a5 = new Author("Steve", 2023);
  Author a6 = new Author("Nick", 2121);
  Author a7 = new Author("Jons", 1945);
  
  Book b1 = new Book("Night", a1, 20);
  Book b2 = new Book("tomorrow", a2, 80);
  Book b3 = new Book("water", a3, 12);
  Book b4 = new Book("Night", a4, 2);
  Book b5 = new Book("Ok", a5, 211);
  Book b6 = new Book("Let's movie", a6, 121);
  Book b7 = new Book("All Right", a7, 32);
  
  ILoBook empty = new MtLoBook();
  ILoBook l1 = new ConsLoBook(b1, empty);
  ILoBook l2 = new ConsLoBook(b2, l1);
  ILoBook l3 = new ConsLoBook(b3, l2);
  ILoBook l4 = new ConsLoBook(b4, l3);
  ILoBook l5 = new ConsLoBook(b5, l4);
  ILoBook l6 = new ConsLoBook(b6, l5);
  ILoBook l7 = new ConsLoBook(b7, l6);
  
  ILoBook sortBook = new ConsLoBook(b4, 
      new ConsLoBook(b3, 
      new ConsLoBook(b1, 
          new ConsLoBook(b7, 
              new ConsLoBook(b2, 
                  new ConsLoBook(b6, new ConsLoBook(b5, empty)))))));
  
  boolean testCountBook(Tester t) {
    return t.checkExpect(l7.countBook(), 7);
  }
    
    
    boolean testtotalSalesPrice(Tester t) {
      return t.checkExpect(l2.totalSalesPrice(10), 90.0);
    }
    
    boolean testPublisheBefore(Tester t) {
      return t.checkExpect(l7.publisheBefore(1600), new ConsLoBook(b4, new ConsLoBook(b3, empty)));
    }
    
      
    
    boolean testSortBook(Tester t) {
      return t.checkExpect(l7.sortBook(), sortBook);
    }
    
    
    
    
    
  }
  
  
