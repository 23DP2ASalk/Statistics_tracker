package lv.rvt;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    AppTest.class,
    PlayerTest.class,
    ColorTest.class,
    EnumsTest.class,
    PlayerServicePersistenceTest.class
})
public class TestSuite {
    // This empty class acts as a holder for the above annotations
}