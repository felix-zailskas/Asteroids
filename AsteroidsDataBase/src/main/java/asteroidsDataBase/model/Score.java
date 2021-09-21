package asteroidsDataBase.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Create Score object and Entity
 */
@Entity
@Table(name = "scores")
public class Score {

    // Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long player;
    private Integer score;
    private String name;

    /**
     * Empty Constructor
     */
    public Score() {
    }

    /**
     * Score constructor
     * @param id Primary key
     * @param score Score value
     * @param player Player ID
     * @param name userName
     */
    public Score(Long id, Integer score, Long player, String name) {
        this.id = id;
        this.score = score;
        this.player = player;
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
     * @param id Primary key
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * getScore()
     * @return Score value
     */
    public Integer getScore() {
        return this.score;
    }

    /**
     * setScore()
     * @param score Value
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * getPlayer()
     * @return Player ID
     */
    public Long getPlayer() {
        return this.player;
    }

    /**
     * setPlayer()
     * @param player ID
     */
    public void setPlayer(Long player) {
        this.player = player;
    }

    /**
     * getName()
     * @return UserName
     */
    public String getName() { return this.name; }

    /**
     * setName()
     * @param name set UserName
     */
    public void setName(String name) { this.name = name; }

    /**
     * Equals Method
     * @param o Object
     * @return Boolean if two Score Objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Score)) return false;
        Score score = (Score) o;
        return Objects.equals(this.id, score.id) && Objects.equals(this.score, score.score)
                && Objects.equals(this.player, score.player) && Objects.equals(this.name, score.name);
    }

    /**
     * HashCode function
     * @return Hashed value of Score Object
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.score, this.player, this.name);
    }

    /**
     * To String
     * @return String representation of Score Object
     */
    @Override
    public String toString() {
        return "Score{" + "id=" + this.id + ", player='" + this.player + '\'' + ", name='" + this.name + '\''
                + ", score='" + this.score + '\'' + '}';
    }
}