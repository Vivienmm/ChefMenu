package com.su.chinaso.suchef.dish;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by chinaso on 2017/3/20.
 */

@Entity
public class DishEntity {
    @NotNull
    private int id;
    @NotNull
    private String name;
    @NotNull
    private String price;
    @NotNull
    private String imgPath;

    private String majorIngredient;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgPath() {
        return this.imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajorIngredient() {
        return this.majorIngredient;
    }

    public void setMajorIngredient(String majorIngredient) {
        this.majorIngredient = majorIngredient;
    }


    @Keep()
    public DishEntity() {
    }

    @Keep()
    public DishEntity(int id, @NotNull String name, @NotNull String price,
                      @NotNull String imgPath, String majorIngredient) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgPath = imgPath;
        this.majorIngredient = majorIngredient;
    }

}
