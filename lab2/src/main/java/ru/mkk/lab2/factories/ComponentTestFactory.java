package ru.mkk.lab2.factories;

import lombok.Getter;
import ru.mkk.lab2.pojo.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// т.к все данные неизменяемые, то кэшируем все компоненты при создании класса
@org.springframework.stereotype.Component
public class ComponentTestFactory implements ComponentFactory {

    @Getter
    private final List<Component> gpus;

    @Getter
    private final List<Component> cpus;

    @Getter
    private final List<Component> motherboards;

    public ComponentTestFactory() {
        motherboards = List.of(
                Component.motherboard("z370", "msi"),
                Component.motherboard("z380", "msi")
        );
        gpus = List.of(
                Component.gpu("1060", "nvidia"),
                Component.gpu("1080", "nvidia")
        );
        cpus = List.of(
                Component.cpu("i7 8700k", "intel", motherboards.get(1)),
                Component.cpu("i7 9700k", "intel", motherboards.get(0))
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
