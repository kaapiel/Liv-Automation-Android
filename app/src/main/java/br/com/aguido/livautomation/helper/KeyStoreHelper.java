package br.com.aguido.livautomation.helper;

import android.content.Context;
import android.security.KeyPairGeneratorSpec;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.security.auth.x500.X500Principal;

import br.com.aguido.livautomation.Constants;
import br.com.aguido.livautomation.LivAutomationApp;
import br.com.aguido.livautomation.model.User;

/**
 * Created by selemafonso on 22/04/16.
 */
public class KeyStoreHelper {
    private final String ALIAS_KEY_STORE = "alias_key_store";

    private Context context;
    private KeyStore keyStore;

    public KeyStoreHelper(Context context) {
        this.context = context;
    }

    public String generateKey(String password) {
        User user = new User(LivAutomationApp.getInstance().getCpf(), password);
        Gson gson = new Gson();
        String initText = gson.toJson(user);
        initKeyStore();

        try {
            if (keyStore.containsAlias(ALIAS_KEY_STORE)) {
                keyStore.deleteEntry(ALIAS_KEY_STORE);
                createKeyStore();
            } else {
                createKeyStore();
            }
        } catch (Exception e) {
        }

        return encryptString(initText);
    }

    public User recoverKey() {
        initKeyStore();

        String encryptedText = recoverEncryptText();
        String json = decryptString(encryptedText);
        User user = null;
        if (!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
            user = gson.fromJson(json, User.class);
        }
        return user;
    }

    private void initKeyStore() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
        } catch (Exception e) {
        }
    }

    private void createKeyStore() {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return;
        }

        try {
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            end.add(Calendar.YEAR, 1);
            KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(context)
                    .setAlias(ALIAS_KEY_STORE)
                    .setSubject(new X500Principal("CN=Sample Name, O=Android Authority"))
                    .setSerialNumber(BigInteger.ONE)
                    .setStartDate(start.getTime())
                    .setEndDate(end.getTime())
                    .build();
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
            generator.initialize(spec);

            KeyPair keyPair = generator.generateKeyPair();
        } catch (Exception e) {
        }
    }

    private String encryptString(String startText) {
        try {
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry)keyStore.getEntry(ALIAS_KEY_STORE, null);
            RSAPublicKey publicKey = (RSAPublicKey) privateKeyEntry.getCertificate().getPublicKey();

            String initialText = startText;
            Cipher inCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidOpenSSL");
            inCipher.init(Cipher.ENCRYPT_MODE, publicKey);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CipherOutputStream cipherOutputStream = new CipherOutputStream(
                    outputStream, inCipher);
            cipherOutputStream.write(initialText.getBytes("UTF-8"));
            cipherOutputStream.close();

            byte [] vals = outputStream.toByteArray();

            String finalText = (Base64.encodeToString(vals, Base64.DEFAULT));

            if(!TextUtils.isEmpty(finalText)) {
                saveEncryptText(finalText);
            }

            return finalText;
        } catch (Exception e) {
        }

        return "";
    }

    private String decryptString(String encryptedText) {
        try {

            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(ALIAS_KEY_STORE, null);
            Cipher output = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            output.init(Cipher.DECRYPT_MODE, privateKeyEntry.getPrivateKey());

            String cipherText = encryptedText;
            CipherInputStream cipherInputStream = new CipherInputStream(
                    new ByteArrayInputStream(Base64.decode(cipherText, Base64.DEFAULT)), output);
            ArrayList<Byte> values = new ArrayList<>();
            int nextByte;
            while ((nextByte = cipherInputStream.read()) != -1) {
                values.add((byte)nextByte);
            }

            byte[] bytes = new byte[values.size()];
            for(int i = 0; i < bytes.length; i++) {
                bytes[i] = values.get(i).byteValue();
            }

            String finalText = new String(bytes, 0, bytes.length, "UTF-8");
            return finalText;

        } catch (Exception e) {
        }

        return "";
    }

    private void saveEncryptText(String finalText) {
        SharedPreferencesHelper.write(context, Constants.SharedPrefsKeys.SHARED_PREF_NAME, Constants.SharedPrefsKeys.FINGERPRINT_PASS, finalText);
    }

    private String recoverEncryptText() {
        return SharedPreferencesHelper.read(context, Constants.SharedPrefsKeys.SHARED_PREF_NAME, Constants.SharedPrefsKeys.FINGERPRINT_PASS, null);
    }


}
