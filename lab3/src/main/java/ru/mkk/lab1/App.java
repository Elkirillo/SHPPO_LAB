package ru.mkk.lab1;

import ru.mkk.lab1.exceptions.InCompatibleComponentException;
import ru.mkk.lab1.pojo.Component;
import ru.mkk.lab1.pojo.Computer;
import ru.mkk.lab1.serializers.Serializer;
import ru.mkk.lab1.utils.ComputerUtils;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class App implements Runnable {

    private final List<Component> cpus;
    private final List<Component> gpus;
    private final List<Component> motherboards;
    private final Serializer<Computer> computerSerializer;
    private final ExecutorService executor;

    @Override
    public void run() {
        for(int i = 0; i <= 1; i++){
        executor.submit(() -> {
            try {
                System.out.println(computerSerializer.serialize(
                        Computer.newBuilder()
                                .setComponent(motherboards.get(i))
                                .setComponent(cpus.get(i))
                                .setComponent(gpus.get(i))
                                .build()
                ));
            } catch (InCompatibleComponentException e) {
                e.printStackTrace();
            }
        });
        }
        
        String serializedComputers = ComputerUtils.getAllComputersByComponents(cpus, gpus, motherboards)
                .parallelStream()
                .map(computerSerializer::serialize)
                .collect(Collectors.joining("\n"));

        System.out.println("Все возможные конфигурации: \n" + serializedComputers);
    }

    public App() {  // подключаем зависимости
        Context context = Context.getInstance();
        cpus = context.getCpus();
        gpus = context.getGpus();
        motherboards = context.getMotherboards();
        computerSerializer = context.getComputerSerializer();
        executor = context.getExecutor();
    }

    public static void main(String[] args) {
        new App().run();
    }
}
