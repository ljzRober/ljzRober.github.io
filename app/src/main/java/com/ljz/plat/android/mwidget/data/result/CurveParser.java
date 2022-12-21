package com.ljz.plat.android.mwidget.data.result;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ljz.plat.android.mwidget.data.model.CurveData;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class CurveParser implements Parser<Map<String, CurveData>> {
   public static final String TAG = "XmlDataParser";

   public Map<String, CurveData> parse(@NonNull String str) throws Exception {
      // 1.创建Reader对象
      SAXReader reader = new SAXReader();
      InputStream inputStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
      // 2.加载xml
      Document document = reader.read(inputStream);
      // 3.获取根节点
      HashMap<String, CurveData> hashMap = new HashMap<>();
      parseElement(document.getRootElement(), hashMap, "0");
      Log.d(TAG, "parse: " + hashMap.toString());
      return hashMap;
   }

   public static void parseElement(@NonNull Element element,
                                   @NonNull Map<String, CurveData> map,
                                   @NonNull String key)
   {
      for (Iterator<Element> elementIterator = element.elementIterator(); elementIterator.hasNext();) {
         Element child = (Element) elementIterator.next();
         // 获得元素节点名字
         String name = child.getName();
         if (Objects.equals(name, "CodeIndex")) {
            Attribute attribute = child.attribute("Code");
            if (attribute != null && !map.containsKey(attribute.getValue())) {
               key = attribute.getValue();
               map.put(key, new CurveData(key));
            }
         }
         if (child.isTextOnly()) {
            // 直接获得此属性节点的文本值
            String textTrim = child.getTextTrim();
            Attribute attribute = child.getParent().attribute("Id");
            if (Objects.equals(attribute.getValue(), "10") && map.containsKey(key)) {
               Objects.requireNonNull(map.get(key)).addData(Float.valueOf(textTrim));
            }
         } else {
            // 将此属性节点传入此方法递归解析
            parseElement(child, map, key);
         }
      }
   }
}
