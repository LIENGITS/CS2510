//andrew.countDad
    public int countDad()                                                      //当andrew去invoke这个方法的时候，首先叫进入countDadHelper叫this.dad（Bill）， 
    {                                                      
      return this.dad.countDadHelper() + this.mom.countDadHelper();
    }
    
    public int countDadHelper() {
      if (this.isMale == true) {                                         //如果他是male，那么自动加1.
        return 1 + this.dad.countDadHelper() + this.mom.countDadHelper();// 然后加上this.dad，这时的dad是Bill的dad，再次进入helper检查，是就加1，不是就检查女性的dad和mom，直到空为止。
      }               //而这时加上的this.mom， 是Bill的mom。再进入helper检查， 是就加1，不是就检查女性的dad和mom，直到空为。
      else {
       return this.dad.countDadHelper() + this.mom.countDadHelper();   //“所有这些只是this.dad.counterDadhelper，检查andrew的dad（Bill)“。
      }                                                                 //所以要加上 this.mom.counterDadhelper
    }                                          
                                                                 Andrew(M, 2001)
                                                                         +
                                +----------------------------------------+--------------------------------------+                                                 
                                +                                                                         +
                          Bill(M, 1980)                                                             Bree(F, 1981)
                                +                                                                         +
                     +---------+------------------+                                                +---------+------------------+
                     +                            +                                                +                            + 
              Clyde(M, 1995)                Candace（F，1960）.                             Cameron(M, 1959)                Claire（F，1956）
                   +                                +                                              +                              +
           +-------+--------+               +-------+--------+                             +-------+--------+             +-------+--------+    
                   +                                +                                              +                              +
   David(M, 1925)    Daisy(F, 19270).  Darren(M, 1935).    Danna(F, 1993)            Dixon(m, 1936)        ?                ?        Darcy(F, 1930)    
           +.               +               +                  +                       +                  +                               +
   +-------+------+     +----+----+    +----+-----+        +----+-----+            +----+-----+                                  +--------+-------+           
           +
Edward(M, 1982)   ?    ?          ?   ?    Enid(F, 1904)   ?          ?            ?           ?                        Eustace(m, 19070)   Emma(F, 1906) 
+-------+------+                                                                                                     +-------+------+    +-------+------+                                                         
?              ?                                                                                                     ?              ?      ?              ?      
   

                                                         
                                                       
