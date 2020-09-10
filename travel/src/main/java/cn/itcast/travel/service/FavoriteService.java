package cn.itcast.travel.service;

public interface FavoriteService {
    /**
     * 判断是否收藏
     * @param rid 线路id
     * @param uid 用户id
     * @return 结果
     */
    boolean isFavorite(int rid ,int uid);

    /**
     * 添加收藏
     * @param rid 线路id
     * @param uid 用户id
     */
    void add(int rid, int uid);
}
