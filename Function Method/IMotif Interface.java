interface IMotif {

  // compute the total number of Motif
  double countMotif();

  // compute the total difficult of the Motif
  double totalMotifDifficulty();

  // computes the average difficult of the motif
  double averageDifficulty();

  // returns a string that has the name of the motif and its type
  String getInfo();
}

class CrossStitchMotif implements IMotif {
  String description;
  double difficulty;

  CrossStitchMotif(String description, double difficulty) {
    this.description = description;
    this.difficulty = difficulty;
  }
  /*
   * fields:
   * this.descritption ... String
   * this.difficulty ... double
   * methods:
   * this.averageDifficulty() ... double
   * this.totalMotifDifficulty() ... double
   * this.countMotif() ... double
   * methods for fields:
   */

  // compute the total number of Motif
  public double countMotif() {
    return 1;
  }

  // computer the total difficult of the Motif
  public double totalMotifDifficulty() {
    return this.difficulty;
  }

  // compute the average difficult of a motif
  public double averageDifficulty() {
    return this.totalMotifDifficulty() / this.countMotif();
  }

  // returns a string that has the name of the motif and its type
  public String getInfo() {
    return this.description + "(cross stitch)";
  }

}

class ChainStitchMotif implements IMotif {
  String description;
  double difficulty;

  ChainStitchMotif(String description, double difficulty) {
    this.description = description;
    this.difficulty = difficulty;

  }
  
  /*
   * fields:
   * this.descritption ... String
   * this.difficulty ... double
   * methods:
   * this.averageDifficulty() ... double
   * this.totalMotifDifficulty() ... double
   * this.countMotif() ... double
   * methods for fields:
   */

  // compute the total number of Motif
  public double countMotif() {
    return 1;
  }

  // computer the total difficult of the Motif
  public double totalMotifDifficulty() {
    return this.difficulty;
  }

  // compute the average difficult of a motif
//compute the average difficult of a motif
 public double averageDifficulty() {
   return this.totalMotifDifficulty() / this.countMotif();
  }

  // returns a string that has the name of the motif and its type
  public String getInfo() {
    return this.description + "(chain stitch)";
  }

}

class GroupMotif implements IMotif {
  String description;
  ILoMotif motifs;

  GroupMotif(String description, ILoMotif motifs) {
    this.description = description;
    this.motifs = motifs;
  }

  
  /*
   * fields:
   * this.descritption ... String
   * this.motifs ... ILoMotif
   * methods:
   * this.averageDifficulty() ... double
   * this.totalMotifDifficulty() ... double
   * this.countMotif() ... double
   * methods for fields:
   */
  
  // compute the total number of Motif
  public double countMotif() {
    return this.motifs.totalMotifCount();
  }

  // compute the total difficult in the group Motif class
  public double totalMotifDifficulty() {
    return this.motifs.totalDifficulty();
  }
  
  // computer the total average difficult of the Motif in group class
  //There should be an if check here first (to make sure 
    //this.countMotif() is not equal to 0. If it is, we will be dividing by zero
    public double averageDifficulty() { 
      if (this.countMotif() == 0) {
        return 0.0;
        }
        else {
        return this.totalMotifDifficulty() / this.countMotif();
        }
  }

  // returns a string that has the name of the motif and its type
  public String getInfo() {
    return this.motifs.getLoMotifInfo();
  }

}

interface ILoMotif {
  
  
  // return average difficult of the motifs in the list
  double averageDifficulty();

  // compute the total number of Motif
  double totalMotifCount();

  // computer the total difficult of the Motif
  double totalDifficulty();

  // returns a string of the motif in the list including its name and type
  String getLoMotifInfo();
  

    

}

class MtLoMotif implements ILoMotif {
  
  /*
   * fields:
   * methods:
   * this.averageDifficulty() ... double
   * this.totalMotifDifficulty() ... double
   * this.totalMotifCount() ... double
   * this.getLoMotifInfo() ... String
   * methods for fields:
   */
   
    
  // return average difficult of the motifs in the list
  public double averageDifficulty() {
    return 0;
  }

  // total number of Motif in the empty list
  public double totalMotifCount() {
    return 0;
  }

  // total number of difficult in the empty
  public double totalDifficulty() {
    return 0;
  }

  // returns nothing to the string because it is empty
  public String getLoMotifInfo() {
    return "";  
  }

  
 
}

class ConsLoMotif implements ILoMotif {
  IMotif first;
  ILoMotif rest;

  ConsLoMotif(IMotif first, ILoMotif rest) {
    this.first = first;
    this.rest = rest;
  }
  /*
   * fields:
   * this.first ... IMotif
   * this.rest ... ILoMotif
   * methods:
   * this.averageDifficulty() ... double
   * this.totalMotifDifficulty() ... double
   * this.totalMotifCount() ... double
   * this.getLoMotifInfo() ... String
   * methods for fields:
   * this.first.averageDifficulty() ... double
   * this.first.totalMotifDifficulty() ... double
   * this.first.totalMotifCount() ... double
   * this.first.getLoMotifInfo() ... String
   * this.rest.averageDifficulty() ... double
   * this.rest.totalMotifDifficulty() ... double
   * this.rest.totalMotifCount() ... double
   * this.rest.getLoMotifInfo() ... String
   */
  
  
  
  
  

  // count the number of motif in the list
  public double totalMotifCount() {
    //return 1 + rest.totalMotifCount(); because we cannot be certain that we will always be adding one. 
    //The this.first field could be a GroupMotif and could have multiple or 
    //no motifs + the number of motifs inside this.rest.
    return this.first.countMotif() + this.rest.totalMotifCount();

  }

  // return the totalDifficulty of the motifs
  public double totalDifficulty() {
    return this.first.totalMotifDifficulty() + this.rest.totalDifficulty();
  }

  // return average difficult of the motifs in the list
  public double averageDifficulty() {
    return this.totalDifficulty() / this.totalMotifCount();
  }

  // produces the string with the info about the embroidery piece
  public String getLoMotifInfo() {
    return this.first.getInfo() + "," + this.rest.getLoMotifInfo();
  }



class ExamplesEmbroidery {
  ExamplesEmbroidery() {
  }

  IMotif bird = new CrossStitchMotif("bird", 4.5);
  IMotif tree = new ChainStitchMotif("tree", 3.0);
  IMotif rose = new CrossStitchMotif("rose", 5.0);
  IMotif puppy = new ChainStitchMotif("puppy", 4.75);
  IMotif daisy = new CrossStitchMotif("daisy", 3.2);

  ILoMotif empty = new MtLoMotif();
  ILoMotif m1 = new ConsLoMotif(this.daisy, empty);
  ILoMotif m2 = new ConsLoMotif(this.puppy, this.m1);
  ILoMotif m3 = new ConsLoMotif(this.rose, this.m2);
  
  
 

  IMotif flowers = new GroupMotif("flowers", this.m3);
  ILoMotif m4 = new ConsLoMotif(this.flowers, empty);
  ILoMotif m5 = new ConsLoMotif(this.tree, this.m4);
  ILoMotif m6 = new ConsLoMotif(this.bird, this.m5);
  
  IMotif nature = new GroupMotif("nature", this.m6);
  

  EmbroideryPiece pillowCover = new EmbroideryPiece("pillowCover", this.nature);
  
