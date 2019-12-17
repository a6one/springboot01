package com.uplooking.SerializedLambda;

import java.io.Serializable;

@FunctionalInterface
public interface IGetter<T> extends Serializable {
    Object get(T source);
}
