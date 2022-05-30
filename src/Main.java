import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        final Pattern p = Pattern.compile("(?<prefix>\\d{3})-(?<suffix>\\d{3})");
        final Matcher m = p.matcher("123-321");
        while (m.find()) {
            System.out.println(m.group());
            System.out.println(m.group("prefix"));
            System.out.println(m.group("suffix"));
        }
    }
}