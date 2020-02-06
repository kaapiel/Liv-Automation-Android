package br.com.aguido.livautomation.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;

import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rey.material.widget.EditText;

import java.util.ArrayList;

import br.com.aguido.livautomation.Constants;
import br.com.aguido.livautomation.R;
import br.com.aguido.livautomation.helper.SharedPreferencesHelper;
import br.com.aguido.livautomation.service.livautomation.usertransactions.model.UserTransactionsResponse;
import br.com.aguido.livautomation.ui.home.Issue;

public class Util {

    public static Drawable getDrawableByName(Context context, String imageName) {
        if (imageName == null) {
            return null;
        }

        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(imageName, "drawable",
                context.getPackageName());
        return resources.getDrawable(resourceId);
    }


    public static void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
    }

    public static String getTypeTransactionName(String type) {
        if (type.equals(UserTransactionsResponse.TRANSACTION_TYPE_ACCUMULATED)) {
            return UserTransactionsResponse.TRANSACTION_TYPE_ACCUMULATED_NAME;
        } else if (type.equals(UserTransactionsResponse.TRANSACTION_TYPE_RESCUED)) {
            return UserTransactionsResponse.TRANSACTION_TYPE_RESCUED_NAME;
        } else if (type.equals(UserTransactionsResponse.TRANSACTION_TYPE_DOWNLOADS)) {
            return UserTransactionsResponse.TRANSACTION_TYPE_DOWNLOADS_NAME;
        } else if (type.equals(UserTransactionsResponse.TRANSACTION_TYPE_CHARGEBACKS)) {
            return UserTransactionsResponse.TRANSACTION_TYPE_CHARGEBACKS_NAME;
        } else if (type.equals(UserTransactionsResponse.TRANSACTION_TYPE_EXPIRE)) {
            return UserTransactionsResponse.TRANSACTION_TYPE_EXPIRE_NAME;
        } else {
            return "";
        }
    }

    public static float getTextSizePoints(Context context, String pointsText) {
        if (TextUtils.isEmpty(pointsText)) {
            return context.getResources().getDimension(R.dimen.summary_top_points_text_size);
        }


        switch (pointsText.length()) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return context.getResources().getDimension(R.dimen.summary_top_points_text_size_5);
            case 6:
                return context.getResources().getDimension(R.dimen.summary_top_points_text_size_6);
            case 7:
                return context.getResources().getDimension(R.dimen.summary_top_points_text_size_7);
            case 8:
                return context.getResources().getDimension(R.dimen.summary_top_points_text_size_8);
            case 9:
                return context.getResources().getDimension(R.dimen.summary_top_points_text_size_9);
            case 10:
                return context.getResources().getDimension(R.dimen.summary_top_points_text_size_10);
            case 11:
                return context.getResources().getDimension(R.dimen.summary_top_points_text_size_11);
            default:
                return context.getResources().getDimension(R.dimen.summary_top_points_text_size_before_11);
        }

    }

    public static boolean showSnackbarUpdate(View view, Context context, boolean updateInformations) {
        if (!updateInformations) {
            return false;
        }

        Snackbar snackbar = Snackbar
                .make(view, context.getString(R.string.wait_updating_information), Snackbar.LENGTH_LONG);

        snackbar.show();

        return true;
    }

    public static void hideKeyboard(View view, Context context) {

        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean isFingerprintAuthAvailable(FingerprintManager mFingerprintManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return mFingerprintManager.isHardwareDetected()
                    && mFingerprintManager.hasEnrolledFingerprints();
        }

        return false;
    }

    public static boolean checkFingerPrintIsEnalbe(Context context) {
        return SharedPreferencesHelper.read(context, Constants.SharedPrefsKeys.SHARED_PREF_NAME, Constants.SharedPrefsKeys.FINGERPRINT_ENABLE, false);
    }

    public static void saveSessioId(Context context, String sessionId) {
        SharedPreferencesHelper.write(context, Constants.SharedPrefsKeys.SHARED_PREF_NAME, Constants.SharedPrefsKeys.SESSION_ID_USER, sessionId);
    }

    public static String loadSessioId(Context context) {
        return SharedPreferencesHelper.read(context, Constants.SharedPrefsKeys.SHARED_PREF_NAME, Constants.SharedPrefsKeys.SESSION_ID_USER, "");
    }

    public static String getVersion(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return info.versionName.split("-")[0];
    }

    public static String getDeviceId(Context context, String cpf) {
        if (!cpf.isEmpty()) {
            String[] arr = DeviceManager.with(context).loadDeviceInfo(cpf).split(":");
            if (arr.length > 2)
                return arr[1];
        }

        return null;
    }

    public static ArrayList<Issue> parseIssues(String jsonIssues) {

        JsonObject object = (JsonObject) new JsonParser().parse(jsonIssues);
        JsonArray issues = (JsonArray) object.get("issues");

        ArrayList<Issue> is = new ArrayList<>();

        for(int i=0; i<issues.size(); i++){

            Issue issue = new Issue();
            try{
                issue.setKey(issues.get(i).getAsJsonObject().get("key").toString());
            }catch (Exception e){
                issue.setKey(null);
            }

            try{
                issue.setSummary(issues.get(i).getAsJsonObject().get("fields").getAsJsonObject().get("summary").toString());
            }catch (Exception e){
                issue.setSummary(null);
            }

            try{
                issue.setIssueType(issues.get(i).getAsJsonObject().get("fields").getAsJsonObject().get("issuetype").getAsJsonObject().get("name").toString());
            }catch (Exception e){
                issue.setIssueType(null);
            }

            try{
                issue.setComponent(issues.get(i).getAsJsonObject().get("fields").getAsJsonObject().get("components").getAsJsonArray().get(0).getAsJsonObject().get("name").toString());
            }catch (Exception e){
                issue.setComponent(null);
            }

            try{
                issue.setCreated(issues.get(i).getAsJsonObject().get("fields").getAsJsonObject().get("created").toString());
            }catch (Exception e){
                issue.setCreated(null);
            }

            try{
                issue.setDisplayName(issues.get(i).getAsJsonObject().get("fields").getAsJsonObject().get("reporter").getAsJsonObject().get("displayName").toString());
            }catch (Exception e){
                issue.setDisplayName(null);
            }

            try{
                issue.setAssignee(issues.get(i).getAsJsonObject().get("fields").getAsJsonObject().get("assignee").getAsJsonObject().get("displayName").toString());
            }catch (Exception e){
                issue.setAssignee(null);
            }

            try{
                issue.setPriority(issues.get(i).getAsJsonObject().get("fields").getAsJsonObject().get("priority").getAsJsonObject().get("name").toString());
            }catch (Exception e){
                issue.setPriority(null);
            }

            try{
                issue.setResolution(issues.get(i).getAsJsonObject().get("fields").getAsJsonObject().get("resolution").getAsJsonObject().get("name").toString());
            }catch (Exception e){
                issue.setResolution(null);
            }

            try{
                issue.setSprint(issues.get(i).getAsJsonObject().get("fields").getAsJsonObject().get("customfield_10015").getAsJsonObject().get("value").toString());
            }catch (Exception e){
                issue.setSprint(null);
            }

            try{
                issue.setStatus(issues.get(i).getAsJsonObject().get("fields").getAsJsonObject().get("status").getAsJsonObject().get("name").toString());
            }catch (Exception e){
                issue.setStatus(null);
            }

            is.add(issue);
        }

        return is;
    }
}
