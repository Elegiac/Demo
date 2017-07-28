package std.demo.local.httpClient;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import std.demo.AESUtil;

/**
 * http请求
 * 
 * @author zy
 *
 */
public class HttpUtil {
	private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

	/**
	 * http请求类型
	 * 
	 * @author zy
	 *
	 */
	private enum HttpMethod {
		GET, POST
	}

	/**
	 * http响应实体
	 * 
	 * @author zy
	 *
	 */
	public static class HttpRep {
		private Integer statusCode;
		private String content;

		public Integer getStatusCode() {
			return statusCode;
		}

		public void setStatusCode(Integer statusCode) {
			this.statusCode = statusCode;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		@Override
		public String toString() {
			return "HttpRep [statusCode=" + statusCode + ", content=" + content + "]";
		}
	}

	/**
	 * 执行post请求
	 * 
	 * @param url
	 * @return
	 */
	public static HttpRep doPost(String url) {
		return doPost(null, url);
	}

	/**
	 * 执行post请求
	 * 
	 * @param messageBody
	 * @param url
	 * @return
	 */
	public static HttpRep doPost(String messageBody, String url) {
		return doHttp(HttpMethod.POST, url, messageBody);
	}

	/**
	 * 执行get请求
	 * 
	 * @param url
	 * @return
	 */
	public static HttpRep doGet(String url) {
		return doHttp(HttpMethod.GET, url, null);
	}

	private static HttpRep doHttp(HttpMethod method, String url, String messageBody) {
		HttpRep rep = new HttpRep();
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResponse = null;
		HttpRequestBase request = null;
		try {
			httpClient = HttpClients.createDefault();
			if (method == HttpMethod.POST) {
				request = new HttpPost(url);

				if (messageBody != null && !"".equals(messageBody.trim())) {
					StringEntity stringEntity = new StringEntity(messageBody, "UTF-8");
					((HttpPost) request).setEntity(stringEntity);
				}
			} else if (method == HttpMethod.GET) {
				request = new HttpGet(url);
			} else {
				throw new IllegalArgumentException("method not supported");
			}

			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
			// 设置请求和传输超时时间
			request.setConfig(requestConfig);

			httpResponse = httpClient.execute(request);

			// 获取响应码
			StatusLine sl = httpResponse.getStatusLine();

			int statusCode = sl.getStatusCode();

			rep.setStatusCode(statusCode);

			HttpEntity entity = httpResponse.getEntity();

			if (entity != null) {

				rep.setContent(EntityUtils.toString(entity, "utf-8"));

				EntityUtils.consumeQuietly(entity);
			}
			return rep;
		} catch (Exception e) {
			log.error("http请求发送失败", e);
			return null;
		} finally {
			try {
				if (httpResponse != null)
					httpResponse.close();
				if (httpClient != null)
					httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws ClientProtocolException, IOException {

		String key = "make it happened";

		String s = "sign=4489F7231F6CD4DEF6F2B7FD8D2305F0&timestamp=1500455930747&v=1.0&categorys=005007&appId=app_gkt_lookerplus_5714&isSex=0&method=content.wallpaper.recommand.list&imsi=460026408885439&format=json&group=61&deviceId=861022004611283&appSecret=2b45451eb109";

		s = "sign=83258A0E6D1BA255F5F6E743A141ABC4&timestamp=1500455930906&parentId=005007&v=1.0&appId=app_gkt_lookerplus_5714&method=content.category.child.list&format=json&appSecret=2b45451eb109";
		s = "sign=86205AD2C32B67F753F3A0D7F57A9278&timestamp=1500455930863&v=1.0&appId=app_gkt_lookerplus_5714&isSex=0&pageNo=0&categoryId=005007&pageSize=1000&method=content.wallpaper.mostpopular.bycategory&imsi=460026408885439&format=json&deviceId=861022004611283&appSecret=2b45451eb109";
		s = "sign=F853324F59EAA5549903B969AF0FAAA0&timestamp=1500456300161&operation=plus&v=1.0&appId=app_gkt_lookerplus_5714&method=content.content2user.save&imsi=460026408885439&format=json&type=W&contentId=f4e324dc-fd7b-4b7e-af96-0034ac46e170&deviceId=861022004611283&appSecret=2b45451eb109";
		s = "sign=DCE2C9803B11A810D2E4327CED19DD02&timestamp=1500456320293&v=1.0&appId=app_gkt_lookerplus_5714&pageNo=0&pageSize=1000&method=content.content2user.list&imsi=460026408885439&format=json&type=W&deviceId=861022004611283&appSecret=2b45451eb109";

		StringBuilder builder = new StringBuilder();

		builder.append("{");

		String[] parts = s.split("&");

		for (String part : parts) {
			String[] arg = part.split("=");
			builder.append("\"").append(arg[0]).append("\":\"").append(arg[1]).append("\",");

		}

		builder.deleteCharAt(builder.length() - 1);
		builder.append("}");

		ObjectMapper objectMapper = new ObjectMapper();

		String encryptStr = AESUtil.encrypt(builder.toString(), key);

		String result = doPost(encryptStr, "http://172.30.120.57:8888/content/app/dispatch/encrypt").getContent();

		Map<String, String> map = objectMapper.readValue(result, new TypeReference<Map<String, String>>() {
		});

		if ("0".equals(map.get("code"))) {
			String data = map.get("data");
			if (data != null)
				System.out.println(AESUtil.decrypt(map.get("data"), key));
		}

	}
}
