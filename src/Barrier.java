import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Barrier
{
	public static final byte[] DEFAULT_SALT = new byte[] {0x2a, 0x28, 0x15, 0x59};
	
	/*
	private final String password;
	private final byte[] salt;
	private final int iterations;
	private final int keyLength;
	*/
	
	private final String64 key;
	
	public Barrier(String password, byte[] salt, int iterations, int keyLength){
		/*
		this.password = password;
		this.salt = salt;
		this.iterations = iterations;
		this.keyLength = keyLength;
		*/
		
		this.key = generateKey(password, salt, iterations, keyLength);
	}
	
	
	public static String64 generateKey(final String password, final byte[] salt, final int iterations, final int keyLength ) {

        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
            PBEKeySpec spec = new PBEKeySpec( password.toCharArray(), salt, iterations, keyLength );
            SecretKey key = skf.generateSecret( spec );
            byte[] res = key.getEncoded( );
            return new String64(res);
        } catch ( NoSuchAlgorithmException | InvalidKeySpecException e ) {
            throw new RuntimeException( e );
        }
    }


	public String64 getKey() {
		return key;
	}
}
