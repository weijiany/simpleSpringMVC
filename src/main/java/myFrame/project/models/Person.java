package myFrame.project.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Sex sex;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", name=" + sex +
                '}';
    }
}
