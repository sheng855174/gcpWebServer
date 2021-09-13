package sheng.gcp.web.server.common;

public class Generater {

    private static final String passwordChar =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz" +
            "01234567890123456789" +
            "~!@#$%^&*+-~!@#$%^&*+-";

    private static final int password_num = 12;

    public static String generatePassword(){
        StringBuffer password = new StringBuffer();
        int length = passwordChar.length();
        for(int i=0;i<password_num;i++){
            int random = (int) (Math.random()*length);
            password.append(passwordChar.charAt(random));
        }
        return password.toString();
    }

}
