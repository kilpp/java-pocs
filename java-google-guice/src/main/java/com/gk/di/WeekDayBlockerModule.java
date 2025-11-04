package com.gk.di;

import com.gk.aop.NotOnWeekDays;
import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class WeekDayBlockerModule extends AbstractModule {

    @Override
    protected void configure() {
        // create a single instance and bind it so Guice can inject it if needed
        WeekDayBlocker blocker = new WeekDayBlocker();
        bind(WeekDayBlocker.class).toInstance(blocker);

        // apply the interceptor to any class method annotated with @NotOnWeekDays
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(NotOnWeekDays.class), blocker);
    }
}
