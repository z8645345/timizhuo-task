package com.timi.timizhuo.dao.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 卓依婷新浪微博实体类
 * @author 曾佳
 */
public class SinaWeibo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 微博ID 主键
	 */
	private String weiboId;
	
	/**
	 * 依婷微博头像
	 */
	private String timiPic;
	
	/**
	 * 发文手机
	 */
	private String weiboFrom;
	
	/**
	 * 发布微博的时间
	 */
	private Date createdAt;
	
	/**
	 * 原文地址
	 */
	private String originalTextUrl;
	
	/**
	 * 微博正文
	 */
	private String weiboText;
	
	/**
	 * 微博缩略图
	 */
	private String thumbnailPics;
	
	/**
	 * 中型图片
	 */
	private String bmiddlePics;
	
	/**
	 * 原图
	 */
	private String originalPics;
	
	/**
	 * 微博视频
	 */
	private String weiboVedio;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;

	public String getWeiboId() {
		return weiboId;
	}

	public void setWeiboId(String weiboId) {
		this.weiboId = weiboId;
	}

	public String getTimiPic() {
		return timiPic;
	}

	public void setTimiPic(String timiPic) {
		this.timiPic = timiPic;
	}

	public String getWeiboFrom() {
		return weiboFrom;
	}

	public void setWeiboFrom(String weiboFrom) {
		this.weiboFrom = weiboFrom;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getWeiboText() {
		return weiboText;
	}

	public void setWeiboText(String weiboText) {
		this.weiboText = weiboText;
	}

	public String getThumbnailPics() {
		return thumbnailPics;
	}

	public void setThumbnailPics(String thumbnailPics) {
		this.thumbnailPics = thumbnailPics;
	}

	public String getBmiddlePics() {
		return bmiddlePics;
	}

	public void setBmiddlePics(String bmiddlePics) {
		this.bmiddlePics = bmiddlePics;
	}

	public String getOriginalPics() {
		return originalPics;
	}

	public void setOriginalPics(String originalPics) {
		this.originalPics = originalPics;
	}

	public String getWeiboVedio() {
		return weiboVedio;
	}

	public void setWeiboVedio(String weiboVedio) {
		this.weiboVedio = weiboVedio;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getOriginalTextUrl() {
		return originalTextUrl;
	}

	public void setOriginalTextUrl(String originalTextUrl) {
		this.originalTextUrl = originalTextUrl;
	}
	
}
