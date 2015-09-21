package saul;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author alexis
 */
public class ChatSerializer {
    private static String version;
    public static void init(String version){
        ChatSerializer.version=version;
    }
    
    public static Class<?> getNMSClass(String nmsname) throws ClassNotFoundException {
        Class<?> nmsClass = Class.forName("net.minecraft.server."+version+"."+nmsname);
        return nmsClass;
    }
    public static Object serialize(String s) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        Class icbc=getNMSClass("IChatBaseComponent");
        Class cs=icbc.getClasses()[0];
        Method a=cs.getMethod("a", String.class);
        return a.invoke(null, s);
    }
}
