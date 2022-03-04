import tester.Tester;

//to represent a generic comparator
interface IComparator<T> {
  // Returns a negative number if t1 comes before t2 in this ordering
  // Returns zero if t1 is the same as t2 in this ordering
  // Returns a positive number if t1 comes after t2 in this ordering
  int compare(T t1, T t2);
}

//to represent a book
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

//to represent a list
interface IList<T> {
//builds a tree given an ABST
  // accumulator: holds the built tree
  ABST<T> buildTree(ABST<T> t);

  // are the two lists the same?
  boolean sameList(IList<T> l);

  //are they are empty?
  boolean sameEmpty(Empty<T> l);

  boolean sameCons(Cons<T> l);

  // to check if they are sorted 
  // passes into the isSortedHelper
  boolean isSorted(IComparator<T> fun);
  boolean isSortedHelp(IComparator<T> fun, T first);

  IList<T> merge(IList<T> l, IComparator<T> fun);

  IList<T> mergeWithCons(Cons<T> l, IComparator<T> fun);

  IList<T> mergeWithMt(Empty<T> l, IComparator<T> fun);

  IList<T> sort(IComparator<T> fun);

}


class StrLexComp implements IComparator<String> {
  // to compare and return the 
  public int compare(String s1, String s2) {
    return s1.compareTo(s2);
  }

}

class StrLengthComp implements IComparator<String> {
  public int compare(String s1, String s2) {
    return s1.length() - s2.length();
  }

}

class Empty<T> implements IList<T> {
  Empty() {
    // Its a empty list of T
  }

  public boolean isSorted(IComparator<T> fun) {
    return true;
  }

  public boolean isSortedHelp(IComparator<T> fun, T s1) {
    return true;
  }

  public IList<T> sort(IComparator<T> fun) {
    return this;
  }

  public IList<T> merge(IList<T> l, IComparator<T> fun) {
    return l.mergeWithMt(this, fun);
  }

  public boolean sameList(IList<T> l) {
    return l.sameEmpty(this);
  }

  public boolean sameEmpty(Empty<T> l) {
    return true;
  }

  public boolean sameCons(Cons<T> l) {
    return false;
  }

  public IList<T> mergeWithCons(Cons<T> l, IComparator<T> fun) {
    return l;
  }

  public IList<T> mergeWithMt(Empty<T> l, IComparator<T> fun) {
    return this;
  }

  public ABST<T> buildTree(ABST<T> t) {
    return t;
  }
}

class Cons<T> implements IList<T> {
  T first;
  IList<T> rest;

  Cons(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  public boolean isSorted(IComparator<T> fun) {
    return this.rest.isSortedHelp(fun, this.first);
  }

  public boolean isSortedHelp(IComparator<T> fun, T s1) {
    return fun.compare(s1, this.first) <= 0 && this.rest.isSortedHelp(fun, this.first);
  }

  public IList<T> sort(IComparator<T> fun) {
    return new Cons<T>(this.first, new Empty<T>()).merge(this.rest.sort(fun), fun);
  }

  // merge using double dispatch
  public IList<T> merge(IList<T> l, IComparator<T> fun) {
    return l.mergeWithCons(this, fun);
  }

  public IList<T> mergeWithCons(Cons<T> l, IComparator<T> fun) {
    if (fun.compare(l.first, this.first) < 0) {
      return new Cons<T>(l.first, this.merge(l.rest, fun));
    }
    else {
      return new Cons<T>(this.first, l.merge(this.rest, fun));
    }
  }

  public IList<T> mergeWithMt(Empty<T> l, IComparator<T> fun) {
    return this;
  }

  // sameness using double dispatch
  public boolean sameList(IList<T> l) {
    return l.sameCons(this);
  }

  public boolean sameEmpty(Empty<T> l) {
    return false;
  }

  public boolean sameCons(Cons<T> l) {
    return this.first.equals(l.first) && this.rest.sameList(l.rest);
  }

  public ABST<T> buildTree(ABST<T> t) {
    t = t.insert(this.first);
    return this.rest.buildTree(t);
  }
}

abstract class ABST<T> {
  IComparator<T> order;

  public ABST(IComparator<T> ord) {
    this.order = ord;
  }

  public abstract boolean isLeaf();

  public abstract ABST<T> insert(Node<T> n);

  public abstract ABST<T> insert(T t);

  public abstract ABST<T> getLeftmostHelp();

  public abstract ABST<T> getRight();

