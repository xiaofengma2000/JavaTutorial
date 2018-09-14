package com.kema.flow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Function;

public class MyProcessor<in, out> implements Flow.Processor<in, out> {


    static final Logger log = LoggerFactory.getLogger(MyProcessor.class);

    private final SubmissionPublisher<out> submissionPublisher = new SubmissionPublisher<>();
    Flow.Subscription mySubscription;

    Function<in, out> mapFunc;

    @Override
    public void subscribe(Flow.Subscriber<? super out> subscriber) {
        submissionPublisher.subscribe(subscriber);
        log.info("Subscriber {} registered.", subscriber);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        if(mySubscription != null){
            log.error("Already subscribed, ignore the new subscription : {}", subscription);
        } else {
            mySubscription = subscription;
            mySubscription.request(1);
        }
    }

    @Override
    public void onNext(in item) {
        submissionPublisher.submit(mapFunc.apply(item));
        mySubscription.request(1);
        log.info("item {} procressed", item);
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Error : {}", throwable);
        mySubscription.cancel();
    }

    @Override
    public void onComplete() {
        mySubscription.cancel();
        log.info("Done");
    }

}
