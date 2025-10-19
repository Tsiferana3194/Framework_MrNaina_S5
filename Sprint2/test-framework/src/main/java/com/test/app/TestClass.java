package main.java.com.test.app;

public class TestClass {

    @MyAnnotation(value = "Hello annotation !")
    public void testMethod() {
        System.out.println("La méthode testMethod a été appelée.");
    }
}