  public abstract boolean sameTree(ABST<T> n);

  public abstract boolean sameData(ABST<T> n);

  public abstract int getHeight();

  public abstract int updateHeight();

  public abstract IList<T> buildList();

  public abstract IList<T> buildList(IList<T> t);

  public abstract boolean sameAsList(IList<T> that);

  public abstract T getLeftmost();

  public abstract ABST<T> getLeft();

  public abstract Node<T> getRightmostHelp();
}

class Leaf<T> extends ABST<T> {
  public Leaf(IComparator<T> order) {
    super(order);
  }

  public boolean isLeaf() {
    return true;
  }

  public ABST<T> getLeftmostHelp() {
    throw new RuntimeException("No leftmost item of an empty tree");
  }

  public ABST<T> getRight() {
    throw new RuntimeException("No right of an empty tree");
  }

  public boolean sameTree(ABST<T> n) {
    return n.isLeaf();
  }

  public int getHeight() {
    return 0;
  }

  public int updateHeight() {
    return 0;
  }

  public ABST<T> insert(Node<T> n) {
    return n;
  }

  public ABST<T> insert(T t) {
    return new Node<T>(t, new Leaf<T>(this.order), new Leaf<T>(this.order), this.order);
  }

  public boolean sameData(ABST<T> n) {
    return n.sameTree(this);
  }

  public IList<T> buildList() {
    return new Empty<T>();
  }

  public boolean sameAsList(IList<T> that) {
    return that.sameEmpty(new Empty<T>());
  }

  public IList<T> buildList(IList<T> t) {
    return t;
  }

  @Override
  public T getLeftmost() {
    throw new RuntimeException("No leftmost item of an empty tree");
  }

  @Override
  public ABST<T> getLeft() {
    throw new RuntimeException("No left of an empty tree");
  }

  @Override
  public Node<T> getRightmostHelp() {
    throw new RuntimeException("No Rightmost item of an empty tree");
  }
}

class Node<T> extends ABST<T> {
  T data;
  ABST<T> left;
  ABST<T> right;
  int height;

  Node(IComparator<T> order, T data, ABST<T> left, ABST<T> right) {
    super(order);
    this.data = data;
    this.left = left;
    this.right = right;
    updateHeight();
  }

  Node(T data, ABST<T> left, ABST<T> right, IComparator<T> order) {
    super(order);
    this.data = data;
    this.left = left;
    this.right = right;
    updateHeight();
  }

  public boolean isLeaf() {
    return false;
  }
  
  
  
  //takes in an item and produces a new ABST with the
 //given item inserted in the correct place
  public ABST<T> insert(Node<T> n) {
    int compare = this.order.compare(n.data, this.data);
    if (compare >= 0) {
        if (this.right.isLeaf()) {
            this.right = n;
        }
        else {
            this.right = this.right.insert(n);
        }
    }
    else {
        if (this.left.isLeaf()) {
            this.left = n;
        }
        else {
            this.left = this.left.insert(n);
        }
    }
    
    this.updateHeight();
    int heightDiff = this.right.getHeight() - this.left.getHeight();

    ABST<T> root = this;

    if (Math.abs(heightDiff) > 1) {
        
        if (heightDiff > 1) {
            
            Node<T> subtreeRight = (Node<T>)this.right;
            heightDiff = subtreeRight.right.getHeight() -
                    subtreeRight.left.getHeight();

            if (heightDiff < 0) {
               
                Node<T> rl = (Node<T>)subtreeRight.left;
                ABST<T> rlr = rl.right;
                subtreeRight.left = rlr;
                rl.right = subtreeRight;

                this.right = rl.left;
                rl.left = this;

                subtreeRight.updateHeight();
                this.updateHeight();
                rl.updateHeight();

                root = rl;
            }
            else if (heightDiff > 0) {
               

                this.right = subtreeRight.left;
                subtreeRight.left = this;

                this.updateHeight();
                subtreeRight.updateHeight();

                root = subtreeRight;
            }
        }
        else if (heightDiff < -1) {
            Node<T> subtreeLeft = (Node<T>)this.left;
            heightDiff = subtreeLeft.right.getHeight() -
                    subtreeLeft.left.getHeight();
            if (heightDiff > 0) {

                Node<T> lr = (Node<T>)subtreeLeft.right;
                ABST<T> lrl = lr.left;
                subtreeLeft.right = lrl;
                lr.left = subtreeLeft;

                this.left = lr.right;
                lr.right = this;

                this.updateHeight();
                subtreeLeft.updateHeight();
                lr.updateHeight();

                root = lr;
            }
            else if (heightDiff < 0) {
                this.left = subtreeLeft.right;
                subtreeLeft.right = this;

                this.updateHeight();
                subtreeLeft.updateHeight();

                root = subtreeLeft;
            }
        }
    }
    return root;
}

