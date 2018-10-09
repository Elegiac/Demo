package std.demo;

import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

public class AdmireUDemo {

	static RestTemplate re = new RestTemplate();
	static String apiUrl = "http://content.mcdmobi.com/admireU/";

	public static void main(String[] args) {
		// register("test_user", DigestUtils.md5Hex("test123"), "8618706891060", "FeRh",
		// 8);

		// login("test_user", DigestUtils.md5Hex("test123"));
		// sendSMSCode("8618706891060");

		// sendMessage("7fe939f2-f139-47ab-a48a-da0fdac9c92b", "00000000002",
		// "test_phone_user3", "male",
		// AESUtil.encrypt("Hello world!", "Admire U Forever"));

		getMessagePreview("7fe939f2-f139-47ab-a48a-da0fdac9c92b", 1, 30);

		getMessageDetail("7fe939f2-f139-47ab-a48a-da0fdac9c92b", "8e62df45-8e50-4535-b14e-1e580991889d", 1, 30);

	}

	public static String decrypt(JSONObject result) {
		System.err.println(result);
		if ("0".equals(result.getString("code"))) {
			String data = AESUtil.decrypt(result.getString("data"), "Admire U Forever");
			return data;
		}
		return null;
	}

	public static void sendSMSCode(String phone) {
		StringBuilder builder = new StringBuilder();
		builder.append(apiUrl).append("sms/code/send");
		builder.append("?phone=").append(phone);

		JSONObject result = re.getForObject(builder.toString(), JSONObject.class);

		System.out.println(decrypt(result));

	}

	public static void login(String userName, String password) {
		StringBuilder builder = new StringBuilder();
		builder.append(apiUrl).append("user/login");
		builder.append("?userName=").append(userName);
		builder.append("&password=").append(password);

		JSONObject result = re.postForObject(builder.toString(), null, JSONObject.class);

		System.out.println(decrypt(result));

	}

	public static void register(String userName, String password, String phoneNumber, String code, int timeZone) {
		StringBuilder builder = new StringBuilder();
		builder.append(apiUrl).append("user/register");
		builder.append("?userName=").append(userName);
		builder.append("&password=").append(password);
		builder.append("&phoneNumber=").append(phoneNumber);
		builder.append("&code=").append(code);
		builder.append("&timeZone=").append(timeZone);

		JSONObject result = re.postForObject(builder.toString(), null, JSONObject.class);

		System.out.println(decrypt(result));

	}

	public static void sendMessage(String userId, String receiverId, String text) {
		StringBuilder builder = new StringBuilder();
		builder.append(apiUrl).append("message/send");
		builder.append("?userId=").append(userId);
		builder.append("&receiverId=").append(receiverId);
		builder.append("&text=").append(text);

		JSONObject result = re.postForObject(builder.toString(), null, JSONObject.class);

		System.out.println(decrypt(result));

	}

	public static void sendMessage(String userId, String phone, String userName, String gender, String text) {
		StringBuilder builder = new StringBuilder();
		builder.append(apiUrl).append("message/send/new");
		builder.append("?userId=").append(userId);
		builder.append("&phone=").append(phone);
		builder.append("&userName=").append(userName);
		builder.append("&gender=").append(gender);
		builder.append("&text=").append(text);

		JSONObject result = re.postForObject(builder.toString(), null, JSONObject.class);

		System.out.println(decrypt(result));

	}

	public static void getMessagePreview(String userId, int pageNo, int pageSize) {
		StringBuilder builder = new StringBuilder();
		builder.append(apiUrl).append("message/search");
		builder.append("?userId=").append(userId);
		builder.append("&pageNo=").append(pageNo);
		builder.append("&pageSize=").append(pageSize);

		JSONObject result = re.getForObject(builder.toString(), JSONObject.class);

		System.out.println(decrypt(result));

	}

	public static void getMessageDetail(String userId, String phoneUserId, int pageNo, int pageSize) {
		StringBuilder builder = new StringBuilder();
		builder.append(apiUrl).append("message/search/details");
		builder.append("?userId=").append(userId);
		builder.append("&phoneUserId=").append(phoneUserId);
		builder.append("&pageNo=").append(pageNo);
		builder.append("&pageSize=").append(pageSize);

		JSONObject result = re.getForObject(builder.toString(), JSONObject.class);

		System.out.println(decrypt(result));

	}

}
