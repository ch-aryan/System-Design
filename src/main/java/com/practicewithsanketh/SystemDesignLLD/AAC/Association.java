package com.practicewithsanketh.SystemDesignLLD.AAC;
class A{
    private final int a;
   private final int b;
  private final  int c;
    A(){
        a=8; b = 10; c = 12;
    }
    void meth(){
        System.out.println(a+b+c);
    }
}
class B{
    private final int d;
    private A a;//what if i write the final here a = new A() i need to do right.
    B(){
        d = 10;

    }
}
public class Association {

}
