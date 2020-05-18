package ru.mkk.lab2;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.mkk.lab2.exceptions.InCompatibleComponentException;
import ru.mkk.lab2.factories.ComponentTestFactory;
import ru.mkk.lab2.pojo.Component;
import ru.mkk.lab2.pojo.Computer;
import ru.mkk.lab2.serializers.Serializer;
import ru.mkk.lab2.utils.ComputerUtils;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

// создает конструктор в который инжектятся зависимости, можно было бы через @AutoWired, но так короче и удобнее
@RequiredArgsConstructor
@SpringBootApplication
public class Lab2Application implements CommandLineRunner {

    private final ComponentTestFactory testFactory;
    private final Serializer<Computer> computerSerializer;
    private final Logger logger = Logger.getGlobal();

    public static void main(String[] args) {
        SpringApplication.run(Lab2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        printLogo();
        List<Component> gpus = testFactory.getGpus();
        List<Component> cpus = testFactory.getCpus();
        List<Component> motherboards = testFactory.getMotherboards();
        try {
            System.out.println(computerSerializer.serialize(
                    Computer.newBuilder()
                            .setComponent(motherboards.get(0))
                            .setComponent(cpus.get(1))
                            .setComponent(gpus.get(0))
                            .build()
            ));
        } catch (InCompatibleComponentException e) {
            logger.warning(e.getMessage());
        }

        try {
            System.out.println(computerSerializer.serialize(
                    Computer.newBuilder()
                            .setComponent(motherboards.get(0))
                            .setComponent(cpus.get(0))
                            .setComponent(gpus.get(0))
                            .build()
            ));
        } catch (InCompatibleComponentException e) {
            e.printStackTrace();
        }

        String serializedComputers = ComputerUtils.getAllComputersByComponents(cpus, gpus, motherboards).stream()
                .map(computerSerializer::serialize)
                .collect(Collectors.joining("\n"));

        System.out.println("Все возможные конфигурации: \n" + serializedComputers);
    }

    private void printLogo() {
        System.out.println(" __    __     __  __     __  __    \n" +
                "/\\ \"-./  \\   /\\ \\/ /    /\\ \\/ /    \n" +
                "\\ \\ \\-./\\ \\  \\ \\  _\"-.  \\ \\  _\"-.  \n" +
                " \\ \\_\\ \\ \\_\\  \\ \\_\\ \\_\\  \\ \\_\\ \\_\\ \n" +
                "  \\/_/  \\/_/   \\/_/\\/_/   \\/_/\\/_/ \n");
    }
}
