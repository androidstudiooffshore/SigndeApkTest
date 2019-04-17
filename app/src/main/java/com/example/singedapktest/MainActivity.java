package com.example.singedapktest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.security.MessageDigest;


public class MainActivity extends AppCompatActivity {

TextView tvDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDetails=findViewById(R.id.tv_details);
        String singedDetails=getSignedDetails();
        tvDetails.setText(singedDetails);
    }


    private String getSignedDetails() {
        StringBuilder builder=new StringBuilder();
        try {
            PackageManager pm = getApplication().getPackageManager();
           // SigningInfo signingInfo = pm.getPackageInfo("com.example.singedapktest", PackageManager.GET_SIGNATURES).signingInfo;

            Signature sig = pm.getPackageInfo("com.example.singedapktest", PackageManager.GET_SIGNATURES).signatures[0];
            String md5Fingerprint = doFingerprint(sig.toByteArray(), "MD5");builder.append("Md5: "+md5Fingerprint+"\n");
            builder.append("--------------------------\n");
            String sha1 = doFingerprint(sig.toByteArray(), "SHA1");
            builder.append("SHA1: "+sha1+"\n");
            builder.append("--------------------------\n");
            String sha256 = doFingerprint(sig.toByteArray(), "SHA-256");
            builder.append("SHA-256: "+sha256+"\n");

            } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    protected static String doFingerprint(byte[] certificateBytes, String algorithm)
            throws Exception {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(certificateBytes);
        byte[] digest = md.digest();

        String toRet = "";
        for (int i = 0; i < digest.length; i++) {
            if (i != 0)
                toRet += ":";
            int b = digest[i] & 0xff;
            String hex = Integer.toHexString(b);
            if (hex.length() == 1)
                toRet += "0";
            toRet += hex;
        }
        return toRet;
    }
}