  public ABST<T> insert(T t) {
    return this
        .insert(new Node<T>(t, new Leaf<T>(this.order), new Leaf<T>(this.order), this.order));
  }

//returns the leftmost item in this tree
//passes it into getLeftmostHelp
  public T getLeftmost() {
    if (this.left.isLeaf()) {
      return this.data;
    }
    else {
      return this.left.getLeftmost();
    }
  }

  public ABST<T> getLeftmostHelp() {
    if (this.left.isLeaf()) {
      return this;
    }
    else {
      return this.left.getLeftmostHelp();
    }
  }
  //returns the ABST without the leftmost item in the tree
  public ABST<T> getRight() {
    if (this.left.isLeaf()) {
      if (this.right.isLeaf()) {
        return new Leaf<T>(this.order);
      }
      else {
        return this.right;
      }
    }
    else {
      return new Node<T>(this.data, this.left.getRight(), this.right, this.order);
    }
  }
  
  public Node<T> getRightmostHelp() {
    if (this.right.isLeaf()) {
      return this;
    }
    else {
      return this.right.getRightmostHelp();
    }
  }
  

  public boolean sameTree(ABST<T> n) {
    if (n.isLeaf()) {
      return false;
    }
    else if (order.compare(this.data, ((Node<T>) n).data) == 0) {
      return this.left.sameTree((((Node<T>) n).left)) && this.right.sameTree((((Node<T>) n).right));
    }
    else {
      return false;
    }
  }

  //to check if they have the same data regularless the order
  /*
    bstA:       bstB:       bstC:       bstD:
         b3          b3          b2          b3
        /  \        /  \        /  \        /  \
       b2  b4      b2  b4      b1   b4     b1   b4
      /           /                /             \
    b1           b1               b3              b5*/
  
  public boolean sameData(ABST<T> n) {
    return order.compare(((Node<T>) this.getLeftmostHelp()).data,
        ((Node<T>) n.getLeftmostHelp()).data) == 0 && this.getRight().sameData(n.getRight());
  }

  public int getHeight() {
    return this.height;
  }

  public int updateHeight() {
    this.height = Math.max(left.getHeight(), right.getHeight()) + 1;
    return height;
  }

  public IList<T> buildList() {
    return new Cons<T>(((Node<T>) this.getRightmostHelp()).data, this.getLeft().buildList());
  }

  public IList<T> buildList(IList<T> t) {
    return t.merge(this.buildList(), this.order);
  }

  public ABST<T> getLeft() {
    if (this.right.isLeaf()) {
      if (this.left.isLeaf()) {
        return new Leaf<T>(this.order);
      }
      else {
        return this.left;
      }
    }
    else {
      return new Node<T>(this.data, this.left, this.right.getLeft(), this.order);
    }
  }

  

  public boolean sameAsList(IList<T> that) {
    return this.buildList().sameList(that);
  }
}

class BooksByTitle implements IComparator<Book> {
  public int compare(Book b1, Book b2) {
    return b1.title.compareTo(b2.title);
  }
}

class BooksByAuthor implements IComparator<Book> {
  public int compare(Book b1, Book b2) {
    return b1.author.compareTo(b2.author);
  }
}

class BooksByPrice implements IComparator<Book> {
  public int compare(Book b1, Book b2) {
    return b1.price - b2.price;
  }
}

class ExamplesABST {
  Book book1 = new Book("The Book of Steve", "Steve", 1);
  Book book2 = new Book("John's Autobiography", "John", 2);
  Book book3 = new Book("Sky is blue", "Sky", 5);
  Book book4 = new Book("The page is not found", "Page", 7);
  Book book5 = new Book("Lee's new book", "Lee", 6);
  Book book6 = new Book("Show me the money", "Me", 4);
  Book book7 = new Book("Let's skydive", "Skydive", 3);
  Book book8 = new Book("Rush hour", "Rush", 8);
  Book book9 = new Book("Spring coming", "Spring", 6);
  Book book10 = new Book("Morning coffee", "Morning", 3);

