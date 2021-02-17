import com.revature.TestClass;

import java.sql.SQLException;

public class TestClassTest {

    public static void main(String[] args) {
        TestClass test = new TestClass(1, "Eli");
        //test.createTableIfNonexistant();
        test.save();

        test.setName("Corpron");
        test.save();
    }
}
