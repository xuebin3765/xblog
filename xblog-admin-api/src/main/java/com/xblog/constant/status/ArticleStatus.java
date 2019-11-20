package com.xblog.constant.status;

/**
 * 文章状态
 * Created by Administrator on 2017/10/12.
 */
public interface  ArticleStatus {
    public int cancel = -1;       // 软删除
    public int all = 0;
    public int sketch = 1 ;       // 草稿
    public int update = 2 ;       // 修改，也属于草稿
    public int audit = 3 ;        // 审核
    public int shield = 4 ;       // 屏蔽
    public int publish = 5;       // 发布
}
