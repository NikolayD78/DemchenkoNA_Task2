import java.util.*;

public class MyProg {
    public static void main(String[] args) {

        double result;

        System.out.println("Hello World!");
        Fraction fr=new Fraction(2,3);
        Fractionable num= new FractionDecorator(fr);

        Object pr;
        pr = ((FractionDecorator)num).getProxy();
        result=((Fractionable)pr).doubleValue();
        System.out.println("MyProg:"+result);
        result=((Fractionable)pr).doubleValue();
        System.out.println("MyProg:"+result);
        ((Fractionable)pr).setNum(20);
        result=((Fractionable)pr).doubleValue();
        System.out.println("MyProg:"+result);
        result=((Fractionable)pr).doubleValue();
        System.out.println("MyProg:"+result);
        ((Fractionable)pr).setDenum(15);
        result=((Fractionable)pr).doubleValue();
        System.out.println("MyProg:"+result);
        result=((Fractionable)pr).doubleValue();
        System.out.println("MyProg:"+result);

    }

}
