import com.revature.util.SessionManager;

import java.sql.Connection;

public class SessionManagerTest {

    public static void main(String[] args) {
        Connection conn = SessionManager.getConnection();
        if (conn != null) {
            System.out.println("Got a connection!");
        }
    }
}
