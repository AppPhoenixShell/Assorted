package rss;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256
{
	public static String hash(byte[] data) {
		
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] encodedhash = digest.digest(data);
			
			return bytesToHex(encodedhash);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static String bytesToHex(byte[] hash) {
	    StringBuffer hexString = new StringBuffer();
	    for (int i = 0; i < hash.length; i++) {
	    String hex = Integer.toHexString(0xff & hash[i]);
	    if(hex.length() == 1) hexString.append('0');
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
	public static String hash(String text) {
		return hash(text.getBytes(StandardCharsets.UTF_8));
	}
}
