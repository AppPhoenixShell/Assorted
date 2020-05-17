package himawari;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Scrambler 
{
	public static final int TYPE_PUBLIC = 1;
	public static final int TYPE_PRIVATE = 2;
	
	private PrivateKey privateKey;
	private PublicKey publicKey;
	private Cipher cipher;
	
	
	public Scrambler(String publicKey64, String privateKey64) {
		
	
		
		
		X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey64.getBytes()));
		PKCS8EncodedKeySpec pkeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey64.getBytes()));
		
		
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			publicKey = keyFactory.generatePublic(pubSpec);
			privateKey = keyFactory.generatePrivate(pkeySpec);
		 
			cipher = Cipher.getInstance("RSA");

			
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	public byte[] encryptPublic(byte[] msg) {
		return doCipher(Cipher.ENCRYPT_MODE, TYPE_PUBLIC, msg);
	}
	public byte[] encryptPrivate(byte[] msg) {
		return doCipher(Cipher.ENCRYPT_MODE, TYPE_PRIVATE, msg);
	}
	public byte[] decryptPublic(byte[] msg) {
		return doCipher(Cipher.DECRYPT_MODE, TYPE_PUBLIC, msg);
	}
	public byte[] decryptPrivate(byte[] msg) {
		return doCipher(Cipher.DECRYPT_MODE, TYPE_PRIVATE, msg);
	}
	
	private byte[] doCipher(int cipherMode, int keyType, byte[] msg) {
		Key localKey = null;
		
		switch(keyType) {
			case TYPE_PRIVATE:
				localKey = privateKey;
				break;
			case TYPE_PUBLIC:
				localKey = publicKey;
				break;
		}
		
		try {
			cipher.init(cipherMode, localKey);
			byte[] emessage = cipher.doFinal(msg);
			return emessage;
		} catch (InvalidKeyException e) {
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
	
}
