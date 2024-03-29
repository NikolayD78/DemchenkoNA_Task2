//Декоратор нужен для того, чтобы ввести переменные для кэшированных значений
import java.lang.reflect.*;
import java.util.Arrays;

public class FractionDecorator implements Fractionable{

    private Fractionable baseFraction;


    private FractionDecorator(){}

    public FractionDecorator(Fractionable baseFraction) {
        this.baseFraction = baseFraction;
    }

    public  Object getProxy()
    {
        Class cls = this.getClass();
        return  Proxy.newProxyInstance(cls.getClassLoader(),
                new Class[]{Fractionable.class},
                new FractionableInvHandler(this));
    }

    @Mutator
    public void setNum(int num)
    {
        baseFraction.setNum(num);
    }

    @Mutator
    public void setDenum(int denum)
    {
        baseFraction.setDenum(denum);
    }

    @Override
    @Cache
    public double doubleValue() {

            System.out.println("Реальное вычисление, значения в кэше нет");
            return baseFraction.doubleValue();

    }

    @Override
    @Cache
    public int intValue(){

        System.out.println("Возвращаем значение типа int не из кэша!");

        return baseFraction.intValue();
    }
}
