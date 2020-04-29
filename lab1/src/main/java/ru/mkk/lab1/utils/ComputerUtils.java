package ru.mkk.lab1.utils;

import lombok.NonNull;
import lombok.SneakyThrows;
import ru.mkk.lab1.exceptions.InCompatibleComponentException;
import ru.mkk.lab1.exceptions.IncorrectTypeException;
import ru.mkk.lab1.pojo.Component;
import ru.mkk.lab1.pojo.Computer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ComputerUtils {

    public static List<Computer> getAllComputersByComponents(
            @NonNull List<Component> cpus,
            @NonNull List<Component> gpus,
            @NonNull List<Component> motherboards
    ) {
        List<Computer> computers = new ArrayList<>();
        cpus.forEach(component -> checkType(component, Component.Type.CPU));
        gpus.forEach(component -> checkType(component, Component.Type.GPU));
        motherboards.forEach(component -> checkType(component, Component.Type.MOTHERBOARD));
        for (Component motherboard : motherboards) {
            Computer.Builder computer = computerWithMotherboard(motherboard);
            for (Component gpu : gpus) {
                    try {
                        computer.setGpu(gpu);
                    } catch (InCompatibleComponentException ignored) {
                    }
                    for (Component cpu : cpus) {
                        try {
                            computer.setCpu(cpu);
                            computers.add(computer.build());
                        } catch (InCompatibleComponentException ignored) {
                        }
                    }
                }
        }
        return Collections.unmodifiableList(computers);
    }

    public static void checkType(Component component, Component.Type type) {
        if (component.getType() != type)
            throw new IncorrectTypeException();
    }

}
