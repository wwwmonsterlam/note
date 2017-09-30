package priv.weilinwu.codecollection.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class MyTestListener implements ITestListener {

	public static final Logger logger = LoggerFactory.getLogger(MyTestListener.class);
	
	@Override
	public void onFinish(ITestContext testContext) {
		logger.info("TestListener-onFinish method was run");
	}

	@Override
	public void onStart(ITestContext testContext) {
		 logger.info("TestListener-onStart method was run");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult testResult) {
		 logger.info("TestListener-onTestFailedButWithinSuccessPercentage method was run");
	}

	@Override
	public void onTestFailure(ITestResult testResult) {
		logger.info("TestListener-onStart method was run");		
	}

	@Override
	public void onTestSkipped(ITestResult testResult) {
		logger.info("TestListener-onTestFailure method was run");		
	}

	@Override
	public void onTestStart(ITestResult arg0) {
		logger.info("TestListener-onTestStart method was run");		
	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		logger.info("TestListener-onTestSuccess method was run");	
	}

}
