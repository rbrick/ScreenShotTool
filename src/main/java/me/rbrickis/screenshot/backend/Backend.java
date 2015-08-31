package me.rbrickis.screenshot.backend;

public interface Backend<T> {

    Result upload(T object);

    String getName();

}
