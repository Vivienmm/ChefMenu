package com.su.chinaso.suchef.dish;

import android.widget.Toast;

import com.su.chinaso.suchef.SuApp;
import com.su.greendaodemo.greendao.gen.DishEntityDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chinaso on 2017/3/20.
 */

public class DishManageDao {

    private List<DishEntity> dishes = new ArrayList<DishEntity>();

    public DishManageDao() {
        dishes = GreenDaoManager.getInstance().getSession().getDishEntityDao().queryBuilder().list();
    }

    public List<DishEntity> getDishes() {
        return dishes;
    }

    /**
     * 根据名字更新某条数据的名字
     *
     * @param prevName 原名字
     * @param newName  新名字
     */
    public void updateDish(String prevName, String newName) {
        DishEntity findUser = GreenDaoManager.getInstance().getSession().getDishEntityDao().queryBuilder()
                .where(DishEntityDao.Properties.Name.eq(prevName)).build().unique();
        if (findUser != null) {
            findUser.setName(newName);
            GreenDaoManager.getInstance().getSession().getDishEntityDao().update(findUser);
            Toast.makeText(SuApp.getContext(), "修改成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(SuApp.getContext(), "用户不存在", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 根据名字删除某用户
     *
     * @param dish
     */
    public void deleteDish(DishEntity dish) {
        DishEntityDao userDao = GreenDaoManager.getInstance().getSession().getDishEntityDao();
        DishEntity findUser = userDao.queryBuilder().where(DishEntityDao.Properties.Name.eq(dish.getName())).build().unique();
        if (findUser != null) {
            userDao.delete(dish);
        }
    }

    /**
     * 本地数据里添加一个User
     *
     * @param name 名字
     */
    public void insertDish(String name, String price, String path, String ingredient) {
        dishes = GreenDaoManager.getInstance().getSession().getDishEntityDao().queryBuilder().list();
        DishEntityDao userDao = GreenDaoManager.getInstance().getSession().getDishEntityDao();

        DishEntity user = new DishEntity(dishes.size(), name, price, path, ingredient);
        userDao.insert(user);
    }
}
