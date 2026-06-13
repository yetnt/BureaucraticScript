import com.yetnt.tokenzier.utils.Find;

public class playground {
    public static void main(String[] args) {
        String[] split = Find.splitByAmount("Time: 13:2", ":", 2);
        System.out.println(split);
    }
}
