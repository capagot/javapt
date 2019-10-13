package javapt;

final public class PathTracer {
    public static void main(final String[] str) {
        try {
            Application app = new Application(str);
            app.run();
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
