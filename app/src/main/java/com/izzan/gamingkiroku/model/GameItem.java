package com.izzan.gamingkiroku.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Aizen on 7 Jun 2017.
 */

@Table(name = "GameItems")
public class GameItem extends Model{

    // This is the unique id given by the server
    @Column(name = "game_item_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private long gameItemId;

    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    private String genre;

    @Column(name = "sub_genre")
    private String subGenre;

    @Column(name = "platform")
    private String platform;

    @Column(name = "finished")
    private boolean finished;

    @Column(name = "rating")
    private float rating;

    public GameItem() {
        super();
    }

    public GameItem(long gameItemId, String title, String genre, String subGenre, String platform,
    boolean finished, float rating) {
        super();
        this.gameItemId = gameItemId;
        this.title = title;
        this.genre = genre;
        this.subGenre = subGenre;
        this.platform = platform;
        this.finished = finished;
        this.rating = rating;
    }

    public GameItem(String title, String genre, String subGenre, String platform,
                    boolean finished, float rating) {
        super();
        this.title = title;
        this.genre = genre;
        this.subGenre = subGenre;
        this.platform = platform;
        this.finished = finished;
        this.rating = rating;
    }

    public long getGameItemId() {
        return gameItemId;
    }

    public void setGameItemId(long gameItemId) {
        this.gameItemId = gameItemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSubGenre() {
        return subGenre;
    }

    public void setSubGenre(String subGenre) {
        this.subGenre = subGenre;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public static List<GameItem> getAll() {
        // This is how you execute a query
        return new Select()
                .from(GameItem.class)
                .orderBy("Name ASC")
                .execute();
    }
}