  IComparator<Book> title = new BooksByTitle();
  IComparator<Book> author = new BooksByAuthor();
  IComparator<Book> price = new BooksByPrice();
  ABST<Book> tLeaf = new Leaf<Book>(title);
  ABST<Book> aLeaf = new Leaf<Book>(author);
  ABST<Book> pLeaf = new Leaf<Book>(price);

  boolean testIsLeaf(Tester t) {
    ABST<Book> node1 = new Node<Book>(book1, tLeaf, tLeaf, title);
    return t.checkExpect(tLeaf.isLeaf(), true) && t.checkExpect(aLeaf.isLeaf(), true)
        && t.checkExpect(pLeaf.isLeaf(), true) && t.checkExpect(node1.isLeaf(), false);
  }

  boolean testInsert(Tester t) {
    ABST<Book> titleTree = new Leaf<Book>(title);
    titleTree = titleTree.insert(book1);
    titleTree = titleTree.insert(book2);
    titleTree = titleTree.insert(book3);
    titleTree = titleTree.insert(book4);
    titleTree = titleTree.insert(book5);
    titleTree = titleTree.insert(book6);
    titleTree = titleTree.insert(book7);

    ABST<Book> titleT1 = new Node<Book>(book1, tLeaf, tLeaf, title);
    ABST<Book> titleT3 = new Node<Book>(book3, tLeaf, tLeaf, title);
    ABST<Book> titleT2 = new Node<Book>(book2, titleT1, titleT3, title);
    ABST<Book> titleT5 = new Node<Book>(book5, tLeaf, tLeaf, title);
    ABST<Book> titleT7 = new Node<Book>(book7, tLeaf, tLeaf, title);
    ABST<Book> titleT6 = new Node<Book>(book6, titleT5, titleT7, title);
    ABST<Book> titleT4 = new Node<Book>(book4, titleT2, titleT6, title);

    ABST<Book> titleCheck = titleT4;

    ABST<Book> authorTree = new Leaf<Book>(new BooksByAuthor());
    authorTree = authorTree.insert(book1);
    authorTree = authorTree.insert(book2);
    authorTree = authorTree.insert(book3);
    authorTree = authorTree.insert(book4);
    authorTree = authorTree.insert(book5);
    authorTree = authorTree.insert(book6);
    authorTree = authorTree.insert(book7);

    ABST<Book> authorT1 = new Node<Book>(book5, aLeaf, aLeaf, author);
    ABST<Book> authorT3 = new Node<Book>(book7, aLeaf, aLeaf, author);
    ABST<Book> authorT2 = new Node<Book>(book6, authorT3, authorT1, author);
    ABST<Book> authorT5 = new Node<Book>(book1, aLeaf, aLeaf, author);
    ABST<Book> authorT7 = new Node<Book>(book3, aLeaf, aLeaf, author);
    ABST<Book> authorT6 = new Node<Book>(book2, authorT7, authorT5, author);
    ABST<Book> authorT4 = new Node<Book>(book4, authorT2, authorT6, author);

    ABST<Book> authorCheck = authorT4;

    ABST<Book> priceTree = new Leaf<Book>(new BooksByPrice());
    priceTree = priceTree.insert(book1);
    priceTree = priceTree.insert(book2);
    priceTree = priceTree.insert(book3);
    priceTree = priceTree.insert(book4);
    priceTree = priceTree.insert(book5);
    priceTree = priceTree.insert(book6);
    priceTree = priceTree.insert(book7);

    ABST<Book> priceT3 = new Node<Book>(book7, pLeaf, pLeaf, price);
    ABST<Book> priceT4 = new Node<Book>(book6, priceT3, pLeaf, price);
    ABST<Book> priceT1 = new Node<Book>(book1, pLeaf, pLeaf, price);
    ABST<Book> priceT2 = new Node<Book>(book2, priceT1, priceT4, price);
    ABST<Book> priceT7 = new Node<Book>(book4, pLeaf, pLeaf, price);
    ABST<Book> priceT6 = new Node<Book>(book5, pLeaf, priceT7, price);
    ABST<Book> priceT5 = new Node<Book>(book3, priceT2, priceT6, price);

    ABST<Book> priceCheck = priceT5;
    return t.checkExpect(titleTree, titleCheck) && t.checkExpect(authorTree, authorCheck)
        && t.checkExpect(priceTree, priceCheck);
  }

