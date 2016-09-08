package br.com.pontomobi.livelopontos.ui.rescuecode;

import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


/**
 * This is an example implementation of the OATH
 * TOTP algorithm.
 * Visit www.openauthentication.org for more information.
 *
 * @author Johan Rydell, PortWise, Inc.
 */

public class TokenGenerator {
	
	private static final int[] DIGITS_POWER
	// 0 1  2   3    4     5      6       7        8
	= {1,10,100,1000,10000,100000,1000000,10000000,100000000 };


	private TokenGenerator() {}

	/**
	 * This method uses the JCE to provide the crypto algorithm.
	 * HMAC computes a Hashed Message Authentication Code with the
	 * crypto hash algorithm as a parameter.
	 *
	 * @param crypto: the crypto algorithm (HmacSHA1, HmacSHA256,
	 *                             HmacSHA512)
	 * @param keyBytes: the bytes to use for the HMAC key
	 * @param text: the message or text to be authenticated
	 */

	private static byte[] hmac_sha(String crypto, byte[] keyBytes,
			byte[] text){
		try {
			Mac hmac;
			hmac = Mac.getInstance(crypto);
			SecretKeySpec macKey = new SecretKeySpec(keyBytes, "RAW");
			hmac.init(macKey);
			return hmac.doFinal(text);
		} catch (GeneralSecurityException gse) {
			throw new UndeclaredThrowableException(gse);
		}
	}

	/**
	 * This method converts a HEX string to Byte[]
	 *
	 * @param hex: the HEX string
	 *
	 * @return: a byte array
	 */

	private static byte[] hexStr2Bytes(String hex){
		// Adding one byte to get the right conversion
		// Values starting with "0" can be converted
		byte[] bArray = new BigInteger("10" + hex,16).toByteArray();

		// Copy all the REAL bytes, not the "first"
		byte[] ret = new byte[bArray.length - 1];
		for (int i = 0; i < ret.length; i++)
			ret[i] = bArray[i+1];
		return ret;
	}
	

	/**
	 * This method generates a TOTP value for the given
	 * set of parameters.
	 *
	 * @param key: the shared secret, HEX encoded
	 * @param time: a value that reflects a time
	 * @param returnDigits: number of digits to return
	 *
	 * @return: a numeric String in base 10 that includes
	 *              {@link truncationDigits} digits
	 */

	protected static String generateTOTP(String key,
			long eventCount,
			int tokenLength){
		return generateTOTP(key, eventCount, tokenLength, "HmacSHA1");
	}

	/**
	 * This method generates a TOTP value for the given
	 * set of parameters.
	 *
	 * @param key: the shared secret, HEX encoded
	 * @param time: a value that reflects a time
	 * @param returnDigits: number of digits to return
	 *
	 * @return: a numeric String in base 10 that includes
	 *              {@link truncationDigits} digits
	 */

	public static String generateTOTP256(String key,
										 long eventCount,
										 int tokenLength){
		return generateTOTP(key, eventCount, tokenLength, "HmacSHA256");
	}


	/**
	 * This method generates a TOTP value for the given
	 * set of parameters.
	 *
	 * @param key: the shared secret, HEX encoded
	 * @param time: a value that reflects a time
	 * @param returnDigits: number of digits to return
	 *
	 * @return: a numeric String in base 10 that includes
	 *              {@link truncationDigits} digits
	 */

	protected static String generateTOTP512(String key,
			long eventCount,
			int tokenLength){
		return generateTOTP(key, eventCount, tokenLength, "HmacSHA512");
	}

	/**
	 * This method generates a TOTP value for the given
	 * set of parameters.
	 *
	 * @param key: the shared secret, HEX encoded
	 * @param time: a value that reflects a time
	 * @param returnDigits: number of digits to return
	 * @param crypto: the crypto function to use
	 *
	 * @return: a numeric String in base 10 that includes
	 *              {@link truncationDigits} digits
	 */

	private static String generateTOTP(String key,
			long eventCount,
			int tokenLength,
			String crypto){
			String result = null;
		
		//convert to hexa String
		 String time = Long.toHexString(eventCount).toUpperCase();
		 
		// Using the counter
		// First 8 bytes are for the movingFactor
		// Compliant with base RFC 4226 (HOTP)
		while (time.length() < 16 )
			time = "0" + time;

		// Get the HEX in a Byte[]
		byte[] msg = hexStr2Bytes(time);
		byte[] k = hexStr2Bytes(key);
		byte[] hash = hmac_sha(crypto, k, msg);

		// put selected bytes into result int
		int offset = hash[hash.length - 1] & 0xf;

		int binary =
				((hash[offset] & 0x7f) << 24) |
				((hash[offset + 1] & 0xff) << 16) |
				((hash[offset + 2] & 0xff) << 8) |
				(hash[offset + 3] & 0xff);

		int otp = binary % DIGITS_POWER[tokenLength];

		result = Integer.toString(otp);
		while (result.length() < tokenLength) {
			result = "0" + result;
		}
		return result;
	}
	
	/**
	 * Generates a new seed value for a token
	 * the returned string will contain a randomly generated
	 * hex value
	 * @param length - defines the length of the new seed this should be either 128 or 160
	 * @return
	 */
	public static String generateNewSeed(int length){
		
		String salt = "";
		long ticks = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis();		
		salt = salt + ticks;
		
		byte[] byteToHash = salt.getBytes();
		
		MessageDigest md = null;
		
		try{
			
			switch (length) {
			case 128:
				//128 long
				md = MessageDigest.getInstance("MD5");
				break;
			case 160:
				//160 long
				md = MessageDigest.getInstance("SHA1");
				break;
			case 256:
				md = MessageDigest.getInstance("SHA-256");
				break;
			case 512:
				md = MessageDigest.getInstance("SHA-512");
				break;
			default:
				throw new IllegalArgumentException("Invalid length to generate seed");
			}
			
			md.reset();
			md.update(byteToHash);
			
			byte[] digest = md.digest();
			
			//convert to hex string			
			
			return byteArrayToHexString(digest);
			
		}catch(NoSuchAlgorithmException ex){
			return null;		
		}
	}


	public static String byteArrayToHexString(byte[] digest) {
		
		StringBuffer buffer = new StringBuffer();
		
		for(int i =0; i < digest.length; i++){
			String hex = Integer.toHexString(0xff & digest[i]);
			
			if(hex.length() == 1)
				buffer.append("0");
			
			buffer.append(hex);

		}
		
		return buffer.toString();
	}
}