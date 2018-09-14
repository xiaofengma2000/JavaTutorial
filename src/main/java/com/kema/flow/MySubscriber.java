package com.kema.flow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Flow;
import java.util.function.Consumer;

public class MySubscriber<T> implements Flow.Subscriber<T> {

    static int index = 1;

    String name;
    Logger log = LoggerFactory.getLogger(MySubscriber.class);
    Flow.Subscription mySubscription;

    Consumer<T> nextFunction;

    public MySubscriber(Consumer<T> func){
        this.nextFunction = func;
        this.name = "Subscriber" + index++;
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
    public void onNext(T item) {
        nextFunction.accept(item);
        mySubscription.request(1);
        log.info("{} item consumed : {}", name, item);
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
