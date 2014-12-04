package sutras.cheng.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * ���ڻ�ȡjar�ļ�����Դ�ķ�װ��---Ŀǰֻ��ͨ���ļ��ķ�ʽ--����ͨ������ȡ
 * 
 * @author chengkai
 * 
 */
public final class JarResources {
	// �Ƿ�������
	private boolean debugOn = false;
	public static final String ANDROID_RES_DIRECTORY = "res/drawable-mdpi/";
	// ����ļ���С��ӳ��
	private Hashtable<String, Integer> htSizes = new Hashtable<String, Integer>();
	// ���jar��Դ�ļ���ӳ��
	private Hashtable<String, Object> htJarContents = new Hashtable<String, Object>();
	// jar�ļ���--ָ��һ���ļ�·��
	private String jarFileName;
	// ʹ���ļ�
	private File jarFile;
	// ʹ��������ȡ��Դ
	private InputStream stream1;
	private InputStream stream2;

	public JarResources(String jarFileName) {
		this.jarFileName = jarFileName;
		useFileName();
	}

	public JarResources(File file) {
		this.jarFile = file;
		useFile();
	}

	// public JarResources(InputStream stream1,InputStream stream2){
	// this.stream1=stream1;
	// this.stream2=stream2;
	// useStream();
	// }
	/**
	 * ͨ����Դ�ļ�����ȡ��Դת��Ϊ������Դ
	 * 
	 * @param name
	 * @return
	 */
	public byte[] getResource(String name) {
		return (byte[]) htJarContents.get(name);
	}

	private void useFileName() {
		try {
			ZipFile zf = new ZipFile(jarFileName);
			Enumeration e = zf.entries();
			while (e.hasMoreElements()) {
				ZipEntry ze = (ZipEntry) e.nextElement();
				dump(ze);
				htSizes.put(ze.getName(), new Integer((int) ze.getSize()));
			}
			zf.close();
			FileInputStream fis = new FileInputStream(jarFileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ZipInputStream zis = new ZipInputStream(bis);
			ZipEntry ze = null;
			while ((ze = zis.getNextEntry()) != null) {
				if (ze.isDirectory()) {
					continue;
				}
				dump(ze);
				int size = (int) ze.getSize();
				if (size == -1) {
					size = ((Integer) htSizes.get(ze.getName())).intValue();
				}
				byte[] b = new byte[(int) size];
				int rb = 0;
				int chunk = 0;
				while (((int) size - rb) > 0) {
					chunk = zis.read(b, rb, (int) size - rb);
					if (chunk == -1) {
						break;
					}
					rb += chunk;
				}
				htJarContents.put(ze.getName(), b);
				dump(ze, rb, size);
			}
			zis.close();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void useFile() {
		try {
			ZipFile zf = new ZipFile(jarFile);
			Enumeration e = zf.entries();
			while (e.hasMoreElements()) {
				ZipEntry ze = (ZipEntry) e.nextElement();
				dump(ze);
				htSizes.put(ze.getName(), new Integer((int) ze.getSize()));
			}
			zf.close();
			FileInputStream fis = new FileInputStream(jarFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ZipInputStream zis = new ZipInputStream(bis);
			ZipEntry ze = null;
			while ((ze = zis.getNextEntry()) != null) {
				if (ze.isDirectory()) {
					continue;
				}
				dump(ze);
				int size = (int) ze.getSize();
				if (size == -1) {
					size = ((Integer) htSizes.get(ze.getName())).intValue();
				}
				byte[] b = new byte[(int) size];
				int rb = 0;
				int chunk = 0;
				while (((int) size - rb) > 0) {
					chunk = zis.read(b, rb, (int) size - rb);
					if (chunk == -1) {
						break;
					}
					rb += chunk;
				}
				htJarContents.put(ze.getName(), b);
				dump(ze);
			}
			zis.close();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ͨ�����ķ�����ʱ������---�˴�ֻ������
	 */
	private void useStream() {
		try {
			ZipInputStream ztop = new ZipInputStream(stream1);
			ZipEntry ztmp = null;
			while ((ztmp = ztop.getNextEntry()) != null) {
				dump(ztmp);
				htSizes.put(ztmp.getName(), new Integer((int) ztmp.getSize()));
			}
			ztop.close();
			BufferedInputStream bis = new BufferedInputStream(stream2);
			ZipInputStream zis = new ZipInputStream(bis);
			ZipEntry ze = null;
			while ((ze = zis.getNextEntry()) != null) {
				if (ze.isDirectory()) {
					continue;
				}
				dump(ze);
				int size = (int) ze.getSize();
				if (size == -1) {
					size = ((Integer) htSizes.get(ze.getName())).intValue();
				}
				System.out.println("size--->" + size);
				byte[] b = new byte[(int) size];
				int rb = 0;
				int chunk = 0;
				while (((int) size - rb) > 0) {
					chunk = zis.read(b, rb, (int) size - rb);
					if (chunk == -1) {
						break;
					}
					rb += chunk;
				}
				htJarContents.put(ze.getName(), b);
				dump(ze);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ���Դ�ӡ��Ϣ
	private void dump(ZipEntry ze) {
		if (debugOn) {
			System.out.println(ze.getName() + "--size=" + ze.getSize());
		}
	}

	// ����ѹ����Ϣ
	private void dump(ZipEntry ze, int rb, int size) {
		if (debugOn) {
			System.out.println(ze.getName() + "rb=" + rb + ",size=" + size
					+ ",csize=" + ze.getCompressedSize());
		}
	}

	public boolean isDebugOn() {
		return debugOn;
	}

	public void setDebugOn(boolean debugOn) {
		this.debugOn = debugOn;
	}
}
