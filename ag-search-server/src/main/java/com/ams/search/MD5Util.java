package com.ams.search;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	public static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++){
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n += 256;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname)) {
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			} else {
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
			}
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };


	// see this How-to for a faster way to convert
	// a byte array to a HEX string
	public static String getMD5Checksum(String filename) {

		if (!new File(filename).isFile()) {
			//LOGGER.error("Error: " + filename
			//  + " is not a valid file.");
			return null;
		}
		byte[] b = createChecksum(filename);
		if(null == b){
			//LOGGER.error("Error:create md5 string failure!");
			return null;
		}
		StringBuilder result = new StringBuilder();

		for (int i = 0; i < b.length; i++) {
			result.append(Integer.toString((b[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		return result.toString();

	}
	private static byte[] createChecksum(String filename) {
		InputStream fis = null;
		try {
			fis = new FileInputStream(filename);
			byte[] buffer = new byte[1024];
			MessageDigest complete = MessageDigest.getInstance("MD5");
			int numRead = -1;

			while ((numRead = fis.read(buffer)) != -1) {
				complete.update(buffer, 0, numRead);
			}
			return complete.digest();
		} catch (FileNotFoundException e) {
			e.getMessage();
		} catch (NoSuchAlgorithmException e) {
			e.getMessage();
		} catch (IOException e) {
			e.getMessage();
		} finally {
			try {
				if (null != fis) {
					fis.close();
				}
			} catch (IOException e) {
				e.getMessage();
			}
		}
		return null;

	}
}
