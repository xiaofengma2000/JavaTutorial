package com.kema.flow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class MyPublisher<T> implements Flow.Publisher<T> {

    static final Logger log = LoggerFactory.getLogger(MyPublisher.class);

    public int submit(T item) {
        return submissionPublisher.submit(item);
    }

    private final SubmissionPublisher<T> submissionPublisher = new SubmissionPublisher<>();

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        submissionPublisher.subscribe(subscriber);
        log.info("Subscriber {} registered.", subscriber);
    }



}
