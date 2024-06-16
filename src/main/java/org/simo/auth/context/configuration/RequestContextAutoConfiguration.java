package org.simo.auth.context.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Author: Simeon Popov
 * Date of creation: 6/16/2024
 */
@AutoConfiguration
@ComponentScan(basePackages = "org.simo.auth.context.provider")
public class RequestContextAutoConfiguration {
}