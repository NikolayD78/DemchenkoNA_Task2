import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.HashMap;

public class FractionableInvHandler implements InvocationHandler {

    private Object obj;
    private HashMap <String,Object> cacheMap;// при добавлении количества кэшированных методов в интерфейс Fractionable -
                                             // все они будут учтены без необходимости изменений
    public FractionableInvHandler(Object obj)
        {
            this.obj = obj;
            cacheMap=new HashMap<>();
        }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //System.out.println("It works");
        Object retValue;
        Method m = obj.getClass().getMethod(method.getName(), method.getParameterTypes());

        Annotation[] anns = m.getAnnotationsByType(Mutator.class);
        for (Annotation a: anns)
        {
            cacheMap.clear(); // сбрасываем кэш, сразу для всех кэшируемых функций, сколько бы их ни было

            System.out.println("Сброшен кэш, по факту вызова функции с аннотацией @Mutator: "+m.getName());
            return m.invoke(obj, args);
        }
        anns = m.getAnnotationsByType(Cache.class);
        for (Annotation a: anns)
            {
                if(cacheMap.get(method.getName())==null) // значения еще нет в кэше, добавляем
                {
                    retValue=m.invoke(obj, args);

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
