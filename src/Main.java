

public class Main {
    public static void main(String[] args) {
        CustomHashMap<Integer,String> chm = new CustomHashMap();
        chm.put(0,"Hello");
        chm.put(1,"World");

        System.out.println(chm.get(0));
        chm.put(0,"Bye");
        System.out.println(chm.get(0));
        chm.remove(0);
        System.out.println(chm.get(0));
    }
}
