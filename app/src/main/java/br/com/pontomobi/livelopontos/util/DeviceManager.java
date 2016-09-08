package br.com.pontomobi.livelopontos.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.scottyab.aescrypt.AESCrypt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

import javax.crypto.NoSuchPaddingException;

public class DeviceManager {

    private final String TAG_LOG = "DeviceManager";

    private final String ENTITY_NAME = "con";
    private final String FILE_NAME = "data_elo_";
    private final String PACKAGE = "com.pm.lvlo";

    private static DeviceManager singleton;
    private static Context context;
    private static String baseDir;

    private DeviceManager(Context context) {
        this.context = context;

        baseDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + PACKAGE;
        createFolder(baseDir);
    }

    public static DeviceManager with(Context context) {
        if (singleton == null) {
            synchronized (DeviceManager.class) {
                singleton = new DeviceManager(context);
            }
        }
        return singleton;
    }

    public boolean saveDeviceInfo(String key, String value, String token) {

        boolean result = false;

        String data = key + ":" + value + ":" + token;
        String fileName = getNameFile(key);
        FileOutputStream outputStream = null;

        try {
            File file = new File(baseDir + File.separator + fileName);

            outputStream = new FileOutputStream(file);

            String encryptedMsg = AESCrypt.encrypt(key, data);

            outputStream.write(encryptedMsg.getBytes());

            result = true;

        } catch (NoSuchPaddingException e) {
            e.printStackTrace();

        } catch (GeneralSecurityException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public String loadDeviceInfo(String key) {

        String result = "";
        InputStream inputStream = null;

        String fileName = getNameFile(key);
        String path = baseDir + File.separator + fileName;

        try {
            inputStream = new FileInputStream(path);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                result = AESCrypt.decrypt(key, stringBuilder.toString());

            }
        } catch (IOException e) {
            e.printStackTrace();

        } catch (GeneralSecurityException e) {
            e.printStackTrace();

        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;

    }

    public boolean isDeviceActive(String key, String value) {
        String[] arr = loadDeviceInfo(key).split(":");

        if (arr.length > 2)
            return arr[1].equals(value);

        return false;
    }


    private String getNameFile(String name) {
        name = name + "pmb";
        return FILE_NAME + sha256(name).substring(0, 10);
    }

    private String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void createFolder(String path) {
        File theDir = new File(path);

        if (!theDir.exists()) {
            boolean result = false;

            try {
                theDir.mkdir();
                result = true;
            } catch (SecurityException se) {
                Log.e(TAG_LOG, "Folder not created");
            }
            if (result)
                Log.i(TAG_LOG, "Folder created");
        }
    }

}
