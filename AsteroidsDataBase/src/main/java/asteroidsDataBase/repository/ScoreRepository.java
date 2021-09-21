package asteroidsDataBase.repository;

import asteroidsDataBase.model.Score;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Score Repository -> Data Table for keeping Score Objects
 */
@Repository
public interface ScoreRepository extends CrudRepository<Score, Long> {

    /**
     * Return all Score Objects made by a specific
     * @param player Player ID
     */
    List<Score> findAllByPlayer(Long player);
}