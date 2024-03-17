import java.lang.annotation.Annotation;
import java.lang.annotation.RetentionPolicy;
import java.lang.invoke.VarHandle;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

public class FractionableInvHandler implements InvocationHandler {

    private Object obj;
    private HashMap <String,Double> cacheMap;// при добавлении количества кэшированных методов в интерфейс Fractionable -
                                             // все они будут учтены без необходимости изменений
    public FractionableInvHandler(Object obj)
        {
            this.obj = obj;
            cacheMap=new HashMap<>();
        }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //System.out.println("It works");
        double retValue;
        Method m = obj.getClass().getMethod(method.getName(), method.getParameterTypes());
        Field flagMutator=obj.getClass().getDeclaredField("flagMutator");
        flagMutator.setAccessible(true);


        Annotation[] anns = m.getAnnotationsByType(Mutator.class);
        for (Annotation a: anns)
        {
            if(flagMutator!=null)
                flagMutator.setInt(obj,1);

            System.out.println("Установлен флаг Mutator, по факту вызова функции: "+m.getName());
            return m.invoke(obj, args);
        }
        anns = m.getAnnotationsByType(Cache.class);
        for (Annotation a: anns)
            {

                if(flagMutator.getInt(obj)==1)
                {
                    flagMutator.setInt(obj,0);
                    retValue=(double)m.invoke(obj, args);

                    cacheMap.put(method.getName(),retValue);
                    System.out.println("retValue "+retValue);
                    return retValue;

                }

                System.out.println("Значение из кэша");
                return cacheMap.get(method.getName());
            }

          return m.invoke(obj, args);


    }


}
