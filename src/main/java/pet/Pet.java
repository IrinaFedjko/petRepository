package pet;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

public class Pet {
    private Integer petTypeId;
    private String petName;
    private String birthDate;
    private Integer weight;
    private Integer ownerId;

}
