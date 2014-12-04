package sutras.cheng.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

/**
 * ���ڴ���ֱ��������ص�����
 * 
 * @author chengkai
 * 
 */
public class IOUtils {
	// �������׺
	public static final String TXTEND = ".txt";
	// pdf��ʽ��׺
	public static final String PDFEND = ".pdf";
	// doc��ʽ��׺
	public static final String DOCEND = ".doc";
	// xml��ʽ��׺
	public static final String XMLEND = ".xml";

	private static final String CHARSET = "utf-8"; // ���ñ���
	private static final int TIME_OUT = 10 * 1000; // ��ʱʱ��
	private static final String CONTENT_TYPE = "multipart/form-data"; // ��������
	private static final String BOUNDARY = UUID.randomUUID().toString();

	public static void openFile(Context context, File file) {
		try {
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// ����intent��Action����
			intent.setAction(Intent.ACTION_VIEW);
			// ��ȡ�ļ�file��MIME����
			String type = getMIMEType(file);
			// ����intent��data��Type���ԡ�
			intent.setDataAndType(/* uri */Uri.fromFile(file), type);
			// ��ת
			context.startActivity(intent);
			// Intent.createChooser(intent, "��ѡ���Ӧ������򿪸ø�����");
		} catch (ActivityNotFoundException e) {
			Toast.makeText(context, "sorry�������ܴ򿪣���������������", 500).show();
		}
	}

