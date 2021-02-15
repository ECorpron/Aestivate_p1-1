import com.revature.TestClass;

import java.sql.SQLException;

public class TestClassTest {

    public static void main(String[] args) {
        TestClass test = new TestClass();
        try {
            test.createTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
