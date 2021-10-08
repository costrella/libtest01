package com.comarch.braceletes.dms;

import android.text.TextUtils;

import com.google.gson.JsonObject;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;

public class DMSUtils {

    public static boolean valid(DMSConfigDto dmsConfigDto) {
        if (dmsConfigDto == null) return false;
        return (isValid(dmsConfigDto.getIotpProject())
                && isValid(dmsConfigDto.getIotpChannelId())
                && isValid(dmsConfigDto.getIotpTenant())
                && isValid(dmsConfigDto.getMqttPasswd())
                && isValid(dmsConfigDto.getMqttUrl())
                && isValid(dmsConfigDto.getMqttUser())
        );
    }

    private static boolean isValid(String value) {
        return (!TextUtils.isEmpty(value) && !containsWhiteSpace(value));
    }

    public static String parse(JsonObject jobj, String fieldName) { //todo utils
        if (jobj.get(fieldName) != null) {
            return jobj.get(fieldName).getAsString();
        }
        return null;
    }

    public static boolean containsWhiteSpace(final String testCode) {
        if (testCode != null) {
            for (int i = 0; i < testCode.length(); i++) {
                if (Character.isWhitespace(testCode.charAt(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getTokenForDMS(String dmsDeviceToken, String serial) {
        byte[] tokenBytes = android.util.Base64.decode(dmsDeviceToken.getBytes(), android.util.Base64.DEFAULT);
        byte[] sum = ArrayUtils.addAll(serial.getBytes(), tokenBytes);
        return new String(Hex.encodeHex(DigestUtils.sha(sum)));
    }
}
