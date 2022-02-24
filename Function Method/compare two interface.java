public IAT youngestParent() {
     return this.mom.youngerIAT(dad);       //当比较两个interface，需要两个helper function
     }                                     //一个helper是比较interface，other.helper.this
              //一个helper是具体比较interface的this.sth,但arugment要有一个interface和具体比较的int
             //因为最好要return other的interface
   
   public IAT youngerIAT(IAT other) {         //这里我们不能用other.yob，所以要用一个helper来叫other的yob
     return other.youngerIATHelper(this, this.yob); //这里的this代替helper function里的IAT other
     }                                              //再放入other进行比较
   
   //helper
   public IAT youngerIATHelper(IAT other, int otherYob) {  //这里要take两个argument的原因是  
     if (this.yob > otherYob) {    //因为helper function 要么return this，要么return other，
       return this;   //需要有一个int otheryob进行比较，！过后叫这个helper的时候，这个otherYob就会换成this.yob 
     }        //有一个other来放在else后面
     else return other;         
   }