  boolean testGetHeight(Tester t) {
    ABST<Book> titleT1 = new Node<Book>(book1, tLeaf, tLeaf, title);
    ABST<Book> titleT3 = new Node<Book>(book3, tLeaf, tLeaf, title);
    ABST<Book> titleT2 = new Node<Book>(book2, titleT1, titleT3, title);
    ABST<Book> titleT5 = new Node<Book>(book5, tLeaf, tLeaf, title);
    ABST<Book> titleT7 = new Node<Book>(book7, tLeaf, tLeaf, title);
    ABST<Book> titleT6 = new Node<Book>(book6, titleT5, titleT7, title);
    ABST<Book> titleT4 = new Node<Book>(book4, titleT2, titleT6, title);

    return t.checkExpect(titleT1.getHeight(), 1) && t.checkExpect(tLeaf.getHeight(), 0)
        && t.checkExpect(titleT2.getHeight(), 2) && t.checkExpect(titleT4.getHeight(), 3);
  }

  boolean testUpdateHeight(Tester t) {
    ABST<Book> titleT1 = new Node<Book>(book1, tLeaf, tLeaf, title);
    ABST<Book> titleT3 = new Node<Book>(book3, tLeaf, tLeaf, title);
    ABST<Book> titleT2 = new Node<Book>(book2, titleT1, titleT3, title);
    ABST<Book> titleT5 = new Node<Book>(book5, tLeaf, tLeaf, title);
    ABST<Book> titleT7 = new Node<Book>(book7, tLeaf, tLeaf, title);
    ABST<Book> titleT6 = new Node<Book>(book6, titleT5, titleT7, title);
    ABST<Book> titleT4 = new Node<Book>(book4, titleT2, titleT6, title);
    ((Node<Book>) titleT2).left = tLeaf;
    ((Node<Book>) titleT2).right = tLeaf;
    ((Node<Book>) titleT4).right = tLeaf;
    titleT2.updateHeight();
    titleT6.updateHeight();

    return t.checkExpect(titleT4.updateHeight(), 2);
  }

  boolean testGetLeftmost(Tester t) {
    ABST<Book> titleT1 = new Node<Book>(book1, tLeaf, tLeaf, title);
    ABST<Book> titleT3 = new Node<Book>(book3, tLeaf, tLeaf, title);
    ABST<Book> titleT2 = new Node<Book>(book2, titleT1, titleT3, title);
    ABST<Book> titleT5 = new Node<Book>(book5, tLeaf, tLeaf, title);
    ABST<Book> titleT7 = new Node<Book>(book7, tLeaf, tLeaf, title);
    ABST<Book> titleT6 = new Node<Book>(book6, titleT5, titleT7, title);
    ABST<Book> titleT4 = new Node<Book>(book4, titleT2, titleT6, title);

    return t.checkException(new RuntimeException("No leftmost item of an empty tree"), tLeaf,
        "getLeftmostHelp") && t.checkExpect(titleT4.getLeftmostHelp(), titleT1);
  }

  boolean testGetRight(Tester t) {
    ABST<Book> titleT1 = new Node<Book>(book1, tLeaf, tLeaf, title);
    ABST<Book> titleT3 = new Node<Book>(book3, tLeaf, tLeaf, title);
    ABST<Book> titleT2 = new Node<Book>(book2, titleT1, titleT3, title);
    ABST<Book> titleT5 = new Node<Book>(book5, tLeaf, tLeaf, title);
    ABST<Book> titleT7 = new Node<Book>(book7, tLeaf, tLeaf, title);
    ABST<Book> titleT6 = new Node<Book>(book6, titleT5, titleT7, title);
    ABST<Book> titleT4 = new Node<Book>(book4, titleT2, titleT6, title);

    ABST<Book> titleT3b = new Node<Book>(book3, tLeaf, tLeaf, title);
    ABST<Book> titleT2b = new Node<Book>(book2, tLeaf, titleT3b, title);
    ABST<Book> titleT5b = new Node<Book>(book5, tLeaf, tLeaf, title);
    ABST<Book> titleT7b = new Node<Book>(book7, tLeaf, tLeaf, title);
    ABST<Book> titleT6b = new Node<Book>(book6, titleT5b, titleT7b, title);
    ABST<Book> titleT4b = new Node<Book>(book4, titleT2b, titleT6b, title);

    return t.checkException(new RuntimeException("No right of an empty tree"), tLeaf, "getRight")
        && t.checkExpect(titleT4.getRight(), titleT4b);
  }

