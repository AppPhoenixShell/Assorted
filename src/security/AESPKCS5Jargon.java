package security;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;


/*  	Jargon is a wrapper around the AES Encryption protocol that converts simple binary & text data
 * 		into base64 encoded strings to be stored in a database
 * 
 * */

public class AESPKCS5Jargon
{
	public static final int SALT_SIZE = 4;
	public static final int IV_SIZE = 16;
	
	private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
	private static final String ALGORITHM = "AES";
	
	
	private Cipher cipher;
	private final SecretKey secretKey;
	
	
	public AESPKCS5Jargon(String64 key64) {
		this(key64.cpy());
	}
	public AESPKCS5Jargon(String key) {
		this(new String64(key));
	}
	public AESPKCS5Jargon() {
		this(AESPKCS5Jargon.generate64Key());
	}
	
	public String64 getKey64() {
		return new String64(secretKey.getEncoded());
	}
	public String getKey() {
		return getKey64().as64String();
	}
	
	public String encryptText(String text) {
		return encrypt64(text.getBytes(StandardCharsets.UTF_8)).as64String();
	}
	
	public String decryptText(String text64)
	{
		String64 base = new String64(text64);
		return new String(decrypt(base.cpy()));
	}
	
	public AESPKCS5Jargon(byte[] keyBytes) {
		secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
		
		try {
			cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static final SecretKey generateKey() {
		try {
			KeyGenerator gen = KeyGenerator.getInstance("AES");
			gen.init(256);
			
			SecretKey key = gen.generateKey();
			return key;
		
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static final String generate64Key() {
		SecretKey key = generateKey();
		return Hex.encodeHexString(key.getEncoded());
	}
	
	public String64 encrypt64(byte[] data) {
	
		SecureRandom rand = new SecureRandom();
		byte[] iv = new byte[IV_SIZE];
		byte[] salt = new byte[SALT_SIZE];
		rand.nextBytes(iv);
		rand.nextBytes(salt);
		
		byte[] emsg = encrypt(data, salt, iv);
		return new String64(emsg);
	}
	

	
	public byte[] decrypt(byte[] data) {
		
		try {
			//byte[] data = Base64.getDecoder().decode(message.getBytes("UTF-8"));
			
			byte[] ivBytes = new byte[IV_SIZE];
			byte[] messageBytes=  new byte[data.length - ivBytes.length];
			
			System.arraycopy(data, 0, ivBytes, 0, ivBytes.length);
			System.arraycopy(data, ivBytes.length, messageBytes, 0, messageBytes.length);
			
			cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(ivBytes));
			
			byte[] text = cipher.doFinal(messageBytes);
			
			byte[] finalBytes= new byte[text.length - SALT_SIZE];
			System.arraycopy(text, SALT_SIZE, finalBytes, 0, finalBytes.length);
			
			return  finalBytes;// new String(text, SALT_SIZE, text.length - SALT_SIZE);
			
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	public byte[] encrypt(byte[] data) {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_SIZE];
		byte[] ivBytes = new byte[IV_SIZE];
		random.nextBytes(salt);
		random.nextBytes(ivBytes);
		
		return encrypt(data, salt, ivBytes);
	}
	
	/** encrypts data usign AES with random IV and specified salt*/
	private byte[] encrypt(byte[] data, byte[] salt, byte[] ivBytes) {
		try {
			IvParameterSpec iv = new IvParameterSpec(ivBytes);
			
			byte[] bytesToBeEncrypted = new byte[salt.length + data.length];
			System.arraycopy(salt, 0, bytesToBeEncrypted, 0, salt.length);
			System.arraycopy(data, 0, bytesToBeEncrypted, salt.length, data.length);
			
			
	
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
			byte[] encryptedBytes = cipher.doFinal(bytesToBeEncrypted);
			
			byte[] finalBytes = new byte[ivBytes.length + encryptedBytes.length];
			System.arraycopy(ivBytes, 0, finalBytes, 0, ivBytes.length);
			System.arraycopy(encryptedBytes, 0, finalBytes, ivBytes.length, encryptedBytes.length);
	
			return finalBytes;//  Base64.getEncoder().encodeToString(finalBytes);
			
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public static class String64 {
		private final byte[] data;
		
		
		public String64(String string64) {
			this.data = Base64.getDecoder().decode(string64);
		}
		public String64(byte[] data) {
			this.data = data;
		}
		public String as64String() {
			return Base64.getEncoder().encodeToString(data);
		}
		public String asString() {
			return new String(data);
		}
		
		public byte[] cpy() {
			byte[] cdata = new byte[data.length];
			System.arraycopy(data, 0, cdata, 0, data.length);
			return cdata;
		}
		
		public String asHex() {
			StringBuilder sb = new StringBuilder();
			for (byte b : data) {
				sb.append(String.format("%02X ", b));
			}
			return sb.toString();
			   
		}
		
	}

	
	
}
