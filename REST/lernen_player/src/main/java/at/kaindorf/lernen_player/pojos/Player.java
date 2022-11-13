package at.kaindorf.lernen_player.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode (exclude = {"id"})
public class Player {
    private long id;
    private String name;
    private String team;
    private int age;

    public Player(String line) {
        String[] token = line.split(";");

        this.id = Long.parseLong(token[0]);
        this.name = token[1];
        this.team = token[2];
        this.age = Integer.parseInt(token[3]);
    }
}
