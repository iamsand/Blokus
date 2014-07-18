package framework;

public class Tester {
    
    public static void main(String[] args) {
        Shape s = new Shape(Misc.tetr[2]);
        System.out.println(s.toConsoleString());
        s.rotateCW(1);
        System.out.println(s.toConsoleString());
        s.rotateCW(1);
        System.out.println(s.toConsoleString());
        s.rotateCW(1);
        System.out.println(s.toConsoleString());
    }
    
}
