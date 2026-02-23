package org.example.Listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int attempt=0;
    public static final int max_retry_count=2;
    @Override
    public boolean retry(ITestResult result){
        if(attempt<max_retry_count){
            attempt++;
            System.out.println("Retrying test:" +result.getName()+"(Attempt "+(attempt+1)+")");
            return true;
        }
        return false;
    }
}
