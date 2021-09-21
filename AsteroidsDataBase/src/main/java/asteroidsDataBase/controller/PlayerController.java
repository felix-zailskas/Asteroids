package asteroidsDataBase.controller;

import asteroidsDataBase.model.Player;
import asteroidsDataBase.repository.PlayerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Player Mapping Controller
 */
@RestController
public class PlayerController {

    private final PlayerRepository repository;

    /**
     * Creating PlayerController
     * @param repository PlayerRepository
     */
    public PlayerController(PlayerRepository repository) {
        this.repository = repository;
    }

    /**
     * "/players" Mapping
     * @return All Players
     */
    @GetMapping("/players")
    public List<Player> all() {
        return (List<Player>) repository.findAll();
    }

    /**
     * Specific "name" Mapping
     * @param name Username
     * @return Player with that username
     */
    @GetMapping("players/{name}")
    Player getPlayerByName(@PathVariable String name) {
        return repository.findAllByName(name);
    }

    /**
     * Post Mapping
     * @param newPlayer Player
     * @return Player Repository
     */
    @PostMapping("/players")
    Player newPlayer(@RequestBody Player newPlayer) {
        return repository.save(newPlayer);
    }

    /**
     * Put Mapping
     * @param newPlayer New Player
     * @param id Player ID
     * @return Save updated Player Object in Player Repository
     */
    @PutMapping("/players/{id}")
    Player replacePlayer(@RequestBody Player newPlayer, @PathVariable Long id) {
        return repository.findById(id)
                .map(player -> {
                    player.setName(newPlayer.getName());
                    return repository.save(player);
                })
                .orElseGet(() -> {
                    newPlayer.setId(id);
                    return repository.save(newPlayer);
                });
    }

    /**
     * Delete Mapping
     * @param id Player ID
     */
    @DeleteMapping("/players/{id}")
    void deletePlayer(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
