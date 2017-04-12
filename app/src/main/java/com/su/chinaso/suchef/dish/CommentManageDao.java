package com.su.chinaso.suchef.dish;

import com.su.greendaodemo.greendao.gen.CommentEntityDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chinaso on 2017/4/11.
 */

public class CommentManageDao {
    private List<CommentEntity> allComments = new ArrayList<>();

    public CommentManageDao() {
        allComments = GreenDaoManager.getInstance().getSession().getCommentEntityDao().queryBuilder().build().list();
    }

    public List<CommentEntity> getDishComment(int dishId) {
        CommentEntityDao channelDao = GreenDaoManager.getInstance().getSession().getCommentEntityDao();
        List<CommentEntity> listComments = channelDao.queryBuilder().where(CommentEntityDao.Properties.DishId.eq(dishId)).build().list();

        return listComments;
    }

    public void insertComment(int dishId, String comment,String date) {
        allComments = GreenDaoManager.getInstance().getSession().getCommentEntityDao().queryBuilder().list();
        CommentEntityDao commentDao = GreenDaoManager.getInstance().getSession().getCommentEntityDao();

        CommentEntity commentItem = new CommentEntity(allComments.size(), dishId, comment,date);
        commentDao.insert(commentItem);
    }

}
