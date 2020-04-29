package ru.mkk.lab1.pojo;

import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
public abstract class Component {

    UUID uuid;
    String name;
    String manufacture;
    List<Component> incompatible;

    public static Component cpu(String name, String manufacture, Component... incompatible) {
        return new CPU(name, manufacture, Arrays.asList(incompatible));
    }

    public static Component gpu(String name, String manufacture, Component... incompatible) {
        return new GPU(name, manufacture, Arrays.asList(incompatible));
    }

    public static Component motherboard(String name, String manufacture, Component... incompatible) {
        return new Motherboard(name, manufacture, Arrays.asList(incompatible));
    }

    public boolean isCompatible(@NonNull Component component) {
        for (Component component1 : incompatible) {
            if (component1.getUuid().equals(component.getUuid())) {
                return false;
            }
        }
        return true;
    }

    protected Component(String name, String manufacture, List<Component> incompatible) {
        this.name = name;
        this.manufacture = manufacture;
        this.incompatible = incompatible;
        uuid = UUID.randomUUID();
    }

}
