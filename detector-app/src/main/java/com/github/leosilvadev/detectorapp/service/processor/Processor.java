package com.github.leosilvadev.detectorapp.service.processor;

import java.util.List;

public interface Processor<T> {

    List<T> onEvents(List<T> events);

    void onError(Throwable ex);

}