  boolean testSameTree(Tester t) {
    ABST<Book> titleT1 = new Node<Book>(book1, tLeaf, tLeaf, title);
    ABST<Book> titleT3 = new Node<Book>(book3, tLeaf, tLeaf, title);
    ABST<Book> titleT2 = new Node<Book>(book2, titleT1, titleT3, title);
    ABST<Book> titleT5 = new Node<Book>(book5, tLeaf, tLeaf, title);
    ABST<Book> titleT7 = new Node<Book>(book7, tLeaf, tLeaf, title);
    ABST<Book> titleT6 = new Node<Book>(book6, titleT5, titleT7, title);
    ABST<Book> titleT4 = new Node<Book>(book4, titleT2, titleT6, title);

    ABST<Book> titleT1b = new Node<Book>(book1, tLeaf, tLeaf, title);
    ABST<Book> titleT3b = new Node<Book>(book3, tLeaf, tLeaf, title);
    ABST<Book> titleT2b = new Node<Book>(book2, titleT1b, titleT3b, title);
    ABST<Book> titleT5b = new Node<Book>(book5, tLeaf, tLeaf, title);
    ABST<Book> titleT7b = new Node<Book>(book7, tLeaf, tLeaf, title);
    ABST<Book> titleT6b = new Node<Book>(book6, titleT5b, titleT7b, title);
    ABST<Book> titleT4b = new Node<Book>(book4, titleT2b, titleT6b, title);

    return t.checkExpect(titleT4.sameTree(titleT4b), true)
        && t.checkExpect(tLeaf.sameTree(tLeaf), true)
        && t.checkExpect(titleT4.sameTree(tLeaf), false)
        && t.checkExpect(tLeaf.sameTree(titleT4), false);
  }

  boolean testSameData(Tester t) {
    ABST<Book> BSTA = new Node<Book>(book3,
        new Node<Book>(book2, new Node<Book>(book1, tLeaf, tLeaf, title), tLeaf, title),
        new Node<Book>(book4, tLeaf, tLeaf, title), title);
    ABST<Book> BSTB = new Node<Book>(book3,
        new Node<Book>(book2, new Node<Book>(book1, tLeaf, tLeaf, title), tLeaf, title),
        new Node<Book>(book4, tLeaf, tLeaf, title), title);
    ABST<Book> BSTC = new Node<Book>(book2, new Node<Book>(book1, tLeaf, tLeaf, title),
        new Node<Book>(book4, new Node<Book>(book3, tLeaf, tLeaf, title), tLeaf, title), title);
    ABST<Book> BSTD = new Node<Book>(book3, new Node<Book>(book1, tLeaf, tLeaf, title),
        new Node<Book>(book4, tLeaf, new Node<Book>(book5, tLeaf, tLeaf, title), title), title);
    return t.checkExpect(BSTA.sameData(BSTB), true) && t.checkExpect(BSTA.sameData(BSTC), true)
        && t.checkExpect(BSTA.sameData(BSTD), false);
  }

  boolean testBuildList(Tester t) {
    ABST<Book> BSTA = new Node<Book>(book3,
        new Node<Book>(book2, new Node<Book>(book1, tLeaf, tLeaf, title), tLeaf, title),
        new Node<Book>(book4, tLeaf, tLeaf, title), title);
    ABST<Book> BSTB = new Node<Book>(book3,
        new Node<Book>(book2, new Node<Book>(book1, tLeaf, tLeaf, title), tLeaf, title),
        new Node<Book>(book4, tLeaf, tLeaf, title), title);
    ABST<Book> BSTC = new Node<Book>(book2, new Node<Book>(book1, tLeaf, tLeaf, title),
        new Node<Book>(book4, new Node<Book>(book3, tLeaf, tLeaf, title), tLeaf, title), title);
    ABST<Book> BSTD = new Node<Book>(book3, new Node<Book>(book1, tLeaf, tLeaf, title),
        new Node<Book>(book4, tLeaf, new Node<Book>(book5, tLeaf, tLeaf, title), title), title);

    IList<Book> ListA = new Cons<Book>(book1,
        new Cons<Book>(book2, new Cons<Book>(book3, new Cons<Book>(book4, new Empty<Book>()))));
    return t.checkExpect(BSTA.buildList().sameList(ListA), false)
        && t.checkExpect(BSTC.buildList().sameList(ListA), false)
        && t.checkExpect(BSTB.buildList().sameList(ListA), false)
        && t.checkExpect(BSTD.buildList().sameList(ListA), false);
  }

