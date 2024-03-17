//Декоратор нужен для того, чтобы ввести переменные для кэшированных значений
import java.lang.reflect.*;
import java.util.Arrays;

public class FractionDecorator implements Fractionable{

    private Fractionable baseFraction;
    private int flagMutator=1; // mutator (изначально считаем значение дроби измененным)

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

            System.out.println("Реальное вычисление, кэш сброшен");
            return baseFraction.doubleValue();

    }

}
