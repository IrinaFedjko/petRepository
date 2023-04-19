package owner;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor //will ot work if id is final, if final must be in constructor

public class Owner {
    private Integer id;
    private String ownerName;
    private Integer age;
    private String email;
    private Timestamp created;
    private Timestamp updatedAt;



}
