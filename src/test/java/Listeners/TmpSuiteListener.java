package Listeners;

import org.testng.*;

public class TmpSuiteListener implements ISuiteListener {
    @Override
    public void onStart(ISuite iSuite) {
        System.out.println("----------Start Suite----------------\n\n");

    }

    @Override
    public void onFinish(ISuite iSuite) {
        System.out.println("----------FINISH SUITE----------------\n\n");

    }

}


