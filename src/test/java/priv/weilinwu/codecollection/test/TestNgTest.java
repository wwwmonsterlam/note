package priv.weilinwu.codecollection.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class TestNgTest {
	
	public static final Logger logger = LoggerFactory.getLogger(TestNgTest.class);
	
	@Test
	public void printHelloWorld() {
		logger.info("Hello TestNg!~~");
	}
}
