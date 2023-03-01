package by.smirnov.guitarstoreproject.constants;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public interface TestConstants {

    Long TEST_ID = 1L;
    String TEST_ADDRESS = "Address";
    String TEST_STRING = "Test";
    Timestamp TEST_DATE_TIME = Timestamp.valueOf(LocalDateTime.of(2023, 1, 1, 12, 0));
}
