package ru.mkk.lab1.serializers;

import lombok.NonNull;
import ru.mkk.lab1.pojo.Component;
import ru.mkk.lab1.pojo.Computer;

public class ComputerSerializer implements Serializer<Computer> {
    @Override
    public String serialize(@NonNull Computer object) {
        StringBuilder builder = new StringBuilder().append("Computer(");
        Component motherboard = object.getMotherboard();
        Component cpu = object.getCpu();
        Component gpu = object.getGpu();
        if (motherboard != null) {
            builder.append(serializeComponent(motherboard)).append(" + ");
        }
        if (cpu != null) {
            builder.append(serializeComponent(cpu)).append(" + ");
        }
        if (gpu != null) {
            builder.append(serializeComponent(gpu));
        }
        return builder.append(')').toString();
    }

    private String serializeComponent(Component component) {
        return String.format("[%s %s]", component.getManufacture(), component.getName());
    }
}
