
package com.codezyw.backuprestore;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author Sodino sodinoopen@hotmail.com
 * @since 2011��3��1��13ʱ00��41��
 */
public class Util {
    private Context mContext;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//���ø�ʽ 
    
	/**
	 * ��ȡ�ֻ������ڴ�·��
	 * 
	 */
	public static String getPhoneCardPath() {
		return Environment.getDataDirectory().getPath();

	}

	/**
	 * ��ȡsd��·�� ˫sd��ʱ�����ݡ����á���������ݴ洢λ��ѡ�񣬻�õ�������sd��������sd��
	 * 
	 * @return
	 */
	public static String getNormalSDCardPath() {
		return Environment.getExternalStorageDirectory().getPath();
	}

	/**
	 * ��ȡsd��·�� ˫sd��ʱ����õ�������sd��
	 * 
	 * @return
	 */
	public static String getSDCardPath() {
		//���������,����1S�ڲ�sd����/storage/emulated/legacy���ⲿsd����/storage/sdcard1
		///dev/fuse /storage/emulated/legacy fuse rw,nosuid,nodev,relatime,user_id=1023,group_id=1023,default_permissions,allow_other 0 0
		///dev/block/vold/179:65 /storage/sdcard1 vfat rw,dirsync,nosuid,nodev,noexec,relatime,uid=1000,gid=1015,fmask=0202,dmask=0202,allow_utime=0020,codepage=cp437,iocharset=iso8859-1,shortname=mixed,utf8,errors=remount-ro 0 0

		String cmd = "cat /proc/mounts";
		Runtime run = Runtime.getRuntime();// �����뵱ǰ Java Ӧ�ó�����ص�����ʱ����
		BufferedInputStream in = null;
		BufferedReader inBr = null;
		try {
			Process p = run.exec(cmd);// ������һ��������ִ������
			in = new BufferedInputStream(p.getInputStream());
			inBr = new BufferedReader(new InputStreamReader(in));

			String lineStr;
			while ((lineStr = inBr.readLine()) != null) {
				// �������ִ�к��ڿ���̨�������Ϣ
				Log.i("CommonUtil:getSDCardPath", lineStr);
				if (lineStr.contains("sdcard")
						&& lineStr.contains(".android_secure")) {
					String[] strArray = lineStr.split(" ");
					if (strArray != null && strArray.length >= 5) {
						String result = strArray[1].replace("/.android_secure",
								"");
						return result;
					}
				}
				// ��������Ƿ�ִ��ʧ�ܡ�
				if (p.waitFor() != 0 && p.exitValue() == 1) {
					// p.exitValue()==0��ʾ����������1������������
					Log.e("CommonUtil:getSDCardPath", "����ִ��ʧ��!");
				}
			}
		} catch (Exception e) {
			Log.e("CommonUtil:getSDCardPath", e.toString());
			// return Environment.getExternalStorageDirectory().getPath();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (inBr != null) {
					inBr.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return Environment.getExternalStorageDirectory().getPath();
	}

	// �鿴���е�sd·��
	public String getSDCardPathEx() {
		String mount = new String();
		try {
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec("mount");
			InputStream is = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			String line;
			BufferedReader br = new BufferedReader(isr);
			while ((line = br.readLine()) != null) {
				if (line.contains("secure"))
					continue;
				if (line.contains("asec"))
					continue;

				if (line.contains("fat")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						mount = mount.concat("*" + columns[1] + "\n");
					}
				} else if (line.contains("fuse")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						mount = mount.concat(columns[1] + "\n");
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mount;
	}

	// ��ȡ��ǰ·�������ÿռ�
	public static long getAvailableSize(String path) {
		try {
			File base = new File(path);
			StatFs stat = new StatFs(base.getPath());
			long nAvailableCount = stat.getBlockSize()
					* ((long) stat.getAvailableBlocks());
			return nAvailableCount;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

    public Util(Context c) {
        mContext = c;
    }

    public boolean checkFile(File a, File b) {
    	if (a == null || b == null) {
    		return false;
    	}
    	if (a.length() == b.length()) {
    		return true;
    	}
    	return false;
    }
    
    public Long convertDate(String str) {
        if (str == null || str.isEmpty()) {
            return Long.valueOf(System.currentTimeMillis());//0L;
        }
        try {
            Date date = mFormat.parse(str);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Long.valueOf(System.currentTimeMillis());
    }
    
    public String convetTime(long modify) {
        return mFormat.format(new Date(modify));
    }
    public void installApk(File file) {
        // ��װ
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        try {
            mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void uninstallApk() {
        // ж�أ�
        // Environmentӵ��һЩ���Ի�ȡ���������ķ���
        // package:com.demo.CanavaCancel �����ʽ�� package:����������·�� (����+������).
        Uri packageURI = Uri.parse("package:com.demo.CanavaCancel");
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        try {
            mContext.startActivity(uninstallIntent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    // ����apk�������
    public File downLoadFile(String httpUrl) {
        final String fileName = "updata.apk";
        File tmpFile = new File("/sdcard/update");
        if (!tmpFile.exists()) {
            tmpFile.mkdir();
        }
        final File file = new File("/sdcard/update/" + fileName);

        try {
            URL url = new URL(httpUrl);
            try {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buf = new byte[256];
                conn.connect();
                double count = 0;
                if (conn.getResponseCode() >= 400) {
                    Toast.makeText(mContext, "���ӳ�ʱ", Toast.LENGTH_SHORT).show();
                } else {
                    while (count <= 100) {
                        if (is != null) {
                            int numRead = is.read(buf);
                            if (numRead <= 0) {
                                break;
                            } else {
                                fos.write(buf, 0, numRead);
                            }

                        } else {
                            break;
                        }

                    }
                }

                conn.disconnect();
                fos.close();
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block

                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }

        return file;
    }

    /** �����ķ���,����ȡ���ɹ� */
    // ��ʵ���Եģ���appInfo.publicSourceDir = "file.getAbsolutePath()";
    public Drawable getUninatllApkInfo(Context context, String archiveFilePath, File file) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(archiveFilePath, 0);// PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            // ������Ϊ����δ��װӦ��ApplicationInfoδ�ܻ�ȡ����Ӧ��source path��
            // ֻҪ��getApplicationIcon()ǰ����source path���ɣ�
            // add-absolute path of app
            appInfo.publicSourceDir = archiveFilePath;
            Drawable d = pm.getApplicationIcon(appInfo);
            return d;
        }
        return null;
    }
    
    public PackageInfo getPackageInfo(Context context, String archiveFilePath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(archiveFilePath, 0);// PackageManager.GET_ACTIVITIES);

        return info;
    }

    //
    private void showUninstallAPKIcon(String apkPath) {
        String PATH_PackageParser = "android.content.pm.PackageParser";
        String PATH_AssetManager = "android.content.res.AssetManager";
        try {
            // apk�����ļ�·��
            // ����һ��Package ������, �����ص�
            // ���캯���Ĳ���ֻ��һ��, apk�ļ���·��
            // PackageParser packageParser = new PackageParser(apkPath);
            Class pkgParserCls = Class.forName(PATH_PackageParser);
            Class[] typeArgs = new Class[1];
            typeArgs[0] = String.class;
            Constructor pkgParserCt = pkgParserCls.getConstructor(typeArgs);
            Object[] valueArgs = new Object[1];
            valueArgs[0] = apkPath;
            Object pkgParser = pkgParserCt.newInstance(valueArgs);
            Log.d("ANDROID_LAB", "pkgParser:" + pkgParser.toString());
            // ���������ʾ�йص�, �����漰��һЩ������ʾ�ȵ�, ����ʹ��Ĭ�ϵ����
            DisplayMetrics metrics = new DisplayMetrics();
            metrics.setToDefaults();
            // PackageParser.Package mPkgInfo = packageParser.parsePackage(new
            // File(apkPath), apkPath,
            // metrics, 0);
            typeArgs = new Class[4];
            typeArgs[0] = File.class;
            typeArgs[1] = String.class;
            typeArgs[2] = DisplayMetrics.class;
            typeArgs[3] = Integer.TYPE;
            Method pkgParser_parsePackageMtd = pkgParserCls.getDeclaredMethod("parsePackage",
                    typeArgs);
            valueArgs = new Object[4];
            valueArgs[0] = new File(apkPath);
            valueArgs[1] = apkPath;
            valueArgs[2] = metrics;
            valueArgs[3] = 0;
            Object pkgParserPkg = pkgParser_parsePackageMtd.invoke(pkgParser, valueArgs);
            // Ӧ�ó�����Ϣ��, ���������, ������Щ����, ����û����
            // ApplicationInfo info = mPkgInfo.applicationInfo;
            Field appInfoFld = pkgParserPkg.getClass().getDeclaredField("applicationInfo");
            ApplicationInfo info = (ApplicationInfo) appInfoFld.get(pkgParserPkg);
            // uid ���Ϊ"-1"��ԭ����δ��װ��ϵͳδ������Uid��
            Log.d("ANDROID_LAB", "pkg:" + info.packageName + " uid=" + info.uid);
            // Resources pRes = getResources();
            // AssetManager assmgr = new AssetManager();
            // assmgr.addAssetPath(apkPath);
            // Resources res = new Resources(assmgr, pRes.getDisplayMetrics(),
            // pRes.getConfiguration());
            Class assetMagCls = Class.forName(PATH_AssetManager);
            Constructor assetMagCt = assetMagCls.getConstructor((Class[]) null);
            Object assetMag = assetMagCt.newInstance((Object[]) null);
            typeArgs = new Class[1];
            typeArgs[0] = String.class;
            Method assetMag_addAssetPathMtd = assetMagCls.getDeclaredMethod("addAssetPath",
                    typeArgs);
            valueArgs = new Object[1];
            valueArgs[0] = apkPath;
            assetMag_addAssetPathMtd.invoke(assetMag, valueArgs);
            Resources res = mContext.getResources();
            typeArgs = new Class[3];
            typeArgs[0] = assetMag.getClass();
            typeArgs[1] = res.getDisplayMetrics().getClass();
            typeArgs[2] = res.getConfiguration().getClass();
            Constructor resCt = Resources.class.getConstructor(typeArgs);
            valueArgs = new Object[3];
            valueArgs[0] = assetMag;
            valueArgs[1] = res.getDisplayMetrics();
            valueArgs[2] = res.getConfiguration();
            res = (Resources) resCt.newInstance(valueArgs);
            CharSequence label = null;
            if (info.labelRes != 0) {
                label = res.getText(info.labelRes);
            }
            // if (label == null) {
            // label = (info.nonLocalizedLabel != null) ? info.nonLocalizedLabel
            // : info.packageName;
            // }
            Log.d("ANDROID_LAB", "label=" + label);
            // ������Ƕ�ȡһ��apk�����ͼ��
            if (info.icon != 0) {
                Drawable icon = res.getDrawable(info.icon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
