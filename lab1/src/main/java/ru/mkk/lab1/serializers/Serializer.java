package ru.mkk.lab1.serializers;

public interface Serializer<T> {
    String serialize(T object);
}
