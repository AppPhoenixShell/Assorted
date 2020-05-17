import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import py.Py;
import security.AESPKCS5Jargon;
import security.AESPKCS5Jargon.String64;

public class JargonTest {
	
	
	public static void main(String[] args) throws Exception {
		
		
		String key = AESPKCS5Jargon.generate64Key();
		System.out.println(key);
		
		
		
		
		AESPKCS5Jargon jarg = new AESPKCS5Jargon(key);
		
		Py.print("key: " + key);
		
		Py.print("keyJargon: " + jarg.getKey());
		
		
		String plain = "hello there worlds";
		
		
		String enc = jarg.encryptText(plain);
		System.out.println(enc);
		
		String dec = jarg.decryptText(enc);
		
		System.out.println(dec);
		
		
		String basekey = "ake8MBazr0AqZHR9GRwU/WK22HZkUOPch8f5ExWj7zU=";
		
		AESPKCS5Jargon back = new AESPKCS5Jargon(basekey);
		
		String deco = back.decryptText("sOF2wO1rggMW84zPBgJO33eHhIBpeDVb4HEg505RCjIiJ0Xr14JTccjyWnA6+gEF");
		
		Py.print(deco);
		
        
        
        
			

	}
	
	

}
