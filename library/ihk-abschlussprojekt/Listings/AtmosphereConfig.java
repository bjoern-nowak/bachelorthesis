package com.oge.contrax.shared.infrastructure.config;

import *;

@Configuration
public class AtmosphereConfig {

    public static void init(ServletRegistration.Dynamic atmosphereRegistration) {
        atmosphereRegistration.setAsyncSupported(true);
        atmosphereRegistration.setInitParameter(
                "org.atmosphere.cpr.packages", "com.oge.contrax.shared.infrastructure.async.atmosphere");
        atmosphereRegistration.setInitParameter(
                "org.atmosphere.interceptor.HeartbeatInterceptor.paddingChar", "hb");
        atmosphereRegistration.setInitParameter(
                "org.atmosphere.interceptor.HeartbeatInterceptor.heartbeatFrequencyInSeconds", "300");
        atmosphereRegistration.setInitParameter(
                "org.atmosphere.interceptor.HeartbeatInterceptor.clientHeartbeatFrequencyInSeconds", "0");
        atmosphereRegistration.setInitParameter(
                "org.atmosphere.cpr.broadcaster.shareableThreadPool", "true");
    }
}