package com.gk.di;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class WeekDayBlocker implements MethodInterceptor {

    public Object invoke(MethodInvocation invocation) throws Throwable {
        Calendar today = new GregorianCalendar();
        int dayOfWeek = today.get(Calendar.DAY_OF_WEEK);
        // Calendar.MONDAY = 2, Calendar.FRIDAY = 6
        if (dayOfWeek >= Calendar.MONDAY && dayOfWeek <= Calendar.FRIDAY) {
            throw new IllegalStateException(
                    invocation.getMethod().getName() + " not allowed on weekdays!");
        }
        return invocation.proceed();
    }
}
