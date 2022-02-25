interface ILostring{
  //give an index, and return the item at that index, if the index
  //is out of bound, we return an empty string
  String elementat(int index);
  }

class MtLostring implements ILoString{
  
  public String elementat(int index) {
   return ""; 
  }
}

class ConsLoSring implements ILoString{
  
  
   public String elementat(int index) {
   if (index ==0){
     return this;
   }
     else {
       return this.rest.elementAt(index -1);
  }
  
  
}


class ExamplesILostring {
  
  
  
  
