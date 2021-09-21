package asteroidsDataBase.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Create Player object and Entity
 */
@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //Primary Key

    private String name;

    /**
     * Empty Constructor
     */
    public Player() {
    }

    /**
     * Create new Player Object
     * @param id Primary Key
     * @param name Username
     */
    public Player(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * getID()
     * @return ID
     */
    public Long getId() {
        return this.id;
    }

    /**
     * setID()
     * @param id ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * getName()
     * @return Name
     */
    public String getName() {
        return this.name;
    }

    /**
     * setName()
     * @param name Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Equals Method
     * @param o Object
     * @return Boolean if two Player Objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return Objects.equals(this.id, player.id) && Objects.equals(this.name, player.name);
    }

    /**
     * HashCode
     * @return Hashed value of Score Player Object
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }

    /**
     * To String
     * @return String representation of Player Object
     */
    @Override
    public String toString() {
        return "Player{" + "id=" + this.id + ", name='" + this.name + '\'' + '}';
    }
}