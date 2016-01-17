package Util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Codec {
	
	public static byte[] convertStringToAscii(String message) {
		System.out.println("string to ascii :"+ message);
		try {
			return message.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		};
		return null;
	}
	
	public static String convertAsciiToString(byte[] message) {
		StringBuilder builder = new StringBuilder();
		for (byte b : message) {
			builder.append((char) b);			
		}
		return builder.toString();
	}
	
	public static List<BigInteger> encode(String message, RSAPublicKey pub) {
		return Codec.encode(Codec.convertStringToAscii(message), pub);
	}
	
	public static List<BigInteger> encode(byte[] messageAscii, RSAPublicKey pub) {
		System.out.println("encode tab in :"+ Arrays.toString(messageAscii));
		List<BigInteger> resultat = new ArrayList<BigInteger>(messageAscii.length);
		for (int i=0;i<messageAscii.length;i++) {
			BigInteger value = new BigInteger(String.valueOf(messageAscii[i]));
			resultat.add(value.modPow(pub.getE(), pub.getN())); 
		}
		System.out.println("encode tab out :"+ resultat.toString());
		return resultat;
	}
	
	
	public static String decodeToString(List<BigInteger> encodeMessage, RSAPrivateKey priv) {
		return Codec.convertAsciiToString(Codec.decode(encodeMessage, priv));
	}
	
	public static byte[] decode(List<BigInteger> encodeMessage, RSAPrivateKey priv) {
		System.out.println("decode tab in :"+ encodeMessage.toString());
		byte[] resultat = new byte[encodeMessage.size()];
		int i=0;
		for (BigInteger bigInt : encodeMessage) {
			bigInt = bigInt.modPow(priv.getU(),priv.getN());
			resultat[i] = bigInt.byteValue();
			i++;
		}
		System.out.println("decode tab out :"+ Arrays.toString(resultat));
		return resultat;
	}

}
