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

			//RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
			// 设置请求和传输超时时间
			//request.setConfig(requestConfig);

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
		//
		// String s =
		// "sign=4489F7231F6CD4DEF6F2B7FD8D2305F0&timestamp=1500455930747&v=1.0&categorys=005007&appId=app_gkt_lookerplus_5714&isSex=0&method=content.wallpaper.recommand.list&imsi=460026408885439&format=json&group=61&deviceId=861022004611283&appSecret=2b45451eb109";
		//
		// s =
		// "sign=83258A0E6D1BA255F5F6E743A141ABC4&timestamp=1500455930906&parentId=005007&v=1.0&appId=app_gkt_lookerplus_5714&method=content.category.child.list&format=json&appSecret=2b45451eb109";
		// s =
		// "sign=86205AD2C32B67F753F3A0D7F57A9278&timestamp=1500455930863&v=1.0&appId=app_gkt_lookerplus_5714&isSex=0&pageNo=0&categoryId=005007&pageSize=1000&method=content.wallpaper.mostpopular.bycategory&imsi=460026408885439&format=json&deviceId=861022004611283&appSecret=2b45451eb109";
		// s =
		// "sign=F853324F59EAA5549903B969AF0FAAA0&timestamp=1500456300161&operation=plus&v=1.0&appId=app_gkt_lookerplus_5714&method=content.content2user.save&imsi=460026408885439&format=json&type=W&contentId=f4e324dc-fd7b-4b7e-af96-0034ac46e170&deviceId=861022004611283&appSecret=2b45451eb109";
		// s =
		// "sign=DCE2C9803B11A810D2E4327CED19DD02&timestamp=1500456320293&v=1.0&appId=app_gkt_lookerplus_5714&pageNo=0&pageSize=1000&method=content.content2user.list&imsi=460026408885439&format=json&type=W&deviceId=861022004611283&appSecret=2b45451eb109";
		//
		// StringBuilder builder = new StringBuilder();
		//
		// builder.append("{");
		//
		// String[] parts = s.split("&");
		//
		// for (String part : parts) {
		// String[] arg = part.split("=");
		// builder.append("\"").append(arg[0]).append("\":\"").append(arg[1]).append("\",");
		//
		// }
		//
		// builder.deleteCharAt(builder.length() - 1);
		// builder.append("}");
		//
		// ObjectMapper objectMapper = new ObjectMapper();
		//
		// String encryptStr = AESUtil.encrypt(builder.toString(), key);
		//
		// String result = doPost(encryptStr,
		// "http://172.30.120.57:8888/content/app/dispatch/encrypt").getContent();
		//
		// Map<String, String> map = objectMapper.readValue(result, new
		// TypeReference<Map<String, String>>() {
		// });
		//
		// if ("0".equals(map.get("code"))) {
		// String data = map.get("data");
		// if (data != null)
		// System.out.println(AESUtil.decrypt(map.get("data"), key));
		// }

		// HttpClient httpClient = HttpClients.createDefault();
		// HttpPost httpPost = new
		// HttpPost("https://payment-test.allterco.net/v1/getLastPIN");
		// List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		//
		// nvps.add(new BasicNameValuePair("sign",
		// "Speb1BVAIH+ViX8UMHkw+I7pZph+476AmYq0cfpeBliBV0QlMXuQIM9nsSHH1pFjEOfz/aHbDeH7pjDPMYohGEMu8z/PhweDOOJil2/Wf3pCYra6KlTkNApckDBQ7saHBjrYn/+MQiVlfIH+/kS2swiSPn1KHIbgfY2uXuO/xyY="));
		// nvps.add(new BasicNameValuePair("data",
		// "pR3xzkEVlEtr5EIOzOzGM75iXY00PadYYDJRCLU2XYQ1fGTxxLf2jb36cpGLw2ENZRMQJEbIO+0YSLoEfZwY5MD5B3T3cMqUpdOcqhwwnrILH2MtZgSmyKmgTKyzKBmyWeQTCs2dkT2cE1K0IC6GvGZ1BhS7LgxSkHexksJUBF1v4aLSvsCfF7v6zS54epBMWI+mF4fcCfZA/88JM1n0kJ6ka769+ASpS9CSA3zayt5PxYLDvd/uDUJTIE93TN7FTtSatHv1AF4QVgDeK1AQXV1JcPNRExLiPE/RObgbftqUD21JkRd/uIA/cNLZKvX1a3zWAEmWC7p75inm5ls4u9xKNIgHwCUqs6XW1k1RzrFhiAL+TXL6xWHQbbM27WlTUy6P7VgEULHd74BHH5IGdmr7tw81sB7bDnAzAAm4Hs2w+MDf/h4dSp56hep0QcV4c4o+Rf2h6yG8wZhG0B+QukuTuF1Er4AxZimst9dWc7YLH2MtZgSmyKmgTKyzKBmyhuyesfBFa4XOTsZW64oNihvaYxCwOhhkov1ruUrFM+InyNKqCdZAt8jnGS0diRcJFXcJ8ZOqy19JshwQaVziofLUpLPa1oUr5mjIUQ1T0xq4a41aPZFKk8rO1HFJwOPWPkTyaaH+uiq9FXScMEYNUHryWcZWHjFzHHkqzAQ0m88lgnRZlRrbouPyfB7OLnTzvmFlh/tquKN+mmRq8eahjSyM4cwmTL4qvPmPjElCK45ljwwjSf1oSed5BtfUhRlrRonDKHUqqSl5MlKnAzGC/8FiBeVfYaR6T5mVhSvKivUrAJ5cA5wIJ5L0iWqDDtti6S4+ZHuNXzbto5w5we0xAiIhae9MIg43zV3JcS4e0uDgQpA6uEJVeMafd7YTXPjN4Ek/mvyE69kRIbX9B65gng=="));
		// nvps.add(new BasicNameValuePair("key",
		// "AksDLD18sg/9qMIq4hG7ZOpBWm0Qzic7rgO2GKvfD7p7SGjttBx3CTKy9WAtePvCf/8GGKsHXl3abTuQzvUDpJiNb5ikQI98uYiw2/vWlIBgU4Pce6UBCgPupXWjGpL5nveBRJ1PO4e1Eg+ymE0Dq8+4+lOOrAR74zqQ23dkQNM="));
		//
		//
		//
		//
		// httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		// HttpResponse response = httpClient.execute(httpPost);
		//
		// System.out.println(response.getStatusLine());
		// System.out.println(EntityUtils.toString(response.getEntity()));

		// System.out.println(doPost("EE09EB25F712AE20212B61D0E3F0DA370745145FB8111ABF8B5A4D4F0C6D2134D792C072BDBA6EF63A8B62402FAA9FDAE3C71E09160971D7D66D89F248B8195F83CB3E5AFC145A01F4A64D90E94C698B5A5473A17E98BA5155DCC3C2A98D52856468AD71E65C91753CB40D0E1AE9DAE18362BED234583DAD04415451EE9486084A848D2EBEEA0FC66F78DEEE8ECE80F05BA42FF60957619DAF3C8AE1720C2FB721695630AF320EB8CCBD0EEF28C92D8A50DA690B1911EEB6EC01BE00819EBA95EAA612783766E61BD1BBD56B0722869A0C318DD5165EE8D42603DF07294659529F1B176A4FF3BCD0566655FA3A64EE8A8789FD208CEE15FD1F0F3F68FF4F6805843D7C8FB3B766CB91996341715DB7A17F00723758B7AD9C0950BDEE97C7D817524594AC7BE45A9FBA035F79164EC13C36911967422B96B893F627B6BF9EE3F2",
		// "http://172.30.120.57:8080/content/app/dispatch/encrypt"));

		String s = "F6630826231746BE5425C33B0AA14FD6F62E35AE6FD4C412F42ED07618B75E5A0DC3267097B731DA3A1149C9EF96EF2406F7F7A424315774ACA74A036FA160764420C68AD7AEC0CD4ECFD6992BD7E800E069B3ACA64026CCD0A263E2D29F58BE1D930A3DFE25B0D808DE1189FD29A685685C8D2CA5E573B7F762A4A3FE663468F857C7097926CABD530852DB4B5182D2B256BF6252DE8FFD461491A60C4F1530F53A4D193DDA843A10A852DB307B87FE8F6358C12971F983D296DB5E2A95B28DD9F20C65EE86ABAD629DAB1E18B7CB671E589CBDBD8FFFC299C761B77CF8EF27";

		System.out.println(AESUtil.decrypt(s, key));
		
		//System.exit(0);
		
//		s = AESUtil.decrypt(s, key);
//		s = parseURLParamsToMapStr(s);
//		s = AESUtil.encrypt(s, key);

		ObjectMapper objectMapper = new ObjectMapper();

		String result = doPost(s, "http://source.vicandguys.com/app/dispatch/encrypt").getContent();

		Map<String, String> map = objectMapper.readValue(result, new TypeReference<Map<String, String>>() {
		});

		System.err.println(map);
		
		if ("0".equals(map.get("code"))) {
			String data = map.get("data");
			if (data != null)
				System.out.println(AESUtil.decrypt(map.get("data"), key));
		}

	}

	public static String parseURLParamsToMapStr(String urlParams) {
		StringBuilder builder = new StringBuilder();

		builder.append("{");

		String[] parts = urlParams.split("&");

		for (String part : parts) {
			String[] arg = part.split("=");
			builder.append("\"").append(arg[0]).append("\":\"");

			if (arg.length > 1)
				builder.append(arg[1]);

			builder.append("\",");
		}

		builder.deleteCharAt(builder.length() - 1);
		builder.append("}");

		return builder.toString();
	}
}
