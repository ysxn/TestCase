
package com.codezyw.common;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 无视Https证书是否正确的Java Http Client
 */
public class HttpClientSendPost {

    /**
     * 忽视证书HostName
     */
    private static HostnameVerifier sIgnoreHostnameVerifier = new HostnameVerifier() {
        public boolean verify(String s, SSLSession sslsession) {
            return true;
        }
    };

    /**
     * Ignore Certification
     */
    private static TrustManager sIgnoreCertificationTrustManger = new X509TrustManager() {
        private X509Certificate[] certificates;

        @Override
        public void checkClientTrusted(X509Certificate certificates[],
                String authType) throws CertificateException {
            if (this.certificates == null) {
                this.certificates = certificates;
                System.out.println("init at checkClientTrusted");
            }

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509certificate,
                String s) throws CertificateException {
            if (this.certificates == null) {
                this.certificates = x509certificate;
            }
            if (certificates != null) {
                for (int c = 0; c < certificates.length; c++) {
                    X509Certificate cert = certificates[c];
                    System.out.println(" Server certificate " + (c + 1) + ":");
                    System.out.println("  Subject DN: " + cert.getSubjectDN());
                    System.out.println("  Signature Algorithm: " + cert.getSigAlgName());
                    System.out.println("  Valid from: " + cert.getNotBefore());
                    System.out.println("  Valid until: " + cert.getNotAfter());
                    System.out.println("  Issuer: " + cert.getIssuerDN());
                }
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

    };

    public static String getMethod(String urlString) {

        ByteArrayOutputStream buffer = new ByteArrayOutputStream(512);
        try {

            URL url = new URL(urlString);

            /*
             * use ignore host name verifier
             */
            HttpsURLConnection.setDefaultHostnameVerifier(sIgnoreHostnameVerifier);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            // Prepare SSL Context
            TrustManager[] tm = {
                    sIgnoreCertificationTrustManger
            };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());

            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            connection.setSSLSocketFactory(ssf);

            InputStream reader = connection.getInputStream();
            byte[] bytes = new byte[512];
            int length = reader.read(bytes);

            do {
                buffer.write(bytes, 0, length);
                length = reader.read(bytes);
            } while (length > 0);

            // result.setResponseData(bytes);
            System.out.println(buffer.toString());
            reader.close();

            connection.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }
        String repString = new String(buffer.toByteArray());
        return repString;
    }
}
