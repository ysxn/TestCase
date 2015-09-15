
package com.codezyw.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.util.Log;

/**
 * 文件的路径如下：<br>
 * 通过读取文件/proc/cpuinfo系统CPU的类型等多种信息。<br>
 * 可通过读取/proc/stat 所有CPU活动的信息来计算CPU使用率。
 */
public class CpuHelper {
    private static final String TAG = "cpu";

    /**
     * 一、在电脑上创建一个文本文档。用写字板或者用Notepad++打开。（不要用记事本打开）<br>
     * 二、在里面复制下面的代码（最好一条一条的复制）<br>
     * #!/system/bin/sh<br>
     * echo 192000 > /sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq<br>
     * echo 1188000 > /sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq<br>
     * echo 1 > /sys/devices/system/cpu/cpu1/online<br>
     * echo 192000 > /sys/devices/system/cpu/cpu1/cpufreq/scaling_min_freq<br>
     * echo 1188000 > /sys/devices/system/cpu/cpu1/cpufreq/scaling_max_freq<br>
     * 做成和下面这个图片一样。中间不要插入多余的字符。<br>
     * 然后保存文件并删除掉.TXT（后缀名）并复制到内存卡<br>
     * 三、用R.E管理器把这个文件复制到system/etc/init.d目录下。 并把权限设置成777（也就是全部打勾。）<br>
     */

