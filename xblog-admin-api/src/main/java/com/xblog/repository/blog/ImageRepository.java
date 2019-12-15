package com.xblog.repository.blog;


import com.xblog.entity.blog.Article;
import com.xblog.entity.blog.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 文章
 * Created by lovebin on 2017/4/19.
 */

public interface ImageRepository extends JpaRepository<Image, String> , JpaSpecificationExecutor<Article>{

//    /**
//     * 更新文章状态
//     * @param id
//     * @param status
//     * @return
//     */
//    @Modifying
//    @Query(value = " update Article set state=?2 where id=?1 ")
//    Article updateStatus(Integer id, int status);

    List<Image> findAllByUrl(String url);



}
