package com.su.chinaso.suchef.dish;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by chinaso on 2017/4/11.
 */

@Entity
public class CommentEntity {
    @NotNull
    private int id;
    @NotNull
    private int dishId;
    @NotNull
    private String comment;
    private String date;

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getDishId() {
        return this.dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Keep()
    public CommentEntity() {
    }

    @Generated(hash = 1245068005)
    public CommentEntity(int id, int dishId, @NotNull String comment, String date) {
        this.id = id;
        this.dishId = dishId;
        this.comment = comment;
        this.date = date;
    }
}
