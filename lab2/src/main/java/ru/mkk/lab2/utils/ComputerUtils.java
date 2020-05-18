package ru.mkk.lab2.utils;

import lombok.NonNull;
import lombok.SneakyThrows;
import ru.mkk.lab2.exceptions.InCompatibleComponentException;
import ru.mkk.lab2.pojo.Component;
import ru.mkk.lab2.pojo.Computer;

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
        for (Component motherboard : motherboards) {
            Computer.Builder computer = computerWithMotherboard(motherboard);
            for (Component gpu : gpus) {
                try {
                    computer.setComponent(gpu);
                } catch (InCompatibleComponentException ignored) {
                }
                for (Component cpu : cpus) {
                    try {
                        computer.setComponent(cpu);
                        computers.add(computer.build());
                    } catch (InCompatibleComponentException ignored) {
                    }
                }
            }
        }
        return Collections.unmodifiableList(computers);
    }

    // можно сказать что это костыль, но при создании нового билдера и установке первого компонента эксепшена
    // никогда не будет, главное никому не показывайте
    @SneakyThrows
    private static Computer.Builder computerWithMotherboard(@NonNull Component motherboard) {
        return Computer.newBuilder().setComponent(motherboard);
    }

}
