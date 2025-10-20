package main.java.com.test.app;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)  // nécessaire pour pouvoir lire l'annotation à l'exécution
@Target(ElementType.METHOD)          // l'annotation s'applique uniquement aux méthodes
public @interface MyAnnotation {
    String value() default "default value";  // paramètre optionnel
}
