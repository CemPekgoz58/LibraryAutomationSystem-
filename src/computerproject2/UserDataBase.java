package computerproject2;

import java.util.ArrayList;
import java.util.List;

public class UserDataBase {

    private static DBConnection db = new DBConnection();
    public static ArrayList<Member> members = new ArrayList<>();

    // Uygulama açılırken veritabanından tüm üyeleri yükle
    public static void loadMembersFromDB() {
        List<Member> allMembers = db.getAllMembers();
        members.clear();
        members.addAll(allMembers);
    }

    // Yeni üye ekle hem listeye hem veritabanına
    public static void addMember(Member member) {
        members.add(member);       // Belleğe ekle
        db.addMember(member);      // Veritabanına ekle
    }

    // Giriş doğrulaması
    public static boolean authenticate(String email, String password) {
        for (Member member : members) {
            if (member.getEmail().equals(email) && member.getPassword().equals(password)) {
                Member.setCurrentMember(member);
                return true;
            }
        }
        return false;
    }
}
