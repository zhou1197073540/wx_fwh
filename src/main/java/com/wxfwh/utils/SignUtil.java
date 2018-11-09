package com.wxfwh.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SignUtil {

	private static String token = "NEWERA_XIAOXIN";

	public static boolean checkSignature(String signature, String timestamp,String nonce) {
		String[] arra = new String[] { token, timestamp, nonce };
		Arrays.sort(arra);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arra.length; i++) {
			sb.append(arra[i]);
		}
		MessageDigest md = null;
		String stnStr = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(sb.toString().getBytes());
			stnStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		sb = null;
		return stnStr != null ? stnStr.equals(signature.toUpperCase()) : false;
	}

	/**
	 * @param digestArra
	 * @return
	 */
	private static String byteToStr(byte[] digestArra) {
		// TODO Auto-generated method stub
		String digestStr = "";
		for (int i = 0; i < digestArra.length; i++) {
			digestStr += byteToHexStr(digestArra[i]);
		}
		return digestStr;
	}

	private static String byteToHexStr(byte dByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] tmpArr = new char[2];
		tmpArr[0] = Digit[(dByte >>> 4) & 0X0F];
		tmpArr[1] = Digit[dByte & 0X0F];
		String s = new String(tmpArr);
		return s;
	}

	public static void main(String[] args) {

	}
}
