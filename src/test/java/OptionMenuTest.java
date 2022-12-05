import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class OptionMenuTest {

    @org.junit.jupiter.api.Test
    void readFromFile() throws IOException {
        Account test = new Account(12345, 12345, 16000.00, 45000.00);
        OptionMenu optionTest = new OptionMenu();

        optionTest.writeToFile();
        optionTest.readFromFile();
    }
}