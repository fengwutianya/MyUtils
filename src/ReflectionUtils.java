import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by xuan on 2017/2/20 0020.
 */
public class ReflectionUtils {
    /**
     * 得到函数名为methodName，参数列表为parameterTypes的Method对象
     * @param object
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
        for (Class<?> superClass = object.getClass();
                superClass != Object.class;
                superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
                //Method 继续向上转型
            }
        }
        return null;
    }

    /**
     * 直接调用对象的方法，返回函数的返回值，忽略修饰符
     * @param object
     * @param methodName
     * @param parameterTypes
     * @param parameters
     * @return
     * @throws InvocationTargetException
     */
    public static Object invokeMethod(Object object,
                                      String methodName,
                                      Class<?>[] parameterTypes,
                                      Object [] parameters) throws InvocationTargetException {
        Method method = getDeclaredMethod(object, methodName, parameterTypes);

        if (method == null) {
            throw new IllegalArgumentException("Could not find method" +
                    " [" + methodName + "]");
        }

        method.setAccessible(true);

        try {
            return method.invoke(object, parameters);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 找到fieldName所对应的field，遍历父类
     * @param object
     * @param fieldName
     * @return field
     */
    public static Field getDeclaredField(Object object, String fieldName) {
        for (Class<?> superClass = object.getClass();
                superClass != Object.class;
                superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                //如果没有此field，继续向上找
                //并不抛出异常
            }
        }
        return null;
    }

    /**
     * 如果是private的，改变其为public
     * @param field
     */
    private static void makeAccessible(Field field) {
        if (!Modifier.isPublic(field.getModifiers())) {
            field.setAccessible(true);
        }
    }

    /**
     * 为object对象字段名为fieldName的属性赋值value
     * @param object
     * @param fieldName
     * @param value
     */
    public static void setFieldValue(Object object, String fieldName, Object value) {
        Field field = ReflectionUtils.getDeclaredField(object, fieldName);

        if (field == null) throw new IllegalArgumentException("Could" +
                " not find field [" + fieldName + "]");

        makeAccessible(field);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回属性值
     * @param object
     * @param fieldName
     * @return value
     */
    public static Object getFieldValue(Object object, String fieldName) {
        Field field = ReflectionUtils.getDeclaredField(object, fieldName);
        if (field == null) throw new IllegalArgumentException("Could" +
                " not find field [" + fieldName + "]");
        makeAccessible(field);
        Object value = null;
        try {
            value = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }
}
