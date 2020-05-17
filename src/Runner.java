import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.database.FirebaseDatabase;

import defrunn.TestSort;
import himawari.Himawari;
import himawari.Scrambler;

public class Runner {
	
	
	public static void main(String[] args) throws InterruptedException, IOException {
		


		PriorityQueue<TestSort> queue = new PriorityQueue<>();
		
		List<TestSort> sorted = new ArrayList<>();
		
		TestSort greater = new TestSort(22, "greater");
		TestSort smaller = new TestSort(1, "smaller");
		
		sorted.add(smaller);
		sorted.add(greater);
		
		queue.addAll(sorted);
		
		Collections.sort(sorted);
		
		TestSort first = queue.poll();
		System.out.println("poll "+ first.msg);
		
		
		
		
		
		
		
		
		
	}
	
	public static void genKey(String [] args) throws InterruptedException, IOException
	{
		File fseed = new File("kernal/himawari_seed");
		
		
		byte[] buffer = new byte[512];
		
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(fseed));
		
		
		ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
	
		
		int c;
		while((c= in.read(buffer)) != -1) {
			out.write(buffer, 0,c);
		}
		
		
		
		
		in.close();
		
		
		byte[] bseed = out.toByteArray();
		
		
		SecureRandom random = new SecureRandom(bseed);
		
		try {
			KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
			
			
			keygen.initialize(2048, random);
			
			KeyPair pair = keygen.generateKeyPair();
			
			System.out.println(Base64.getEncoder().encodeToString(pair.getPublic().getEncoded()));
			System.out.println();
			System.out.println(Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded()));
			
			
			
			saveKey(pair.getPublic(), "kernal/public_key-X509");
			saveKey(pair.getPrivate(), "kernal/private_key-PKCS8");
			
			
			
			
			
			
			
			
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		//byte[] seed1 = out.toByteArray();
		
		
		
		
		
		
		
		
		
		
	}
	public static void saveKey(Key key, String filename) {
		File file = new File(filename);
		
		try {
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
			out.write(key.getEncoded());
			out.close();
			
			System.out.printf("saved key %s as %s\n", filename, key.getFormat());
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch bl;ock
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	
	public static void main2(String [] args) {
		
	
	try {
		
		
		
		
		SecureRandom random = new SecureRandom();
		String password = "mypassword";
		byte[] saltBytes = new byte[16];
		random.nextBytes(saltBytes);
		
		KeyGenerator gen = KeyGenerator.getInstance("AES");
		gen.init(256);
		
		
		
		
		 byte[] iv = new byte[16];
		 IvParameterSpec ivspec = new IvParameterSpec(iv);
		
		
		
		SecretKey secretKey = gen.generateKey();
		
		
		System.out.println(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
		System.out.println("size: " + secretKey.getEncoded().length);
		
		
		
		
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey,ivspec);
		
		String hello = "hello cipher";
		
		
		
		String en64 = Base64.getEncoder().encodeToString(cipher.doFinal(hello.getBytes("UTF-8")));
		System.out.println(en64);
		
		
		
		String myKey64 = "VpQYYFyffnJ4t7TW+kYPbbJlPRCYmLVWRu5Q/vLfKXA=";
		String message = "z6GFwslSJ0wa9vk9I5m5/Q==";
		
		
		SecretKey sec = new SecretKeySpec(Base64.getDecoder().decode(myKey64.getBytes("UTF-8")), "AES");
		
		Cipher dcip = Cipher.getInstance("AES/CBC/PKCS5Padding");
		dcip.init(Cipher.DECRYPT_MODE, sec, ivspec);
		
		String pop = new String (dcip.doFinal(Base64.getDecoder().decode(message)));
		
		System.out.println(pop);
		
		
		
		
		
		
		
		
	} catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (NoSuchPaddingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InvalidKeyException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalBlockSizeException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (BadPaddingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InvalidAlgorithmParameterException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		
	
		
		
		
		
		
	}
}