  boolean testSameAsList(Tester t) {
    ABST<Book> BSTA = new Node<Book>(book3,
        new Node<Book>(book2, new Node<Book>(book1, tLeaf, tLeaf, title), tLeaf, title),
        new Node<Book>(book4, tLeaf, tLeaf, title), title);
    ABST<Book> BSTB = new Node<Book>(book3,
        new Node<Book>(book2, new Node<Book>(book1, tLeaf, tLeaf, title), tLeaf, title),
        new Node<Book>(book4, tLeaf, tLeaf, title), title);
    ABST<Book> BSTC = new Node<Book>(book2, new Node<Book>(book1, tLeaf, tLeaf, title),
        new Node<Book>(book4, new Node<Book>(book3, tLeaf, tLeaf, title), tLeaf, title), title);
    ABST<Book> BSTD = new Node<Book>(book3, new Node<Book>(book1, tLeaf, tLeaf, title),
        new Node<Book>(book4, tLeaf, new Node<Book>(book5, tLeaf, tLeaf, title), title), title);

    IList<Book> ListA = new Cons<Book>(book1,
        new Cons<Book>(book2, new Cons<Book>(book3, new Cons<Book>(book4, new Empty<Book>()))));
    return t.checkExpect(BSTA.sameAsList(ListA), false)
        && t.checkExpect(BSTC.sameAsList(ListA), false)
        && t.checkExpect(BSTB.sameAsList(ListA), false)
        && t.checkExpect(BSTD.sameAsList(ListA), false);
  }

  IComparator<String> lex = new StrLexComp();
  IComparator<String> len = new StrLengthComp();

  boolean testLexCompare(Tester t) {
    return t.checkExpect(lex.compare("cs", "java") != 0, true)
        && t.checkExpect(lex.compare("java", "cs") != 0, true)
        && t.checkExpect(lex.compare("cs", "cs") == 0, true)
        && t.checkExpect(lex.compare("fundies", "fundiesfundies") != 0, true);
  }

  boolean testLengthCompare(Tester t) {
    return t.checkExpect(len.compare("cs", "myJava") != 0, true)
        && t.checkExpect(len.compare("myJava", "cs") != 0, true)
        && t.checkExpect(len.compare("cs", "cs") == 0, true)
        && t.checkExpect(len.compare("fundies", "fundiesfundies") != 0, true);
  }

  boolean testIsSorted(Tester t) {
    IList<String> l1 = new Empty<String>();
    IList<String> l2 = new Cons<String>("Mike", l1);
    IList<String> l3 = new Cons<String>("Mike", l2);
    IList<String> l4 = new Cons<String>("Steveny", l3);
    IList<String> l5 = new Cons<String>("Zhanig", l4);
    return t.checkExpect(l1.isSorted(lex), true) && t.checkExpect(l2.isSorted(lex), true)
        && t.checkExpect(l3.isSorted(lex), true) && t.checkExpect(l4.isSorted(lex), false)
        && t.checkExpect(l5.isSorted(lex), false);
  }

  boolean testSort(Tester t) {
    IList<String> l1 = new Empty<String>();
    IList<String> l2 = new Cons<String>("Mike", l1);
    IList<String> l3 = new Cons<String>("Mike", l2);
    IList<String> l4 = new Cons<String>("Steveny", l3);
    IList<String> l5 = new Cons<String>("Zhanig", l4);

    IList<String> l6 = new Empty<String>();
    IList<String> l7 = new Cons<String>("Steveny", l6);
    IList<String> l8 = new Cons<String>("Zhanig", l7);
    IList<String> l9 = new Cons<String>("Mike", l8);
    IList<String> l10 = new Cons<String>("Mike", l9);
    return t.checkExpect(l5.sort(len), l10)
        && t.checkExpect(new Empty<String>().sort(len), new Empty<String>());
  }

  boolean testSameEmpty(Tester t) {
    Empty<String> l1 = new Empty<String>();
    Empty<String> l2 = new Empty<String>();
    Cons<String> l3 = new Cons<String>("", l1);
    return t.checkExpect(l1.sameEmpty(l1), true) && t.checkExpect(l2.sameEmpty(l1), true)
        && t.checkExpect(l3.sameEmpty(l1), false);
  }

