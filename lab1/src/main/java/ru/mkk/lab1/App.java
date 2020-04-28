package ru.mkk.lab1;

import ru.mkk.lab1.exceptions.InCompatibleComponentException;
import ru.mkk.lab1.pojo.Component;
import ru.mkk.lab1.pojo.Computer;
import ru.mkk.lab1.serializers.Serializer;
import ru.mkk.lab1.utils.ComputerUtils;

import java.util.List;
import java.util.stream.Collectors;

public class App implements Runnable {

    private final List<Component> cpus;
    private final List<Component> gpus;
    private final List<Component> motherboards;
    private final Serializer<Computer> computerSerializer;

    @Override
    public void run() {
        try {
            System.out.println(computerSerializer.serialize(
                    Computer.newBuilder()
                            .setMotherboard(motherboards.get(0))
                            .setCpu(cpus.get(1))
                            .setGpu(gpus.get(0))
                            .build()
            ));
        } catch (InCompatibleComponentException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(computerSerializer.serialize(
                    Computer.newBuilder()
                            .setMotherboard(motherboards.get(0))
                            .setCpu(cpus.get(0))
                            .setGpu(gpus.get(0))
                            .build()
            ));
        } catch (InCompatibleComponentException e) {
            e.printStackTrace();
        }

        String serializedComputers = ComputerUtils.getAllComputersByComponents(cpus, gpus, motherboards).stream()
                .map(computerSerializer::serialize)
                .collect(Collectors.joining("\n"));

        System.out.println("Р’СЃРµ РІРѕР·РјРѕР¶РЅС‹Рµ РєРѕРЅС„РёРіСѓСЂР°С†РёРё: \n" + serializedComputers);
    }

    public App() {  // подключаем зависимости
        Context context = Context.getInstance();
        cpus = context.getCpus();
        gpus = context.getGpus();
        motherboards = context.getMotherboards();
        computerSerializer = context.getComputerSerializer();
    }

    public static void main(String[] args) {
        new App().run();
    }
}