	/**
	 * �ϴ��ļ�
	 * 
	 * @param file
	 * @param serverUrl
	 * @return
	 */
	public static String uploadFile(File file, String serverUrl) {
		HttpURLConnection conn = getConnect(serverUrl);
		String result = null;
		// �߽��ʶ �������
		String PREFIX = "--", LINE_END = "\r\n";

		try {
			// conn.connect();//��������
			if (file != null) {
				StringBuilder sb = new StringBuilder();
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				// ������������װ�ļ��ϴ�
				DataOutputStream dos = new DataOutputStream(
						conn.getOutputStream());
				/**
				 * �����ص�ע�⣺ name�����ֵΪ����������Ҫkey ֻ�����key �ſ��Եõ���Ӧ���ļ�
				 * filename���ļ������֣�������׺���� ����:abc.png
				 */

				sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""
						+ file.getName() + "\"" + LINE_END);
				sb.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + LINE_END);
				sb.append(LINE_END);
				dos.write(sb.toString().getBytes());
				InputStream is = new FileInputStream(file);
				byte[] bytes = new byte[1024];
				int len = 0;
				while ((len = is.read(bytes)) != -1) {
					dos.write(bytes, 0, len);
				}
				is.close();
				dos.write(LINE_END.getBytes());
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
						.getBytes();
				dos.write(end_data);
				dos.flush();
				/**
				 * ��ȡ��Ӧ�� 200=�ɹ� ����Ӧ�ɹ�����ȡ��Ӧ����
				 */
				int res = conn.getResponseCode();
				System.out.println("��Ӧ��--->" + res);
				if (res == 200) {
					InputStream input = conn.getInputStream();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(input));
					StringBuilder sb1 = new StringBuilder();
					String s = "";
					while ((s = reader.readLine()) != null) {
						sb1.append(s);
					}
					result = sb1.toString();
					System.out.println(result);
				} else {
					System.out.println("�ϴ��ļ�ʧ�ܣ�");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ָ��·�����ļ�����Ϊ�ַ���
	 * 
	 * @param path
	 * @param charset
	 * @return
	 */
	public static String parseFile(String path, String charset) {
		String info = "";
		try {
			InputStream input = new FileInputStream(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input, "utf-8"));
			String temp = "";
			while ((temp = reader.readLine()) != null) {
				info += temp;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * @param link
	 *            �ϴ��Լ����ڵĵ���λ��
	 * @return ������Ӧ������
	 */
	public static String uploadLoc(String link) {
		String result = null;
		try {
			HttpURLConnection conn = getConnect(link);
			conn.connect();// ��������
			// ������Ӧ��
			int res = conn.getResponseCode();
			System.out.println("������Ӧ����" + res);
			if (res == 200) {
				InputStream input = conn.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(input));
				StringBuilder sb = new StringBuilder();
				String s = "";
				while ((s = reader.readLine()) != null) {
					sb.append(s);
				}
				result = sb.toString();
				System.out.println("��Ӧ���-->" + result);
			} else {
				System.out.println("û����Ӧ��");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ����link����HttpURLConnection����ʵ��
	 * 
	 * @param link
	 * @return
	 */
	public static HttpURLConnection getConnect(String link) {
		// ������
		HttpURLConnection conn = null;
		try {
			// ����URL
			URL url = new URL(link);
			conn = (HttpURLConnection) url.openConnection();
			// ���ö�ȡʱ��
			conn.setReadTimeout(TIME_OUT);
			// �������ӳ�ʱʱ��
			conn.setConnectTimeout(TIME_OUT);
			// ����������
			conn.setDoInput(true);
			// ���������
			conn.setDoOutput(true);
			// ������ʹ�û���
			conn.setUseCaches(false);
			// ����ʽ---post����
			conn.setRequestMethod("POST");
			// �����ַ�����--��ز�������
			conn.setRequestProperty("Charset", CHARSET);
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * ��ȡ���ӵ�������
	 * 
	 * @param str
	 * @return
	 */
	public static InputStream getInputStream(String url) {
		HttpClient client = new DefaultHttpClient();
		try {
			HttpGet get = new HttpGet(url);
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return entity.getContent();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡĿ¼��ȫ���ļ�
	 * 
	 * @param dir
	 * @return
	 */
	public static List<File> listFile(File dir) {
		File[] files = dir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isFile();
			}
		});
		return new ArrayList<File>(Arrays.asList(files));
	}

	/**
	 * ��ȡĿ¼��ȫ���ļ�, ָ����չ�����ļ�
	 * 
	 * @param dir
	 * @return
	 */
	public static List<File> listFile(File dir, final String ext) {

		File[] files = dir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isFile() && pathname.getName().endsWith(ext);
			}
		});
		return new ArrayList<File>(Arrays.asList(files));
	}

	/**
	 * �ݹ��ȡĿ¼��ȫ���ļ�
	 * 
	 * @param dir
	 * @return
	 */
	public static List<File> listAll(File dir) {
		List<File> all = listFile(dir);
		File[] subs = dir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});
		for (File sub : subs) {
			all.addAll(listAll(sub));
		}
		return all;
	}

	/**
	 * �ݹ��ȡĿ¼��ȫ���ļ�, ָ����չ�����ļ�
	 * 
	 * @param dir
	 * @return
	 */
	public static List<File> listAll(File dir, String ext) {
		List<File> all = listFile(dir, ext);
		File[] subs = dir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});
		for (File sub : subs) {
			all.addAll(listAll(sub, ext));
		}
		return all;
	}

	/**
	 * �����ļ�
	 */
	public static void cp(String from, String to) throws IOException {
		cp(new File(from), new File(to));
	}

	/**
	 * �����ļ�
	 */
	public static void cp(File from, File to) throws IOException {
		FileInputStream in = new FileInputStream(from);
		OutputStream out = new FileOutputStream(to);
		cp(in, out);
		in.close();
		out.close();
	}

	/**
	 * �����ļ�
	 */
	public static void cp(InputStream in, OutputStream out) throws IOException {
		// 1K byte �Ļ�����!
		byte[] buf = new byte[1024];
		int count;
		while ((count = in.read(buf)) != -1) {
			// System.out.println(count);
			out.write(buf, 0, count);
		}
		in.close();
		out.close();
	}

	public static String readLine(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int b;
		while (true) {
			b = in.read();
			if (b == '\n' || b == '\r' || b == -1) {// �����Ƿ��ǻس�����
				break;
			}
			out.write(b);
		}
		return new String(out.toByteArray());
	}

	/**
	 * �����ж�ȡһ���ı�, ��ȡ��һ�еĽ���Ϊֹ
	 * 
	 * @param in
	 * @return һ���ı�
	 */
	public static String readLine(InputStream in, String charset)
			throws IOException {
		byte[] buf = new byte[0];
		int b = 0;
		while (true) {
			b = in.read();
			if (b == '\n' || b == '\r' || b == -1) {// �����Ƿ��ǻس�����
				break;
			}
			buf = Arrays.copyOf(buf, buf.length + 1);
			buf[buf.length - 1] = (byte) b;
		}
		if (buf.length == 0 && b == -1) {
			return null;
		}
		return new String(buf, charset);
	}

	/**
	 * ��ȡ�ļ���ȫ�����ݵ�һ��byte���� ���Ի���һ��"С"�ļ������ڴ���
	 */
	public static byte[] read(String filename) throws IOException {
		return read(new File(filename));
	}

	/**
	 * ��ȡ�ļ���ȫ�����ݵ�һ��byte���� ���Ի���һ��"С"�ļ������ڴ���
	 */
	public static byte[] read(File file) throws IOException {
		// ��RAF���ļ�
		RandomAccessFile raf = new RandomAccessFile(file, "r");
		// ��װ�ļ��ĳ��ȿ��� ����������(byte����)
		byte[] buf = new byte[(int) raf.length()];
		// ��ȡ�ļ��Ļ�����
		raf.read(buf);
		// �ر��ļ�(RAF)
		raf.close();
		// ���ػ�������������.
		return buf;
	}

	/**
	 * ��ȡ�ļ���ȫ�����ݵ�һ��byte���� ���Ի���һ��"С"�ļ������ڴ��� ��: �ļ�����: ABC�� ��ȡΪ: {41, 42, 43, d6,
	 * d0}
	 */
	public static byte[] read(InputStream in) throws IOException {
		byte[] ary = new byte[in.available()];
		in.read(ary);
		in.close();
		return ary;
	}

	/**
	 * ����byte �����ȫ������Ϊ�ַ���, ��hex(ʮ������)��ʽ���� ��: ����{0x41, 0x42, 0x43, 0xd6, 0xd0}
	 * ���: "[41, 42, 43, d6, d0]"
	 */
	public static String join(byte[] ary) {
		if (ary == null || ary.length == 0)
			return "[]";
		StringBuilder buf = new StringBuilder();
		buf.append("[").append(
				leftPad(Integer.toHexString(ary[0] & 0xff), '0', 2));
		for (int i = 1; i < ary.length; i++) {
			String hex = Integer.toHexString(ary[i] & 0xff);
			buf.append(",").append(leftPad(hex, '0', 2));
		}
		buf.append("]");
		return buf.toString();
	}

	public static String toBinString(byte[] ary) {
		if (ary == null || ary.length == 0)
			return "[]";
		StringBuilder buf = new StringBuilder();
		buf.append("[").append(
				leftPad(Integer.toBinaryString(ary[0] & 0xff), '0', 8));
		for (int i = 1; i < ary.length; i++) {
			String hex = Integer.toBinaryString(ary[i] & 0xff);
			buf.append(",").append(leftPad(hex, '0', 8));
		}
		buf.append("]");
		return buf.toString();
	}

	/**
	 * ʵ��leftPad����, ���ַ���ʵ�������
	 * 
	 * @param str
	 *            ������ַ���: 5
	 * @param ch
	 *            ����ַ�: #
	 * @param length
	 *            ����Ժ�ĳ���: 8
	 * @return "#######5"
	 */
	public static String leftPad(String str, char ch, int length) {
		if (str.length() == length) {
			return str;
		}
		char[] chs = new char[length];
		Arrays.fill(chs, ch);
		System.arraycopy(str.toCharArray(), 0, chs, length - str.length(),
				str.length());
		return new String(chs);
	}

	/**
	 * ��text׷�ӵ��ļ� filename��β�� ʹ��ϵͳĬ���ı�����
	 */
	public static void println(String filename, String text) throws IOException {
		println(new File(filename), text);
	}

	public static void println(File file, String text) throws IOException {
		OutputStream out = new FileOutputStream(file, true);
		println(out, text);
		out.close();
	}

	/**
	 * ���������һ���ַ���, ʹ��Ĭ�ϱ��� ���ر���
	 * 
	 * @param out
	 *            Ŀ����
	 * @param text
	 *            �ı�
	 * @throws IOException
	 */
	public static void println(OutputStream out, String text)
			throws IOException {
		out.write(text.getBytes());
		out.write('\n');
		out.flush();
	}

	/**
	 * ���������һ���ַ���, ʹ��ָ���ı��� ���ر���
	 * 
	 * @param out
	 *            Ŀ����
	 * @param text
	 *            �ı�
	 * @param charset
	 *            ָ���ı���
	 * @throws IOException
	 */
	public static void println(OutputStream out, String text, String charset)
			throws IOException {
		out.write(text.getBytes(charset));
		out.write('\n');
	}

	/**
	 * �з��ļ�, ��: file.dat �з�Ϊ file.dat.0, file.dat.1 ...
	 * 
	 * @param file
	 * @param size
	 *            ��С, ��KByteΪ��λ
	 */
	public static void split(String file, int size) throws IOException {
		if (size <= 0) {
			throw new IllegalArgumentException("��ɶѽ!");
		}
		int idx = 0;// �ļ������
		InputStream in = new BufferedInputStream(new FileInputStream(file));
		OutputStream out = new BufferedOutputStream(new FileOutputStream(file
				+ "." + idx++));
		int b;
		int count = 0;
		while ((b = in.read()) != -1) {
			out.write(b);
			count++;
			if (count % (size * 1024) == 0) {
				out.close();
				out = new BufferedOutputStream(new FileOutputStream(file + "."
						+ idx++));
			}
		}
		in.close();
		out.close();
	}

	/**
	 * ���ļ���������
	 * 
	 * @param filename
	 *            �ǵ�һ���ļ���,��:file.dat.0
	 */
	public static void join(String file) throws IOException {
		String filename = file.substring(0, file.lastIndexOf("."));
		String num = file.substring(file.lastIndexOf(".") + 1);
		int idx = Integer.parseInt(num);
		OutputStream out = new FileOutputStream(filename);
		File f = new File(filename + "." + idx++);
		while (f.exists()) {
			InputStream in = new FileInputStream(f);
			cp(in, out);
			in.close();
			f = new File(filename + "." + idx++);
		}
		out.close();
	}

	/**
	 * ���л�����
	 */
	public static byte[] Serialize(Serializable obj) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(os);

		out.writeObject(obj);// �������л�, foo
		out.close();
		byte[] ary = os.toByteArray();
		return ary;
	}

	public static Object Unserialize(byte[] data) throws IOException,
			ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(
				data));
		Object o = in.readObject();// �����л�
		in.close();
		return o;
	}

	/**
	 * �������㸳ֵ(��¡)
	 * 
	 * @param obj
	 * @return ����ĸ���
	 * @throws ClassNotFoundException
	 */
	public static Object clone(Serializable obj) throws IOException,
			ClassNotFoundException {
		return Unserialize(Serialize(obj));
	}

	/**
	 * ��ȡ���е��ַ�����
	 * 
	 * @param in
	 * @return
	 */
	public static char[] readChar(Reader in) throws IOException {
		StringBuilder buf = new StringBuilder();
		int c;
		while ((c = in.read()) != -1) {
			buf.append((char) c);
		}
		in.close();
		return buf.toString().toCharArray();
	}

	public static char[] readChar(String filename, String encoding)
			throws IOException {
		return readChar(new File(filename), encoding);
	}

	public static char[] readChar(File file, String encoding)
			throws IOException {
		return readChar(new FileInputStream(file), encoding);
	}

	public static char[] readChar(InputStream in, String encoding)
			throws IOException {
		return readChar(new InputStreamReader(in, encoding));
	}

	private static String getMIMEType(File file) {

		String type = "*/*";
		String fName = file.getName();
		// ��ȡ��׺��ǰ�ķָ���"."��fName�е�λ�á�
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
		/* ��ȡ�ļ��ĺ�׺�� */
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "")
			return type;
		// ��MIME���ļ����͵�ƥ������ҵ���Ӧ��MIME���͡�
		for (int i = 0; i < MIME_MapTable.length; i++) {

			if (end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}

	// �����Լ��������
	private static String[][] MIME_MapTable = {
			// {��׺����MIME����}
			{ ".3gp", "video/3gpp" },
			{ ".apk", "application/vnd.Android.package-archive" },
			{ ".asf", "video/x-ms-asf" },
			{ ".avi", "video/x-msvideo" },
			{ ".bin", "application/octet-stream" },
			{ ".bmp", "image/bmp" },
			{ ".c", "text/plain" },
			{ ".class", "application/octet-stream" },
			{ ".conf", "text/plain" },
			{ ".cpp", "text/plain" },
			{ ".doc", "application/msword" },
			{
					".docx",
					"application/vnd.openxmlformats-officedocument"
							+ ".wordprocessingml.document" },
			{ ".xls", "application/vnd.ms-excel" },
			{
					".xlsx",
					"application/vnd.openxmlformats-officedocument"
							+ "spreadsheetml.sheet" },
			{ ".exe", "application/octet-stream" },
			{ ".gif", "image/gif" },
			{ ".gtar", "application/x-gtar" },
			{ ".gz", "application/x-gzip" },
			{ ".h", "text/plain" },
			{ ".htm", "text/html" },
			{ ".html", "text/html" },
			{ ".jar", "application/java-archive" },
			{ ".java", "text/plain" },
			{ ".jpeg", "image/jpeg" },
			{ ".jpg", "image/jpeg" },
			{ ".js", "application/x-javascript" },
			{ ".log", "text/plain" },
			{ ".m3u", "audio/x-mpegurl" },
			{ ".m4a", "audio/mp4a-latm" },
			{ ".m4b", "audio/mp4a-latm" },
			{ ".m4p", "audio/mp4a-latm" },
			{ ".m4u", "video/vnd.mpegurl" },
			{ ".m4v", "video/x-m4v" },
			{ ".mov", "video/quicktime" },
			{ ".mp2", "audio/x-mpeg" },
			{ ".mp3", "audio/x-mpeg" },
			{ ".mp4", "video/mp4" },
			{ ".mpc", "application/vnd.mpohun.certificate" },
			{ ".mpe", "video/mpeg" },
			{ ".mpeg", "video/mpeg" },
			{ ".mpg", "video/mpeg" },
			{ ".mpg4", "video/mp4" },
			{ ".mpga", "audio/mpeg" },
			{ ".msg", "application/vnd.ms-outlook" },
			{ ".ogg", "audio/ogg" },
			{ ".pdf", "application/pdf" },
			{ ".png", "image/png" },
			{ ".pps", "application/vnd.ms-powerpoint" },
			{ ".ppt", "application/vnd.ms-powerpoint" },
			{
					".pptx",
					"application/vnd.openxmlformats-officedocument"
							+ ".presentationml.presentation" },
			{ ".prop", "text/plain" }, { ".rc", "text/plain" },
			{ ".rmvb", "audio/x-pn-realaudio" }, { ".rtf", "application/rtf" },
			{ ".sh", "text/plain" }, { ".tar", "application/x-tar" },
			{ ".tgz", "application/x-compressed" }, { ".txt", "text/plain" },
			{ ".wav", "audio/x-wav" }, { ".wma", "audio/x-ms-wma" },
			{ ".wmv", "audio/x-ms-wmv" },
			{ ".wps", "application/vnd.ms-works" }, { ".xml", "text/plain" },
			{ ".z", "application/x-compress" },
			{ ".zip", "application/x-zip-compressed" }, { "", "*/*" } };
}
