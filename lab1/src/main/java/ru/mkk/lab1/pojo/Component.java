package ru.mkk.lab1.pojo;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Component {

    UUID uuid;
    Type type;
    String name;
    String manufacture;
    List<Component> incompatible;

    public boolean isCompatible(@NonNull Component component) {
        for (Component component1 : incompatible) {
            if (component1.getUuid().equals(component.getUuid())) {
                return false;
            }
        }
        return true;
    }

    public static Component of(
            @NonNull Type type,
            @NonNull String name,
            @NonNull String manufacture,
            Component ...incompatible
    ) {
        return new Component(UUID.randomUUID(), type, name, manufacture, Arrays.asList(incompatible));
    }

    public enum Type {
        CPU,
        GPU,
        MOTHERBOARD
    }

}
