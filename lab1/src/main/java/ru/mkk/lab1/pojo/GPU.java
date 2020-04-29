package ru.mkk.lab1.pojo;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Value
class GPU extends Component {
    protected GPU(String name, String manufacture, List<Component> incompatible) {
        super(name, manufacture, incompatible);
    }
}
