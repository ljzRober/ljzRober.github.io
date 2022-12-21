package com.ljz.plat.android.mwidget.data.result;

import com.google.gson.Gson;
import com.ljz.plat.android.mwidget.data.model.Num;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NumParser implements Parser<Map<String, Num>> {
    @Override
    public Map<String, Num> parse(String str) throws Exception {
        JsonResult jsonResult = new JsonResult(str);
        JSONObject jsonObject = jsonResult.getJSONObject();
        Iterator<String> keys = jsonObject.keys();
        Map<String, Num> result = new HashMap<>();
        while (keys.hasNext()) {
            String key = keys.next();
            String code = key.split("_")[0];
            Num num = new Gson().fromJson(String.valueOf(jsonObject.get(key)), Num.class);
            result.put(code, num);
        }
        return result;
    }
}
