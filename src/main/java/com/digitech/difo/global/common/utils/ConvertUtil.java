package com.digitech.difo.global.common.utils;

import java.util.ArrayList;
import java.util.List;

public class ConvertUtil {
    public static List<String> StringToArray(String str) {
        String newStr = str.replaceAll("[\\[\\[\\]]","").replaceAll(" ", ""); // 대괄호 제거
        
        List<String> list = List.of(newStr.split(","));
        return list;
    }
}
