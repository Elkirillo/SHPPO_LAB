package ru.mkk.lab1.pojo;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import ru.mkk.lab1.exceptions.InCompatibleComponentException;
import ru.mkk.lab1.exceptions.IncorrectTypeException;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Computer {

    Component motherboard;
    Component cpu;
    Component gpu;

    private Computer(Builder builder) {
        motherboard = builder.motherboard;
        cpu = builder.cpu;
        gpu = builder.gpu;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Component motherboard;
        private Component cpu;
        private Component gpu;

        private Builder() {
        }

        public Builder setMotherboard(Component val) throws InCompatibleComponentException {
            if (val.getType() != Component.Type.MOTHERBOARD)
                throw new IncorrectTypeException();
            if (cpu != null)
                isCompatible(val, cpu);
            if (gpu != null)
                isCompatible(val, gpu);
            motherboard = val;
            return this;
        }

        public Builder setCpu(Component val) throws InCompatibleComponentException {
            if (val.getType() != Component.Type.CPU)
                throw new IncorrectTypeException();
            if (motherboard != null)
                isCompatible(val, motherboard);
            if (gpu != null)
                isCompatible(val, gpu);
            cpu = val;
            return this;
        }

        public Builder setGpu(Component val) throws InCompatibleComponentException {
            if (val.getType() != Component.Type.GPU)
                throw new IncorrectTypeException();
            if (cpu != null)
                isCompatible(val, cpu);
            if (motherboard != null)
                isCompatible(val, motherboard);
            gpu = val;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }

        private void isCompatible(Component component1, Component component2) throws InCompatibleComponentException {
            if (!component1.isCompatible(component2))
                throw new InCompatibleComponentException();
            if (!component2.isCompatible(component1))
                throw new InCompatibleComponentException();
        }
    }
}
