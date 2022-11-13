package at.kaindorf.lernen_player.database;

import at.kaindorf.lernen_player.io.IO_Access;
import at.kaindorf.lernen_player.pojos.Player;
import jakarta.ws.rs.NotFoundException;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class PlayerMockDB {
    private List<Player> playerList;

//    SINGLETON
    private static PlayerMockDB instance;

    private PlayerMockDB(){
        try {
            playerList = IO_Access.getPlayersFromCsv();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static PlayerMockDB getInstance(){
        if(instance == null){
            instance = new PlayerMockDB();
        }

        return instance;
    }
//    ////////////////

    public List<Player> getAllPlayers(){
        return playerList;
    }

    public Optional<Player> getPlayerById(long id){
        Optional<Player> playerOptional = playerList.stream()
                .filter(p -> p.getId() == id)
                .findFirst();

        if(!playerOptional.isPresent()){
            throw new NotFoundException();
        }

        return playerOptional;
    }

    public Optional<Player> addPlayer(Player player){
        if(playerList.contains(player)){
            throw new KeyAlreadyExistsException();
        }

        long nextId = playerList.stream()
                .mapToLong(Player::getId)
                .max()
                .orElse(0) + 1;

        player.setId(nextId);

        playerList.add(player);

        return Optional.of(player);
    }

    public Optional<Player> removePlayer(long id){
        Optional<Player> playerOptional = getPlayerById(id);  // throws NotFound exception
        playerList.remove(playerOptional.get());
        return playerOptional;
    }

    public Optional<Player> updatePlayer(Player player){
        try{// player updated
            Optional<Player> playerOptional = getPlayerById(player.getId());  // throws NotFoundException

            if(playerList.contains(player)){
                throw new KeyAlreadyExistsException();
            }

            playerList.set(playerList.indexOf(playerOptional.get()), player);
            return Optional.of(player);
        } catch (NotFoundException e){ // player neu erstellen
            Optional<Player> playerOptional = addPlayer(player); //throws KeyAlreadyExistsException
            return playerOptional;
        }
    }
}
