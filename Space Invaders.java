import tester.*;     
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import javalib.worldimages.*;   
import javalib.funworld.*;      
import java.awt.Color;  



//interface for cartesianpoint
interface IPredicate<T> {
  boolean test(CartPt p);
}

//represents the cartesian points
class CartPt implements IPredicate<CartPt> {
  IList<CartPt> points;
    
  CartPt(IList<CartPt> points) {
    this.points = points;
  }
  
  //determines if the CartPt is contained within points
  public boolean test(CartPt p) {
    return points.contains(p);//unfinished
  }
    
}


interface IList<T> {
  //filter this IList using the given predicate
  IList<T> filter(Predicate<T> pred);
  
  //map the given function onto every member of this IList
  <R> IList<R> map(Function<T,R> converter);
  
  //combine the items in this IList using the given function
  <R> R fold(BiFunction<T,R,R> converter,R initial);  
}

class MtList<T> implements IList<T> {
    
  MtList() {}

  //filter this MtList using the given predicate
  public IList<T> filter(Predicate<T> pred) {
    return new MtList<T>();
  }

  //map the given function onto every member of this MtList
  public <R> IList<R> map(Function<T, R> converter) {
    return new MtList<R>();
  }

  //combine the items in this MtList using the given function
  public <R> R fold(BiFunction<T, R, R> converter, R initial) {
    return initial;
  }
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;
    
  ConsList(T first,IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  //filter this ConsList using the given predicate
  public IList<T> filter(Predicate<T> pred) {
    if (pred.test(this.first)) {
      return new ConsList<T>(this.first,this.rest.filter(pred));
    }
    else {
      return this.rest.filter(pred);
    }
  }

  //map the given function onto every member of this ConsList
  public <R> IList<R> map(Function<T, R> converter) {
    return new ConsList<R>(converter.apply(this.first),this.rest.map(converter));
  }

  //combine the items in this ConsList using the given function
  public <R> R fold(BiFunction<T, R, R> converter, R initial) {
    return converter.apply(this.first, this.rest.fold(converter,initial));
  }
}
  
 
//Represents the body of the spaceship and invaders aswell as their bullets
interface IObjects {
    
}

abstract class AObjects implements IObjects {
  int height;
  int width;
  Color c;
  int x;
  int y;
  
  //spaceship constructor
  AObjects(int height, int width, Color c, int x, int y) {
    this.height = height;
    this.width = width;
    this.c = c;
    this.x = x;
    this.y = y;
  }
  
  //invader constructor
  AObjects(Color c, int x, int y) {
    this.c = c;
    this.x = x;
    this.y = y;
  }
}

//represents the spaceship
class SpaceShip extends AObjects {
  SpaceShip(int height, int width, Color c, int x, int y) {
    super(height, width, c, x, y);
  }
  
  // draws a spaceship
  WorldScene draw(WorldScene ws) {
    return ws.placeImageXY(new RectangleImage(this.height, this.width,"solid", this.c), 
                                              this.x, this.y);
  }
}

//represents the invaders
class Invaders extends AObjects { 
  Invaders(int size, Color c, int x, int y) {
    super(size, size, c, x, y);
  }
  
  // draws a spaceship
  WorldScene draw(WorldScene ws) {
    return ws.placeImageXY(new RectangleImage(this.height, this.height,"solid", this.c), 
                                              this.x, this.y);
  }
}

//worldscene

//represents bullets from the spaceship
class SpaceshipBullets extends AObjects {
  int radius = 10;
  Color c = Color.black;

  SpaceshipBullets(int radius, Color c, int x, int y) {
    super(c, x, y);
    this.radius = radius;
  }
  
  // draws a single bullet
  WorldScene draw(WorldScene ws) {
    return ws.placeImageXY(new CircleImage(this.radius, "solid", this.c), this.x, this.y);
  }
    
  public SpaceshipBullets moveOnKey(String key) {
    if (key.equals("Space")) {
      return new SpaceshipBullets(this.radius, this.c, this.x, this.y + 5);
    }
    else {
      return this;
    }
  }
}

//represents bullets from the invaders 
class InvadersBullets extends AObjects {
  int radius = 10;
  Color c = Color.red;
  
  InvadersBullets(int radius, Color c, int x, int y) {
    super(c, x, y);
    this.radius = radius;
  }
  
