import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * 杂项测试类
 */
public class Test {



    public static void main(String[] args) {
        ArrayList<String> a = new ArrayList<>();
        a.add("1");
        a.add("2");

        ArrayList<String> b = a;
        System.out.println(a);
        System.out.println(b);
        b.add("3");
        System.out.println(a);
        System.out.println(b);
        b.set(2,"0");
        System.out.println(a);
        System.out.println(b);

//        ArrayList<String> c = (ArrayList<String>) a.clone();
//        System.out.println(a);
//        System.out.println(b);
//        System.out.println(c);
//        c.add("4");
//        System.out.println(a);
//        System.out.println(b);
//        System.out.println(c);

    }
}
