package ru.task.demo.util;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Dependent
public class LoggerProducer {
    @Produces
    public Logger getLogger(InjectionPoint p) {
        return LoggerFactory.getLogger(p.getMember().getDeclaringClass().getName());
    }
}
