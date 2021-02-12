import com.revature.factories.PostgreSQLSessionFactory;
import com.revature.util.Configuration;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PostgreSQLSessionFactoryTest {

    public static void main(String[] args) {

        Properties props = new Properties();

        try {
            props.load(new FileReader("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Configuration con = new Configuration(props.getProperty("url"), props.getProperty("admin-usr"),
                props.getProperty("admin-pw"));

        PostgreSQLSessionFactory sql = new PostgreSQLSessionFactory(con);
        sql.getConnection();
        System.out.println("Got a connection!");
    }
}
