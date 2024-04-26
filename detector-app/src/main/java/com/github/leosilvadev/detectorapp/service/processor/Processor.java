package com.github.leosilvadev.detectorapp.service.processor;

public interface Processor<T> {

    T onEvent(T event);

    void onError(Throwable ex);

}
