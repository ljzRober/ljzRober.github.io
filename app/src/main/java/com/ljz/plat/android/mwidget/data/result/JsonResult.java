package com.ljz.plat.android.mwidget.data.result;

import androidx.annotation.NonNull;
import org.json.JSONObject;

public class JsonResult {

   private final String mCode;
   private final String mMessage;
   private final JSONObject mJSONObject;

   public JsonResult(@NonNull String str) throws Exception {
      final JSONObject jsonObject = new JSONObject(str);
      mCode = jsonObject.getString("status_code");
      mMessage = jsonObject.getString("status_msg");
      mJSONObject = jsonObject.getJSONObject("data");
   }

   public String getCode() {
      return mCode;
   }

   public String getMessage() {
      return mMessage;
   }

   public JSONObject getJSONObject() {
      return mJSONObject;
   }
}
