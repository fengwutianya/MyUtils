import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by xuan on 2017/2/20 0020.
 */
public class Test {
    public static void main(String[] args) {
        Son son = new Son();
        Field field = null;
//        try {
//            field = son.getClass().getDeclaredField("father");
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
        field = ReflectionUtils.getDeclaredField(son, "father");
        System.out.println(field);
        ReflectionUtils.setFieldValue(son, "father", 1);
        System.out.println(ReflectionUtils.getFieldValue(son, "father"));
        ReflectionUtils.setFieldValue(son, "father", 2);
        System.out.println(ReflectionUtils.getFieldValue(son, "father"));
//        System.out.println(ReflectionUtils.getFieldValue(son, "hello"));
        Class<?>[] parameterTypes = new Class[1];
        parameterTypes[0] = int.class;
        Object[] parameters = new Object[1];
        parameters[0] = 12;
        Method method = ReflectionUtils.getDeclaredMethod(son, "say1", parameterTypes);
        System.out.println(method);
        try {
            ReflectionUtils.invokeMethod(son, "say1", parameterTypes, parameters);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

class Father {
    private void say1(int x) {
        System.out.println(x);
    }
    public int getFather() {
        return father;
    }

    public void setFather(int father) {
        this.father = father;
    }

    private int father;
}

class Son extends Father {
    private int son;
}
