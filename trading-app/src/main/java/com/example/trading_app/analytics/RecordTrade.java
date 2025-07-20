package com.example.trading_app.analytics;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RecordTrade {
    /** 
     * SpEL expression to pick out the price parameter 
     * (default assumes first method arg is BigDecimal price)
     */
    String priceArg() default "#p0";
}
