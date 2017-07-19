package std.demo;

import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密
 * 
 * @author don
 *
 */
public class AESUtil {
	/** 默认字符集 */
	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	/** 默认初始向量 */
	private static final byte[] DEFAULT_IV = "0000000000000000".getBytes();

	/**
	 * 加密
	 * 
	 * @param content
	 *            内容明文
	 * @param key
	 *            秘钥
	 * @return 密文
	 */
	public static String encrypt(String content, String key) {
		return parseByte2HexStr(encrypt(content.getBytes(DEFAULT_CHARSET), key.getBytes(DEFAULT_CHARSET), DEFAULT_IV));
	}

	/**
	 * 解密
	 * 
	 * @param content
	 *            内容密文
	 * @param key
	 *            秘钥
	 * @return 密文
	 */
	public static String decrypt(String content, String key) {
		return new String(decrypt(parseHexStr2Byte(content), key.getBytes(DEFAULT_CHARSET), DEFAULT_IV),
				DEFAULT_CHARSET);
	}

	public static String encrypt(String content, String key, String iv) {
		return parseByte2HexStr(encrypt(content.getBytes(DEFAULT_CHARSET), key.getBytes(DEFAULT_CHARSET),
				iv.getBytes(DEFAULT_CHARSET)));
	}

	public static String decrypt(String content, String key, String iv) {
		return new String(
				decrypt(parseHexStr2Byte(content), key.getBytes(DEFAULT_CHARSET), iv.getBytes(DEFAULT_CHARSET)),
				DEFAULT_CHARSET);
	}

	public static byte[] encrypt(byte[] content, byte[] key, byte[] iv) {
		try {
			SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(iv));
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] decrypt(byte[] content, byte[] key, byte[] iv) {
		try {
			SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(iv));
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	public static void main(String[] args) {
		String content = "123456";
		String key = "make it happened";

		System.out.println("原字符串:" + content);
		String result1 = encrypt(content, key);
		System.out.println("加密:" + result1);
		String result2 = decrypt(result1, key);
		System.out.println("解密:" + result2);
	}
}
