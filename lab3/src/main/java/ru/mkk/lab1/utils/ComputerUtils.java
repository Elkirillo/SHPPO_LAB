package ru.mkk.lab1.utils;

import lombok.NonNull;
import lombok.SneakyThrows;
import ru.mkk.lab1.Context;
import ru.mkk.lab1.exceptions.InCompatibleComponentException;
import ru.mkk.lab1.pojo.Component;
import ru.mkk.lab1.pojo.Computer;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public abstract class ComputerUtils {

    private static final ExecutorService executor = Context.getInstance().getExecutor();

    public static List<Computer> getAllComputersByComponents(
            @NonNull List<Component> cpus,
            @NonNull List<Component> gpus,
            @NonNull List<Component> motherboards
    ) {
        Collection<Computer> computers = new ConcurrentLinkedDeque<>();
        List<Future<?>> tasks = new LinkedList<>();
        for (Component motherboard : motherboards) {
            tasks.add(executor.submit(() -> {
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
            }));
        }
        tasks.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        return List.copyOf(computers);
    }

    // можно сказать что это костыль, но при создании нового билдера и установке первого компонента эксепшена
    @SneakyThrows
    private static Computer.Builder computerWithMotherboard(@NonNull Component motherboard) {
        return Computer.newBuilder().setComponent(motherboard);
    }

}
