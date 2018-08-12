package com.timi.timizhuo.weibo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.timi.timizhuo.dao.mapper.SinaWeiboDao;
import com.timi.timizhuo.dao.model.SinaWeiboModel;
import com.timi.timizhuo.feige.SendWechatMsg;
import com.timi.timizhuo.util.WeiboUtils;

/**
 * 卓依婷微博处理类
 * @author 曾佳
 */
@Service
public class WeiboHandle {
	
	private static Logger logger = LoggerFactory.getLogger(WeiboHandle.class);
	
	@Autowired
	private SinaWeiboDao sinaWeiboDao;

	/**
	 * 根据接口返回的json获得卓依婷新浪微博数据库模型类
	 * @param json
	 * @return
	 * @throws Exception 
	 */
	private SinaWeiboModel getSinaWeiboModel(JSONObject json) throws Exception {
		JSONArray weiboJsonArray = json.getJSONArray("data");
		JSONObject firstData  = weiboJsonArray.getJSONObject(0);
		SinaWeiboModel sinaWeiboModel = new SinaWeiboModel();
		String pDate = firstData.getString("pDate");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sinaWeiboModel.setCreatedAt(df.parse(pDate));
		sinaWeiboModel.setWeiboText(firstData.getString("content"));
		sinaWeiboModel.setOriginalTextUrl(firstData.getString("url"));
		JSONArray imageURLsJsonArray = firstData.getJSONArray("imageURLs");
		if (imageURLsJsonArray != null && imageURLsJsonArray.size() > 0) {
			String originalPics = "";
			for(Object object : imageURLsJsonArray) {
				originalPics += object.toString() + ";";
			}
			sinaWeiboModel.setOriginalPics(originalPics.substring(0, originalPics.length() - 1));
		}
		JSONArray videoURLsJsonArray = firstData.getJSONArray("videoURLs");
		if (videoURLsJsonArray != null && videoURLsJsonArray.size() > 0) {
			String weiboVedio = "";
			for(Object object : videoURLsJsonArray) {
				weiboVedio += object.toString() + ";";
			}
			sinaWeiboModel.setWeiboVedio(weiboVedio.substring(0, weiboVedio.length() - 1));
		}
		JSONObject fromJSONObject = firstData.getJSONObject("from");
		JSONObject extendJSONObject = fromJSONObject.getJSONObject("extend");
		sinaWeiboModel.setTimiPic(extendJSONObject.getString("avatar_large"));
		return sinaWeiboModel;
	}
	
	/**
	 * 卓依婷最新微博处理
	 */
	public void timiWeiboDataHandle() {
		String url = "http://api01.bitspaceman.com:8000/post/weibo?apikey=EFoPstgagLRjbS786RbROmuFVau9XZDcoJ1uN2H3UxOADz7EzzjWVMypWKbTsP5x&uid=1254461195";
		try {
			JSONObject json = WeiboUtils.getRequestFromUrl(url);
			SinaWeiboModel sinaWeiboModel = this.getSinaWeiboModel(json);
			Date newestWeiboCreateAt = getNewestWeiboCreateAt();
			if (isUpdateWeibo(sinaWeiboModel, newestWeiboCreateAt)) {
				// 2.有更新微博微信发出通知
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String updateTime = df.format(sinaWeiboModel.getCreatedAt());
				SendWechatMsg.sendMsg("卓依婷微博更新通知", updateTime + " 依婷发布新微博啦，原文地址：" + sinaWeiboModel.getOriginalTextUrl(), sinaWeiboModel.getWeiboText());
				// 3.将更新的微博信息同步至数据库
				sinaWeiboDao.insert(sinaWeiboModel);
			}
		} catch (Exception e) {
			logger.error("m:timiWeiboDateHandle 同步微博信息失败", e);
		}
		
	}
	
	/**
	 * 获取数据库最新一条微博发文时间
	 * @return
	 */
	private Date getNewestWeiboCreateAt() {
		SinaWeiboModel sinaWeiboModel = sinaWeiboDao.findNewestWeibo();
		if (sinaWeiboModel == null) {
			return null;
		}
		return sinaWeiboModel.getCreatedAt();
	}
	
	/**
	 * 判断卓依婷是否更新微博
	 * @param statuses 微博信息列表
	 * @return true：更新了 false：未更新
	 */
	private boolean isUpdateWeibo(SinaWeiboModel sinaWeiboModel, Date newestWeiboCreateAt) {
		if (newestWeiboCreateAt == null) {
			return true;
		}
		if (sinaWeiboModel.getCreatedAt().after(newestWeiboCreateAt)) {
			return true;
		}
		return false;
	}
}
