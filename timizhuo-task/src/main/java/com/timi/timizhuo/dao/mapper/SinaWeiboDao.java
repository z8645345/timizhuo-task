package com.timi.timizhuo.dao.mapper;

import com.timi.timizhuo.dao.model.SinaWeiboModel;

/**
 * 卓依婷新浪微博dao
 * @author 曾佳
 */
public interface SinaWeiboDao {

	/**
	 * 插入一条微博信息
	 * @param sinaWeiboModel
	 */
	public void insert(SinaWeiboModel sinaWeiboModel);

	/**
	 * 更新发文时间查询最新的一条微博信息
	 * @return
	 */
	public SinaWeiboModel findNewestWeibo();
}
