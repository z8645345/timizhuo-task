package com.timi.timizhuo.feige;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.timi.timizhuo.weibo.WeiboHandle;

/**
 * 发送飞鸽快信
 * @author 曾佳
 */
public class SendWechatMsg {
	
	private static Logger logger = LoggerFactory.getLogger(SendWechatMsg.class);
	
	public static void sendMsg(String title,String content,String remark) throws Exception {
        // 创建一个httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建一个post对象
        HttpPost post = new HttpPost("http://u.ifeige.cn/api/send_message");
        // 创建一个Entity，模拟表单数据
        List<NameValuePair> formList = new ArrayList<NameValuePair>();
        // 添加表单数据  
        formList.add(new BasicNameValuePair("secret", "6e3510beabe2279d8d17e720837fd10a"));
        formList.add(new BasicNameValuePair("token", "1a75db7b11a1d0014785f457f225606a"));
        formList.add(new BasicNameValuePair("key", "notice"));
        formList.add(new BasicNameValuePair("title", title));
        formList.add(new BasicNameValuePair("content", content));
        formList.add(new BasicNameValuePair("remark", remark));
//        formList.add(new BasicNameValuePair("time", "time()"));
        
        // 包装成一个Entity对象
        StringEntity entity = new UrlEncodedFormEntity(formList, "utf-8");
        // 设置请求的内容
        post.setEntity(entity);
        // 设置请求的报文头部的编码
        // post.setHeader(new BasicHeader("Content-Type",
        // "application/x-www-form-urlencoded; charset=utf-8"));
        // 设置期望服务端返回的编码
        // post.setHeader(new BasicHeader("Accept",
        // "text/plain;charset=utf-8"));
        // 执行post请求
        CloseableHttpResponse response = client.execute(post);
        // 获取响应码
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            // 获取数据
            String resStr = EntityUtils.toString(response.getEntity());
            logger.info("m: sendMsg 推送成功, " + resStr);
        } else {
        	logger.info("m: sendMsg 推送失败,推送状态" + statusCode);
        }
    }
	
	public static void main(String[] args) {
		try {
			SendWechatMsg.sendMsg("卓依婷微博更新通知", "卓依婷微博更新啦！赶快去评论吧！ ​​​", "有人说她也有跟我一样的帽子，要一起戴一起拍照[哈哈]");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
