package tk.tnicy.tradisilation.util;

import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import tk.tnicy.tradisilation.db.Translation;

public class Utility {
    public static boolean handleJson(String response) {
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allTranslations = new JSONArray(response);
                for (int i = 0; i < allTranslations.length(); i++) {
                    JSONObject translationsJSONObject = allTranslations.getJSONObject(i);
                    Translation translation = new Translation();
                    translation.setId(translationsJSONObject.getInt("id"));
                    translation.setChi(translationsJSONObject.getString("chi"));
                    translation.setEng(translationsJSONObject.getString("eng"));
                    translation.setBigType(translationsJSONObject.getString("bigType"));
                    translation.setSmallType(translationsJSONObject.getString("smallType"));
                    translation.setDetail(translationsJSONObject.getString("detail"));
                    translation.setRelated(translationsJSONObject.getString("related"));
                    translation.save();
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
