package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {
    /**
     * 根据线路ID 和 用户ID查询收藏
     * @param rid 线路ID
     * @param uid 用户ID
     * @return 结果
     */
    Favorite findByRidAndUid(int rid, int uid);

    /**
     * 根据线路ID 查询收藏的次数
     * @param rid 线路id
     * @return 次数
     */
    int countFavoriteByRid(int rid);

    /**
     * 添加收藏
     * @param rid 线路id
     * @param uid 用户id
     */
    void add(int rid, int uid);
}
