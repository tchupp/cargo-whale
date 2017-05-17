package com.cargowhale.docker.rx;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class RequestAttributesAwareSingleObserver<T> implements SingleObserver<T> {

    public static <T> SingleObserver<T> build(final SingleObserver<T> observer) {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        return new RequestAttributesAwareSingleObserver<>(observer, requestAttributes);
    }

    private final SingleObserver<T> observer;
    private final RequestAttributes requestAttributes;

    private RequestAttributesAwareSingleObserver(final SingleObserver<T> observer, final RequestAttributes requestAttributes) {
        this.observer = observer;
        this.requestAttributes = requestAttributes;
    }

    @Override
    public void onSubscribe(final Disposable disposable) {
        this.observer.onSubscribe(disposable);
    }

    @Override
    public void onSuccess(final T item) {
        RequestContextHolder.setRequestAttributes(this.requestAttributes);
        this.observer.onSuccess(item);
    }

    @Override
    public void onError(final Throwable error) {
        this.observer.onError(error);
    }
}
