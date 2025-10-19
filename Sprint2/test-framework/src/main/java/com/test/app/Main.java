package main.java.com.test.app;

import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        TestClass obj = new TestClass();

        // parcourir toutes les méthodes de TestClass
        for (Method method : obj.getClass().getDeclaredMethods()) {
            // vérifier si la méthode a l'annotation MyAnnotation
            if (method.isAnnotationPresent(MyAnnotation.class)) {
                MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);
                System.out.println("Annotation trouvée sur : " + method.getName());
                System.out.println("Valeur de l'annotation : " + annotation.value());

                try {
                    method.invoke(obj); // exécuter la méthode
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
