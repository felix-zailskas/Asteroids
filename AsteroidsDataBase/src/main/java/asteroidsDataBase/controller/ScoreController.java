package asteroidsDataBase.controller;

import asteroidsDataBase.model.Score;
import asteroidsDataBase.repository.ScoreRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Score Mapping Controller
 */
@RestController
public class ScoreController {

    private final ScoreRepository repository;

    /**
     * Create new ScoreController
     * @param repository Score Repository
     */
    public ScoreController(ScoreRepository repository) {
        this.repository = repository;
    }

    /**
     * Get Mapping for all "/scores"
     * @return Score Repository
     */
    @GetMapping("/scores")
    public List<Score> all() {
        return (List<Score>) repository.findAll();
    }

    /**
     * Post Mapping
     * @param newScore New Score
     * @return Add Score to Score Repository
     */
    @PostMapping("/scores")
    Score newScore(@RequestBody Score newScore) {
        return repository.save(newScore);
    }

    /**
     * Update ("Put") Score Mapping
     * @param newScore New Score
     * @param id Score ID
     * @return Save updated Score Object
     */
    @PutMapping("/scores/{id}")
    Score replaceScore(@RequestBody Score newScore, @PathVariable Long id) {
        return repository.findById(id)
                .map(score -> {
                    score.setScore(newScore.getScore());
                    score.setName(newScore.getName());
                    return repository.save(score);
                })
                .orElseGet(() -> {
                    newScore.setId(id);
                    return repository.save(newScore);
                });
    }

    /**
     * Delete Mapping
     * @param id Primary ID
     */
    @DeleteMapping("/scores/{id}")
    void deleteScore(@PathVariable Long id) {
        repository.deleteById(id);
    }

    /**
     * Get Mapping for all Scores by one Player
     * @param player Player ID
     * @return List of Scores with foreign key referencing Player ID of param player
     */
    @GetMapping("/scores/{player}")
    List<Score> userScores(@PathVariable Long player) {
        return repository.findAllByPlayer(player);
    }
}
