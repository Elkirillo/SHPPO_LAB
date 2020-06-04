package ru.mkk.lab1.pojo;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import ru.mkk.lab1.exceptions.InCompatibleComponentException;

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
        private Motherboard motherboard;
        private CPU cpu;
        private GPU gpu;

        private Builder() {
        }

        public Builder setComponent(Component component) throws InCompatibleComponentException {
            if (component instanceof Motherboard)
                setMotherboard((Motherboard) component);
            else if (component instanceof CPU)
                setCpu((CPU) component);
            else if (component instanceof GPU)
                setGpu((GPU) component);
            else
                throw new InCompatibleComponentException();
            return this;
        }

        private void setMotherboard(Motherboard val) throws InCompatibleComponentException {
            if (cpu != null)
                isCompatible(val, cpu);
            if (gpu != null)
                isCompatible(val, gpu);
            motherboard = val;
        }

        private void setCpu(CPU val) throws InCompatibleComponentException {
            if (motherboard != null)
                isCompatible(val, motherboard);
            if (gpu != null)
                isCompatible(val, gpu);
            cpu = val;
        }

        private void setGpu(GPU val) throws InCompatibleComponentException {
            if (cpu != null)
                isCompatible(val, cpu);
            if (motherboard != null)
                isCompatible(val, motherboard);
            gpu = val;
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
