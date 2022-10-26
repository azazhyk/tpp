package stu.tpp;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
public class TestRunner {
    public static void main(String[] args) throws ClassNotFoundException {

        if (args.length != 1) {
            throw new IllegalArgumentException("Exactly 1 argument must be provided");
        }
        Class<?> testClass = Class.forName(args[0]);

        StringBuilder result = new StringBuilder("Class " + testClass + " successfully loaded\n");
        Method[] declaredMethods = testClass.getDeclaredMethods();

        int successful = 0;
        int test = 0;

        for (Method declaredMethod : declaredMethods) {
            String nameMethod = declaredMethod.getName();

            //check for name began with test
            if (nameMethod.indexOf("test") == 0) {
                result.append("\tTest: ").append(nameMethod);
            } else {
                result.append("\tMethod: ").append(nameMethod).append(" is not test method\n");
                continue;
            }

            //check modifier of method
            if (declaredMethod.getModifiers() != Modifier.PUBLIC) {
                result.append(" is not public method\n");
                continue;
            } else //check parameters
                if (declaredMethod.getParameterCount() != 0) {
                result.append(" method takes parameters\n");
                continue;
            } else if (!declaredMethod.getReturnType().toString().equals("void")) {
                result.append(" method isn`t void\n");
                continue;
            }
            test++;
            try {
                CalculatorTest obj = (CalculatorTest) testClass.getDeclaredConstructor().newInstance();
                Method mth = obj.getClass().getDeclaredMethod(nameMethod);
                mth.invoke(obj);
                result.append(" SUCCESSFUL\n");
                successful++;
            } catch (Exception e) {
                result.append(" FAILED, error: ").append(e.getCause().getMessage()).append("\n");
            }

        }
        result.append("\tTotal methods: ").append(declaredMethods.length).append(" Total tests: ").append(test).append(" Successful: ").append(successful).append(" Failed: ").append(test - successful);
        System.out.println(result);


    }
}