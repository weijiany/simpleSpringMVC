package myFrame.project.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sex {

    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Sex{" +
                "name='" + name + '\'' +
                '}';
    }
}
