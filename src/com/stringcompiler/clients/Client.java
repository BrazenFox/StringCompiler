// The Client code

package com.stringcompiler.clients;

import java.lang.reflect.Method;
import java.util.logging.Logger;

import com.stringcompiler.loaders.StringClassLoader;

public class Client {
    private static final Logger logger =
            Logger.getLogger(Client.class.getName());

    public static void main(String[] args) throws Exception {
        final String simpleProgram = "public class SimpleProgram {" +
                " public static void main(String[] args) {" +
                "    System.out.println(\"Hello from SimpleProgram!\");}}";

        testSimpleProgram(simpleProgram);

        final String complexProgram = "package com.stringcompiler;" +
                "import java.util.Random;" +
                "public class ComplexProgram {"+
                "  public static void main(String[] args) {" +
                "   System.out.println(\"Sup from Fubar\");}" +
                "public int getRandomNumber() {" +
                "  return (new Random()).nextInt(100);}}";

        testComplexProgram(complexProgram);
    }

    private static void testSimpleProgram(final String simpleProgram) throws Exception {
        logger.info("Testing SimpleProgram");

        Class<?> simpleClazz =
                StringClassLoader.getInstance().loadClassFromString(simpleProgram);

        if (simpleClazz != null) {
            Method main = simpleClazz.getDeclaredMethod("main", String[].class);

            if (main != null) {
                main.invoke(null, (Object)null);
            }
        }
        logger.info("Finished testing SimpleProgram");
    }

    private static void testComplexProgram(final String complexProgram) throws Exception {
        logger.info("Testing ComplexProgram");

        Class<?> complexClazz =
                StringClassLoader.getInstance().loadClassFromString(complexProgram);

        if (complexClazz != null) {
            Object obj = complexClazz.getConstructor().newInstance();
            if (obj != null) {
                Method main = complexClazz.getDeclaredMethod("main", String[].class);

                if (main != null) {
                    main.invoke(null, (Object)null);
                }

                Method getRandomNumber = complexClazz.getDeclaredMethod("getRandomNumber");
                if (getRandomNumber != null) {
                    int n = (int)getRandomNumber.invoke(obj);
                    System.out.format("Random number = %d\n", n);
                }
            }
        }
        logger.info("Finished testing ComplexProgram");
    }
}