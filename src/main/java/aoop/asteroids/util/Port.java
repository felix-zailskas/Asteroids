package aoop.asteroids.util;

public class Port {

    public static boolean validPort(String s) {
        System.out.println(s);
        if (s.equals("")) return true;
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
