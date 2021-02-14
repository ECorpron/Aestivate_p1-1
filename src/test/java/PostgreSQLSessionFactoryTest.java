import com.revature.factories.PostgreSQLSessionFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PostgreSQLSessionFactoryTest {

    public static void main(String[] args) {

        PostgreSQLSessionFactory sql = new PostgreSQLSessionFactory();
        sql.getConnection();
        System.out.println("Got a connection!");
    }
}
