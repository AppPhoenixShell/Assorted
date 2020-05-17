import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordHasher 
{
	private final int iterations;
	private final int keyLength;
	
	
	public PasswordHasher(int keyLength, int iterations) {
		this.iterations = iterations;
		this.keyLength = keyLength;
		
		
	}
	
	public byte[] hash(String password, byte[] salt) {
		return hash(password.toCharArray(), salt);
	}
	
	public byte[] hash(char[] password, byte[] salt) {
		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
			PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength );
            SecretKey key = skf.generateSecret( spec );
            byte[] res = key.getEncoded( );
            
            return res;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String hash64(String text, String salt) {
		
		try {
			byte[] hashBytes = hash(text, salt.getBytes("UTF-8"));
			return Base64.getEncoder().encodeToString(hashBytes);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public String hashSalt64(String password, String salt64) {
		byte[] saltBytes = Base64.getDecoder().decode(salt64);
		
		return Base64.getEncoder().encodeToString(hash(password, saltBytes));
	}
}
