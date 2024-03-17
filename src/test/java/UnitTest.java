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

    static void checkResultOutput() // повторяемые фрагменты вынесены в отдельную ф-ю
    {
        res = baos.toString();
        System.setOut(oldPrintStream);// для визуального контроля - выведем еще и на "реальную" консоль
        System.out.println(res);
        baos.reset();
        System.setOut(newPrintStream);
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
        checkResultOutput();
        if (res.indexOf("Реальное вычисление, кэш сброшен",0)==-1)
            throw new AssertionError("Нет строки: Реальное вычисление, кэш сброшен");


        result=((Fractionable)pr).doubleValue();
        checkResultOutput();
        if (res.indexOf("Значение из кэша",0)==-1)
            throw new AssertionError("Нет строки: Значение из кэша");

        ((Fractionable)pr).setNum(20);
        result=((Fractionable)pr).doubleValue();
        checkResultOutput();
        if (res.indexOf("Установлен флаг Mutator, по факту вызова функции",0)==-1||res.indexOf("Реальное вычисление, кэш сброшен",0)==-1)
            throw new AssertionError("Нет строки: Установлен флаг Mutator, по факту вызова функции или Реальное вычисление, кэш сброшен");

        result=((Fractionable)pr).doubleValue();
        checkResultOutput();
        if (res.indexOf("Значение из кэша",0)==-1)
            throw new AssertionError("Нет строки: Значение из кэша");

        ((Fractionable)pr).setDenum(15);
        checkResultOutput();
        if (res.indexOf("Установлен флаг Mutator, по факту вызова функции",0)==-1)
            throw new AssertionError("Нет строки: Установлен флаг Mutator, по факту вызова функции");

        result=((Fractionable)pr).doubleValue();
        checkResultOutput();
        if (res.indexOf("Реальное вычисление, кэш сброшен",0)==-1)
            throw new AssertionError("Нет строки: Реальное вычисление, кэш сброшен");


    }
}
