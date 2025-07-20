// package com.example.trading_app.analytics;

// import org.aspectj.lang.ProceedingJoinPoint;
// import org.aspectj.lang.annotation.*;
// import org.aspectj.lang.reflect.MethodSignature;
// import org.springframework.stereotype.Component;

// import java.lang.reflect.Method;
// import java.math.BigDecimal;
// import java.util.Map;

// import org.springframework.expression.ExpressionParser;
// import org.springframework.expression.spel.standard.SpelExpressionParser;
// import org.springframework.expression.spel.support.StandardEvaluationContext;

// @Aspect
// @Component
// public class TradeRecordingAspect {

//     private final RollingAnalyticsService analyticsService;
//     private final ExpressionParser parser = new SpelExpressionParser();

//     public TradeRecordingAspect(RollingAnalyticsService analyticsService) {
//         this.analyticsService = analyticsService;
//     }

//     @Around("@annotation(com.example.trading_app.analytics.RecordTrade)")
//     public Object aroundRecord(ProceedingJoinPoint pjp) throws Throwable {
//         // execute the original method
//         Object result = pjp.proceed();

//         // read annotation
//         MethodSignature sig = (MethodSignature) pjp.getSignature();
//         Method method = sig.getMethod();
//         RecordTrade ann = method.getAnnotation(RecordTrade.class);

//         // prepare SpEL context with method args
//         StandardEvaluationContext ctx = new StandardEvaluationContext();
//         Object[] args = pjp.getArgs();
//         for (int i = 0; i < args.length; i++) {
//             ctx.setVariable("p" + i, args[i]);
//         }

//         // evaluate the price expression
//         BigDecimal price = parser.parseExpression(ann.priceArg())
//                                  .getValue(ctx, BigDecimal.class);

//         // record the trade
//         if (price != null) {
//             analyticsService.recordTrade(price);
//         }

//         return result;
//     }
// }