  boolean testSameCons(Tester t) {
    IList<String> l1 = new Empty<String>();
    Cons<String> l2 = new Cons<String>("Mike", l1);
    Cons<String> l3 = new Cons<String>("Mike", l2);
    Cons<String> l4 = new Cons<String>("Steveny", l3);
    Cons<String> l5 = new Cons<String>("Steveny", l3);

    return t.checkExpect(l2.sameCons(l2), true) && t.checkExpect(l3.sameCons(l2), false)
        && t.checkExpect(l4.sameCons(l2), false) && t.checkExpect(l5.sameCons(l4), true);
  }

  boolean testSameList(Tester t) {
    IList<String> l1 = new Empty<String>();
    IList<String> l2 = new Cons<String>("Mike", l1);
    IList<String> l3 = new Cons<String>("Mike", l2);
    IList<String> l4 = new Cons<String>("Steveny", l3);
    IList<String> l5 = new Cons<String>("Zhanig", l4);

    IList<String> l6 = new Empty<String>();
    IList<String> l7 = new Cons<String>("Mike", l1);
    IList<String> l8 = new Cons<String>("Mike", l2);
    IList<String> l9 = new Cons<String>("Steveny", l3);
    IList<String> l10 = new Cons<String>("Zhanig", l4);

    return t.checkExpect(l1.sameList(l6), true) && t.checkExpect(l1.sameList(l7), false)
        && t.checkExpect(l2.sameList(l7), true) && t.checkExpect(l3.sameList(l2), false)
        && t.checkExpect(l5.sameList(l10), true);
  }

  boolean testMerge(Tester t) {
    IList<String> l1 = new Empty<String>();
    IList<String> l2 = new Cons<String>("Mike", l1);
    IList<String> l3 = new Cons<String>("Mike", l2);
    IList<String> l4 = new Cons<String>("Steveny", l3);
    IList<String> l5 = new Cons<String>("Zhanig", l4);

    IList<String> l6 = new Empty<String>();
    IList<String> l7 = new Cons<String>("Mike", l1);
    IList<String> l8 = new Cons<String>("Mike", l2);
    IList<String> l9 = new Cons<String>("Zhanig", l3);
    IList<String> l10 = new Cons<String>("Steveny", l4);

    IList<String> l11 = new Cons<String>("Mike", new Cons<String>("Zhanig",
        new Cons<String>("Mike", new Cons<String>("Mike", new Empty<String>()))));

    IList<String> l12 = new Cons<String>("Zhanig", new Empty<String>());

    IList<String> l13 = new Cons<String>("Mike", new Cons<String>("Mike",
        new Cons<String>("Mike", new Cons<String>("Zhanig", new Empty<String>()))));
    return t.checkExpect(l9.merge(l2, lex), l11) && t.checkExpect(l9.merge(l1, lex), l9)
        && t.checkExpect(l8.merge(l2, len).merge(l12, len), l13)
        && t.checkExpect(new Empty<String>().merge(l11, len), l11)
        && t.checkExpect(new Empty<String>().merge(new Empty<String>(), len), new Empty<String>());
  }

  boolean testBuildListfromTree(Tester t) {
    ABST<Book> BSTA = new Node<Book>(book3,
        new Node<Book>(book2, new Node<Book>(book1, tLeaf, tLeaf, title), tLeaf, title),
        new Node<Book>(book4, tLeaf, tLeaf, title), title);
    IList<Book> list1 = new Cons<Book>(book1,
        new Cons<Book>(book2, new Cons<Book>(book3, new Cons<Book>(book4, new Empty<Book>()))));
    return t.checkExpect(new Leaf<Book>(title).buildList(new Empty<Book>()), new Empty<Book>())
        && t.checkExpect(new Leaf<Book>(title).buildList(list1), list1);
  }

  boolean testBuildTreefromList(Tester t) {
    ABST<Book> BSTA = new Node<Book>(book3,
        new Node<Book>(book2, new Node<Book>(book1, tLeaf, tLeaf, title), tLeaf, title),
        new Node<Book>(book4, tLeaf, tLeaf, title), title);
    IList<Book> list1 = new Cons<Book>(book1,
        new Cons<Book>(book2, new Cons<Book>(book3, new Cons<Book>(book4, new Empty<Book>()))));
    ABST<Book> BSTB = new Node<Book>(book5, tLeaf, tLeaf, title);

    return t.checkExpect(new Empty<Book>().buildTree(new Leaf<Book>(title)), new Leaf<Book>(title))
        && t.checkExpect(new Empty<Book>().buildTree(BSTA), BSTA)
        && t.checkExpect(list1.buildTree(BSTB).sameData(BSTA.insert(book5)), false);
  }
}