  //draws a single bullet
  WorldScene draw(WorldScene ws) {
    return ws.placeImageXY(new CircleImage(this.radius, "solid", this.c), this.x, this.y);
  }
}



//represents the game world
class SaceInvaders extends World {
  //width and height of the world
  int width = 600;
  int height = 600;
    
  //the invaders
  IList<Invaders> invaderList = 
            new ConsList<Invaders>(new Invaders(20, Color.red, 40, 500), 
            new ConsList<Invaders>(new Invaders(20, Color.red, 60, 500),
            new ConsList<Invaders>(new Invaders(20, Color.red, 80, 500),
            new ConsList<Invaders>(new Invaders(20, Color.red, 100, 500),
            new ConsList<Invaders>(new Invaders(20, Color.red, 120, 500),
            new ConsList<Invaders>(new Invaders(20, Color.red, 140, 500),
            new ConsList<Invaders>(new Invaders(20, Color.red, 160, 500),
            new ConsList<Invaders>(new Invaders(20, Color.red, 180, 500),
            new ConsList<Invaders>(new Invaders(20, Color.red, 200, 500),
                new ConsList<Invaders>(new Invaders(20, Color.red, 40, 480), 
                new ConsList<Invaders>(new Invaders(20, Color.red, 60, 480),
                new ConsList<Invaders>(new Invaders(20, Color.red, 80, 480),
                new ConsList<Invaders>(new Invaders(20, Color.red, 100, 480),
                new ConsList<Invaders>(new Invaders(20, Color.red, 120, 480),
                new ConsList<Invaders>(new Invaders(20, Color.red, 140, 480),
                new ConsList<Invaders>(new Invaders(20, Color.red, 160, 480),
                new ConsList<Invaders>(new Invaders(20, Color.red, 180, 480),
                new ConsList<Invaders>(new Invaders(20, Color.red, 200, 480),
                     new ConsList<Invaders>(new Invaders(20, Color.red, 40, 460),
                     new ConsList<Invaders>(new Invaders(20, Color.red, 60, 460),
                     new ConsList<Invaders>(new Invaders(20, Color.red, 80, 460),
                     new ConsList<Invaders>(new Invaders(20, Color.red, 100, 460),
                     new ConsList<Invaders>(new Invaders(20, Color.red, 120, 460),
                     new ConsList<Invaders>(new Invaders(20, Color.red, 140, 460),
                     new ConsList<Invaders>(new Invaders(20, Color.red, 160, 460),
                     new ConsList<Invaders>(new Invaders(20, Color.red, 180, 460),
                     new ConsList<Invaders>(new Invaders(20, Color.red, 200, 460),
                         new ConsList<Invaders>(new Invaders(20, Color.red, 40, 440), 
                         new ConsList<Invaders>(new Invaders(20, Color.red, 60, 440),
                         new ConsList<Invaders>(new Invaders(20, Color.red, 80, 440),
                         new ConsList<Invaders>(new Invaders(20, Color.red, 100, 440),
                         new ConsList<Invaders>(new Invaders(20, Color.red, 120, 440),
                         new ConsList<Invaders>(new Invaders(20, Color.red, 140, 440),
                         new ConsList<Invaders>(new Invaders(20, Color.red, 160, 440),
                         new ConsList<Invaders>(new Invaders(20, Color.red, 180, 440),
                         new ConsList<Invaders>(new Invaders(20, Color.red, 200, 440), 
                         new MtList<Invaders>()))))))))))))))))))))))))))))))))))));
    
  //the space ship
  SpaceShip ship = new SpaceShip(20, 40, Color.black, 300, 10);
    
  //used after first instantiation
  SaceInvaders(SpaceShip ship,IList<Invaders> invaderList) {
    this.ship = ship;
    this.invaderList = invaderList;
  }
    
  //makes the scene for our game
  public WorldScene makeScene() {
    return  invaderList.makeScene(this.getEmptyScene().placeImageXY(this.ship, 300, 100));
  }  
}

class ExamplesSpaceInvader {
    
    
  // Renders the game
  boolean testBigBang(Tester t) {
    SaceInvaders si = new SaceInvaders();
    int Width = 600;
    int Height = 600;
    return si.bigBang(Width, Height);
  }
}


