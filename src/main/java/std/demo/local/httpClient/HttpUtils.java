package std.demo.local.httpClient;

import java.io.IOException;
import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
	/**
	 * HTTP响应实体
	 * 
	 * @author yeahmobi
	 *
	 */
	public static class HttpResponseEntity {
		private int statusCode;
		private String responseContent;

		public int getStatusCode() {
			return statusCode;
		}

		public void setStatusCode(int statusCode) {
			this.statusCode = statusCode;
		}

		public String getResponseContent() {
			return responseContent;
		}

		public void setResponseContent(String responseContent) {
			this.responseContent = responseContent;
		}

		@Override
		public String toString() {
			return "HttpResponseEntity [statusCode=" + statusCode + ", responseContent=" + responseContent + "]";
		}

	}

	/**
	 * 执行HTTP请求
	 * 
	 * @param url
	 * @param httpMethod
	 * @param messageBody
	 * @param headers
	 * @return
	 */
	public static HttpResponseEntity doHttp(String url, Class<? extends HttpRequestBase> httpMethod, String messageBody,
			Header... headers) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResponse = null;
		try {
			httpClient = HttpClients.createDefault();

			HttpRequestBase httpRequest = httpMethod.newInstance();

			httpRequest.setURI(new URI(url));

			if (httpRequest instanceof HttpEntityEnclosingRequestBase && messageBody != null) {
				((HttpEntityEnclosingRequestBase) httpRequest).setEntity(new StringEntity(messageBody, "UTF-8"));
			}

			httpRequest.setHeaders(headers);

			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000)
					.build();
			// 设置请求和传输超时时间
			httpRequest.setConfig(requestConfig);

			httpResponse = httpClient.execute(httpRequest);

			int statusCode = httpResponse.getStatusLine().getStatusCode();

			HttpEntity entity = httpResponse.getEntity();

			String resultMessage = null;

			if (entity != null) {
				resultMessage = EntityUtils.toString(entity, "utf-8");
			}

			EntityUtils.consumeQuietly(entity);

			HttpResponseEntity response = new HttpResponseEntity();

			response.setStatusCode(statusCode);
			response.setResponseContent(resultMessage);

			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (httpResponse != null) {
					httpResponse.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
			}
		}
	}
	
	public static void main(String[] args) {
		String url = "http://api.mobgkt.com/admin/login.html";

		System.err.println(doHttp(url, HttpOptions.class, null));
	}
}
