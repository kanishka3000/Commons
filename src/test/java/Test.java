/*
 * Copyright 2006 (C) Kanishka Weerasekara
 *
 * Created on : 18/11/2018
 * Author     : Kanishka Weerasekara
 *

 */
import commons.util.VInteger;
import commons.util.VIntegerTest;
import commons.util.VLongTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Test {


    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ConversionsTest.class,
               VLongTest.class, VIntegerTest.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}
