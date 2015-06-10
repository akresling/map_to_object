package map.object;

import java.util.Map;
import java.util.Map.Entry;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

public class ObjectMapper {

    public Class<?> generate(String className, Map<String, Class<?>> objectMapping) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass(className);

        for (Entry<String, Class<?>> entry : objectMapping.entrySet()) {
            CtField currField = new CtField(this.resolveCtClass(entry.getValue()), entry.getKey(), cc);
            cc.addField(currField);

            String getterName = String.format(
                    "get%s",
                    entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1)
                    );
            String setterName = String.format(
                    "set%s",
                    entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1)
                    );
            cc.addMethod(CtNewMethod.getter(getterName, currField));
            cc.addMethod(CtNewMethod.setter(setterName, currField));

        }

        return cc.toClass();
    }

    private CtClass resolveCtClass(Class<?> clazz) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        return pool.get(clazz.getName());
    }
}
