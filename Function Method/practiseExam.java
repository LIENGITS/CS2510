import tester.Tester;
//represents a list of strings
interface ILoString {
  
  //give an index, and return the item at that index, if the index
  //is out of bound, we return an empty string
  String elementAt(int index);
  
  
  ILoString multipleOf(int multiple);
  
  
  ILoString multipleHelper (int currentIndex, int multiple);
}



//represents an empty list of strings
class MtLoString implements ILoString {
  
  public String elementAt(int index) {
    
    return "";
  }
  
  public ILoString multipleOf(int multiple) {
    return this;
  }
 public ILoString multipleHelper (int currentIndex, int multiple) {
 return this;
 }
}
  

//represents a non-empty list of strings
class ConsLoString implements ILoString {
String first;
ILoString rest;
ConsLoString(String first, ILoString rest) {
this.first = first;
this.rest = rest;
}




public String elementAt(int index) {
  if (index == 0) {
    return this.first;  
    }
  else {
    return this.rest.elementAt(index -1);
  }
}



public ILoString multipleOf(int multiple) {
  return this.multipleHelper(0, multiple);
}

public ILoString multipleHelper (int currentIndex, int multiple) {
  if (currentIndex % multiple ==0) {
    return new ConsLoString(this.first, this.rest.multipleHelper(currentIndex +1, multiple));
  }
  else {
    return this.rest.multipleHelper(currentIndex +1, multiple);
  }
}

}




//represents a pair of lists of strings
class PairOfLists {
ILoString first;
ILoString second;
PairOfLists(ILoString first, ILoString second) {
this.first = first;
this.second = second;
}


  
  
}


// Produces a new pair of lists, with the given String added to 
// the front of the first list of this pair
PairOfLists addToFirst(String first) {
//TODO: complete this method
  return new PairOfLists(new ConsLoString(first, this.first), this.second);
}
// Produces a new pair of lists, with the given String added to
// the front of the second list of this pair
PairOfLists addToSecond(String first) {
  return new PairOfLists(this.first, new ConsLoString(first, this.second));
  
  }
} 







class ExamplesLists {
ILoString mtStrings = new MtLoString();
ILoString list1 = new ConsLoString("A", new ConsLoString("B", 
this.mtStrings));
ILoString list2 = new ConsLoString("D", new ConsLoString("E", 
this.mtStrings));
ILoString list3 = new ConsLoString("A", new ConsLoString("D", new 
ConsLoString("B", 
                           new ConsLoString("E", 
this.mtStrings))));

PairOfLists pair0 = new PairOfLists(this.mtStrings, this.mtStrings);
PairOfLists pair1 = new PairOfLists(this.list1, this.list2);
PairOfLists pair2 = new PairOfLists(this.list2, this.list3);


boolean testElementAt(Tester t) {
  return
      t.checkExpect(list1.elementAt(0), "A") &&
      t.checkExpect(list1.elementAt(2), "") &&
      t.checkExpect(list1.elementAt(3), "") &&
      t.checkExpect(list1.elementAt(-1), "");
} 


boolean checkAddToFirst(Tester t) {
  return 
      t.checkExpect(pair1.addToFirst("ooo"), new PairOfLists(new ConsLoString("000", new ConsLoString("A", 
          new ConsLoString("B", this.mtStrings))), list2));
}

  
  boolean checkAddToSecond(Tester t) {
    return 
        t.checkExpect(pair1.addToSecond("ooo"), new PairOfLists(list1, new ConsLoString("000", new ConsLoString("D", 
            new ConsLoString("E", this.mtStrings)))));
 
  }
  
           
        
        
}
    
    
    



