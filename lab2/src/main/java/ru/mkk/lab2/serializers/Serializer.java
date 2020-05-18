package ru.mkk.lab2.serializers;

public interface Serializer<T> {
    String serialize(T object);
}
