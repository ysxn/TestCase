package com.codezyw.backuprestore;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ������һ���������ַ������ļ�MD5У��������ӣ����Թ����ɵ���������ֱ��ʹ�ã���������Ҫ����getMD5String(String
 * s)��getFileMD5String(File
 * file)�����������ֱ����������ַ�����md5У��ֵ�������ļ���md5У��ֵ��getFileMD5String_old(File
 * file)������ɾ����������ʹ�ã�
 * 
 * @author Ok
 * 
 */
public class MD5Util {
	/**
	 * Ĭ�ϵ������ַ�����ϣ��������ֽ�ת���� 16 ���Ʊ�ʾ���ַ�,apacheУ�����ص��ļ�����ȷ���õľ���Ĭ�ϵ�������
	 */
	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	protected static MessageDigest messagedigest = null;
	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException nsaex) {
			System.err.println(MD5Util.class.getName()
					+ "��ʼ��ʧ�ܣ�MessageDigest��֧��MD5Util��");
			nsaex.printStackTrace();
		}
	}

	/**
	 * �����ַ�����md5У��ֵ
	 * 
	 * @param s
	 * @return
	 */
	public static String getMD5String(String s) {
		return getMD5String(s.getBytes());
	}

	/**
	 * �ж��ַ�����md5У�����Ƿ���һ����֪��md5����ƥ��
	 * 
	 * @param password
	 *            ҪУ����ַ���
	 * @param md5PwdStr
	 *            ��֪��md5У����
	 * @return
	 */
	public static boolean checkPassword(String password, String md5PwdStr) {
		String s = getMD5String(password);
		return s.equals(md5PwdStr);
	}

	/**
	 * �����ļ���md5У��ֵ
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5String(File file) throws IOException {
		if (messagedigest == null) {
			return null;
		}
		InputStream fis;
		fis = new FileInputStream(file);
		byte[] buffer = new byte[1024];
		int numRead = 0;
		while ((numRead = fis.read(buffer)) > 0) {
			messagedigest.update(buffer, 0, numRead);
		}
		fis.close();
		return bufferToHex(messagedigest.digest());
	}

	public static String getMD5String(byte[] bytes) {
		if (messagedigest == null) {
			return null;
		}
		messagedigest.update(bytes);
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		// ȡ�ֽ��и� 4 λ������ת��, >>Ϊ�߼����ƣ�������λһ������, >>>�޷������ƣ��˴�δ�������ַ����кβ�ͬ
//		��>> ����,��λ������λ�� ��������һλ��ʾ��2
//		��>>> �޷�������,��λ��0���� ��>>����
//		��<< ���ơ� ����һλ��ʾ��2����λ�ͱ�ʾ4������2��n�η�
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		// ȡ�ֽ��е� 4 λ������ת��
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	public static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	public static String md5sum(String filename) {
		if (messagedigest == null) {
			return null;
		}
		InputStream fis;
		byte[] buffer = new byte[1024];
		int numRead = 0;
		MessageDigest md5;
		try {
			fis = new FileInputStream(filename);
			md5 = MessageDigest.getInstance("MD5");
			while ((numRead = fis.read(buffer)) > 0) {
				md5.update(buffer, 0, numRead);
			}
			fis.close();
			return toHexString(md5.digest());
		} catch (Exception e) {
			System.out.println("error");
			return null;
		}
	}

}