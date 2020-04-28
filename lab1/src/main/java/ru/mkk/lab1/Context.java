package ru.mkk.lab1;

import lombok.Getter;
import ru.mkk.lab1.factories.ComponentTestFactory;
import ru.mkk.lab1.pojo.Component;
import ru.mkk.lab1.pojo.Computer;
import ru.mkk.lab1.serializers.ComputerSerializer;
import ru.mkk.lab1.serializers.Serializer;

import java.util.List;

// Используем как аналог @Configuration в spring
public class Context {

    private static volatile Context instance;

    @Getter  // используем как аналог @Bean в spring
    private final List<Component> gpus;

    @Getter  // используем как аналог @Bean в spring
    private final List<Component> cpus;

    @Getter  // используем как аналог @Bean в spring
    private final List<Component> motherboards;

    @Getter  // используем как аналог @Bean в spring
    private final Serializer<Computer> computerSerializer;

    private Context() {
        ComponentTestFactory testFactory = new ComponentTestFactory();
        gpus = testFactory.getGpus();
        cpus = testFactory.getCpus();
        motherboards = testFactory.getMotherboards();
        computerSerializer = new ComputerSerializer();
    }

    public static Context getInstance() {
        Context localInstance = instance;
        if (instance == null) {
            synchronized (Context.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Context();
                }
            }
        }
        return localInstance;
    }

}
