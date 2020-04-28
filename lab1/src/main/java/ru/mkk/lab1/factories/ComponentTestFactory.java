package ru.mkk.lab1.factories;

import lombok.Getter;
import ru.mkk.lab1.pojo.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// т.к все данные неизменяемые, то кэшируем все компоненты при создании класса
public class ComponentTestFactory implements TestFactory {

    @Getter
    private final List<Component> gpus;

    @Getter
    private final List<Component> cpus;

    @Getter
    private final List<Component> motherboards;

    public ComponentTestFactory() {
        motherboards = List.of(
                Component.of(Component.Type.MOTHERBOARD, "z370", "msi"),
                Component.of(Component.Type.MOTHERBOARD, "z380", "msi")
        );
        gpus = List.of(
                Component.of(Component.Type.GPU, "1060", "nvidia"),
                Component.of(Component.Type.GPU, "1080", "nvidia")
        );
        cpus = List.of(
                Component.of(Component.Type.CPU, "i7 8700k", "intel", motherboards.get(1)),
                Component.of(Component.Type.CPU, "i7 9700k", "intel", motherboards.get(0))
        );
    }

    @Override
    public List<Component> getData() {
        return Collections.unmodifiableList(new ArrayList<>() {{
            addAll(getMotherboards());
            addAll(getCpus());
            addAll(getGpus());
        }});
    }
}