    /**
     * CPU工作模式:
     */
    public static String[] getCpuWorkMode() {
        String[] result = new String[] {};
        ProcessBuilder cmd;
        try {
            String[] args = {
                    "/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/scaling_available_governors"
            };
            cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String line = reader.readLine();
            return new String[] {
                    line
            };
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * CPU可用频率：<br>
     * 384000 486000 594000 702000 810000 918000 1026000 1134000 1242000 1350000
     * 1458000 1566000 1674000 1728000
     */
    public static String[] getCpuFreqMode() {
        String[] result = new String[] {};
        ProcessBuilder cmd;
        try {
            String[] args = {
                    "/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/scaling_available_frequencies"
            };
            cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String line = reader.readLine();
            return new String[] {
                    line
            };
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 工作的cpu核心数：
     */
    public static int getOnlineCpu() {
        ProcessBuilder cmd;
        try {
            String[] args = {
                    "/system/bin/cat",
                    "/sys/devices/system/cpu/online"
            };
            cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String line = reader.readLine();
            return (int) StringUtils.parseLongSafe(line, 10);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取CPU最大频率：<br>
     * // "/system/bin/cat" 命令行 <br>
     * // "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq" 存储最大频率的文件的路径
     * 
     * @return
     */
    public static long getCpuFrequence() {
        ProcessBuilder cmd;
        try {
            String[] args = {
                    "/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"
            };
            cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String line = reader.readLine();
            return StringUtils.parseLongSafe(line, 10, 0);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    // "/system/bin/cat" 命令行
    // "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq" 存储最大频率的文件的路径
    /**
     * 获取CPU最大频率（单位KHZ）
     * 
     * @return
     */
    public static String getMaxCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {
                    "/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"
            };
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        return result.trim();
    }

    // 获取CPU最小频率（单位KHZ）
    public static String getMinCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {
                    "/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"
            };
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        return result.trim();
    }

    // 实时获取CPU当前频率（单位KHZ）
    public static String getCurCpuFreq() {
        String result = "N/A";
        BufferedReader br = null;
        try {
            FileReader fr = new FileReader(
                    "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    // 获取CPU名字
    public static String getCpuName() {
        BufferedReader br = null;
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            for (int i = 0; i < array.length; i++) {
            }
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 内存：/proc/meminfo：
     */
    public void getTotalMemory() {
        String str1 = "/proc/meminfo";
        String str2 = "";
        BufferedReader localBufferedReader = null;
        try {
            FileReader fr = new FileReader(str1);
            localBufferedReader = new BufferedReader(fr, 8192);
            while ((str2 = localBufferedReader.readLine()) != null) {
                Log.i(TAG, "---" + str2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (localBufferedReader != null) {
                try {
                    localBufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Rom大小
     * 
     * @return
     */
    public long[] getRomMemroy() {
        long[] romInfo = new long[2];
        // Total rom memory
        romInfo[0] = getTotalInternalMemorySize();

        // Available rom memory
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        romInfo[1] = blockSize * availableBlocks;
        getVersion();
        return romInfo;
    }

    /**
     * Rom大小
     * 
     * @return
     */
    public long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    /**
     * 系统的版本信息：
     * 
     * @return
     */
    public String[] getVersion() {
        String[] version = {
                "null", "null", "null", "null"
        };
        String str1 = "/proc/version";
        String str2;
        String[] arrayOfString;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            version[0] = arrayOfString[2];// KernelVersion
            localBufferedReader.close();
        } catch (IOException e) {
        }
        version[1] = Build.VERSION.RELEASE;// firmware version
        version[2] = Build.MODEL;// model
        version[3] = Build.DISPLAY;// system version
        return version;
    }

    /**
     * sdCard大小
     * 
     * @return
     */
    public long[] getSDCardMemory() {
        long[] sdCardInfo = new long[2];
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long bSize = sf.getBlockSize();
            long bCount = sf.getBlockCount();
            long availBlocks = sf.getAvailableBlocks();

            sdCardInfo[0] = bSize * bCount;// 总大小
            sdCardInfo[1] = bSize * availBlocks;// 可用大小
        }
        return sdCardInfo;
    }

    /**
     * mac地址
     * 
     * @return
     */
    public String[] getOtherInfo(Context context) {
        String[] other = {
                "null", "null"
        };
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getMacAddress() != null) {
            other[0] = wifiInfo.getMacAddress();
        } else {
            other[0] = "Fail";
        }
        other[1] = getTimes();
        return other;
    }

    /**
     * 开机时间
     * 
     * @param mContext
     * @return
     */
    private String getTimes() {
        long ut = SystemClock.elapsedRealtime() / 1000;
        if (ut == 0) {
            ut = 1;
        }
        int m = (int) ((ut / 60) % 60);
        int h = (int) ((ut / 3600));
        return h + " 小时" + m + " 分钟";
    }

    /**
     * @param first
     * @param mypidStat
     * @param total
     * @param pattern
     */
    private static void updateCpuStat(boolean first, long mypidStat, long total, Pattern pattern) {
        ProcessBuilder cmd;
        long oldMypidStat = first ? 0 : mypidStat;
        long oldTotal = first ? 0 : total;

        try {
            StringBuilder builder = new StringBuilder();
            int pid = android.os.Process.myPid();
            String[] args = {
                    "/system/bin/cat", "/proc/" + pid + "/stat"
            };
            cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[1024];
            int len;
            while ((len = in.read(re)) != -1) {
                builder.append(new String(re, 0, len));
            }
            // Log.i(TAG, builder.toString());
            in.close();

            String[] stats = builder.toString().split(" +", -1);
            if (stats.length >= 17) {
                long utime = StringUtils.parseLongSafe(stats[13], 10);
                long stime = StringUtils.parseLongSafe(stats[14], 10);
                long cutime = StringUtils.parseLongSafe(stats[15], 10);
                long cstime = StringUtils.parseLongSafe(stats[16], 10);
                // Log.i(TAG, String.format(
                // "utime:%d, stime:%d, cutime:%d, cstime:%d", utime,
                // stime, cutime, cstime));
                mypidStat = utime + stime + cutime + cstime;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            String[] args = {
                    "/system/bin/cat", "/proc/stat"
            };
            cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    String[] stats = line.split(" +", -1);
                    long tmpTotal = 0;
                    for (int i = 1; i < stats.length; i++) {
                        tmpTotal += StringUtils.parseLongSafe(stats[i], 10);
                    }
                    total = tmpTotal;
                }
                break;
            }
            if (first) {
                first = false;
            } else {
                Log.i(TAG, String.format("vsir cpu usage: %2.1f%%",
                        (double) (mypidStat - oldMypidStat)
                                / (total - oldTotal) * 100));
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * CPU个数
     * 
     * @return
     */
    private static int getNumCores() {
        try {
            // Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            // Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            Log.d(TAG, "CPU Count: " + files.length);
            // Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
            // Print exception
            Log.d(TAG, "CPU Count: Failed.");
            e.printStackTrace();
            // Default to return 1 core
            return 1;
        }
    }

    // Private Class to display only CPU devices in the directory listing
    public static class CpuFilter implements FileFilter {
        @Override
        public boolean accept(File pathname) {
            // Check if filename is "cpu", followed by a single digit number
            if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                return true;
            }
            return false;
        }
    }
}
