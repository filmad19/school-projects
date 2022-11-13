package at.kaindorf.lernen_player.io;

import at.kaindorf.lernen_player.pojos.Player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class IO_Access {
    private static final Path path = Paths.get("C:\\Users\\matze\\IdeaProjects\\4. Klasse\\lernen_player\\src\\main\\resources\\players.csv");

    public static List<Player> getPlayersFromCsv() throws IOException {
        return Files.readAllLines(path).stream()
                .skip(1)
                .map(Player::new)
                .collect(Collectors.toList());
    }
}
