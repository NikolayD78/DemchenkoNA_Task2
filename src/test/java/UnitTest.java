import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class UnitTest {

    static ByteArrayOutputStream baos;
    static PrintStream ps;
    static PrintStream oldPrintStream;
    static PrintStream newPrintStream;
    static String res;

    @BeforeAll
    static void preparing() {


        baos = new ByteArrayOutputStream();
        ps = new PrintStream(baos);
        oldPrintStream=System.out;
        newPrintStream=ps;
        System.setOut(ps);
    }

    static void checkResultOutput(String outputStr) // повторяемые фрагменты вынесены в отдельную ф-ю
    {
        res = baos.toString();
        System.setOut(oldPrintStream);// для визуального контроля - выведем еще и на "реальную" консоль
        System.out.println(res);
        baos.reset();
        System.setOut(newPrintStream);
        if (res.indexOf(outputStr,0)==-1)
            throw new AssertionError("Нет строки: "+outputStr);
    }

    @org.junit.jupiter.api.Test
    public void testCache()
    {
        double result;

        System.out.println("Hello World!");
        Fraction fr=new Fraction(2,3);
        Fractionable num= new FractionDecorator(fr);

        Object pr;

        pr = ((FractionDecorator)num).getProxy();
        result=((Fractionable)pr).doubleValue();
        checkResultOutput("Реальное вычисление, кэш сброшен");
        Assertions.assertEquals(String.valueOf(result),"0.6666666666666666");

        result=((Fractionable)pr).doubleValue();
        checkResultOutput("Значение из кэша");
        Assertions.assertEquals(String.valueOf(result),"0.6666666666666666");


        ((Fractionable)pr).setNum(20);
        checkResultOutput("Установлен флаг Mutator, по факту вызова функции");
        result=((Fractionable)pr).doubleValue();
        checkResultOutput("Реальное вычисление, кэш сброшен");
        Assertions.assertEquals(String.valueOf(result),"6.666666666666667");

        result=((Fractionable)pr).doubleValue();
        checkResultOutput("Значение из кэша");
        Assertions.assertEquals(String.valueOf(result),"6.666666666666667");

        ((Fractionable)pr).setDenum(15);
        checkResultOutput("Установлен флаг Mutator, по факту вызова функции");

        result=((Fractionable)pr).doubleValue();
        checkResultOutput("Реальное вычисление, кэш сброшен");
        Assertions.assertEquals(String.valueOf(result),"1.3333333333333333");
    }
}
