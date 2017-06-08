package com.izzan.gamingkiroku;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

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

    public GameItem() {
        super();
    }

    public GameItem(long gameItemId, String title, String genre, String subGenre, String platform) {
        super();
        this.gameItemId = gameItemId;
        this.title = title;
        this.genre = genre;
        this.subGenre = subGenre;
        this.platform = platform;
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
}
