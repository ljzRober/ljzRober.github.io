package com.ljz.plat.android.mwidget.data.result;

import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;

import com.ljz.plat.android.mwidget.data.model.CurveData;
import com.ljz.plat.android.mwidget.data.model.LargeWidget;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DataParser implements Parser<List<LargeWidget>> {

    public static final String TAG = "DataParser";

    @Override
    public List<LargeWidget> parse(String str) throws Exception {
        // 1.创建Reader对象
        SAXReader reader = new SAXReader();
        InputStream inputStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
        // 2.加载xml
        Document document = reader.read(inputStream);
        // 3.获取根节点
        List<LargeWidget> list = new ArrayList<>();
        parseElement(document.getRootElement(), list);
        return list;
    }

    public static void parseElement(@NonNull Element element,
                                    @NonNull List<LargeWidget> list)
    {
        for (Iterator<Element> elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Element child = (Element) elementIterator.next();
            // 获得元素节点名字
            String name = child.getName();
            if (Objects.equals(name, "Record")) {
                list.add(new LargeWidget());
            }
            if (child.isTextOnly()) {
                // 直接获得此属性节点的文本值
                String textTrim = child.getTextTrim();
                Attribute attribute = child.getParent().attribute("Id");
                if (!list.isEmpty()) {
                    list.get(list.size() - 1).addParam(new Pair<>(attribute.getValue(), textTrim));
                }
            } else {
                // 将此属性节点传入此方法递归解析
                parseElement(child, list);
            }
        }
    }
}
