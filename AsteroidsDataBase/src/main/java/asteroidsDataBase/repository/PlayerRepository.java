package asteroidsDataBase.repository;

import asteroidsDataBase.model.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * Player Repository -> Data Table for keeping Player Objects
 */
@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {

    /**
     * Used to check if a username exists in the database
     * @param name Username
     * @return Player with that username
     */
    Player findAllByName(String name);

}