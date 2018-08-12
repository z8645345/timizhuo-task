package com.timi.timizhuo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.timi.timizhuo.dao.model.SinaWeiboModel;

public class WeiboUtils {

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject postRequestFromUrl(String url, String body) throws IOException, JSONException {
		URL realUrl = new URL(url);
		URLConnection conn = realUrl.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		PrintWriter out = new PrintWriter(conn.getOutputStream());
		out.print(body);
		out.flush();

		InputStream instream = conn.getInputStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(instream, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = JSONObject.parseObject(jsonText);
			return json;
		} finally {
			instream.close();
		}
	}

	public static JSONObject getRequestFromUrl(String url) throws IOException, JSONException {
		URL realUrl = new URL(url);
		URLConnection conn = realUrl.openConnection();
		InputStream instream = conn.getInputStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(instream, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = JSONObject.parseObject(jsonText);
			return json;
		} finally {
			instream.close();
		}
	}

	public static void main(String[] args) throws Exception {

		// 请求示例 url 默认请求参数已经做URL编码
		String url = "http://api01.bitspaceman.com:8000/post/weibo?apikey=EFoPstgagLRjbS786RbROmuFVau9XZDcoJ1uN2H3UxOADz7EzzjWVMypWKbTsP5x&uid=1254461195";
		JSONObject json = getRequestFromUrl(url);
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
	}
}
