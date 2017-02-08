package hmap.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;

public class PinyinUtils {
  private static HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
  private static String[] pinyin = null;

  private PinyinUtils() {}

  private static HanyuPinyinOutputFormat getFormat() {
    pinyin = null;
    format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    return format;
  }

  // 转换单个中文字符为多音字数组
  private static List<String> getCharacterPinYinSet(char c) {
    try {
      pinyin = PinyinHelper.toHanyuPinyinStringArray(c, getFormat());
    } catch (BadHanyuPinyinOutputFormatCombination e) {
      e.printStackTrace();
    }
    if (null == pinyin) {
      pinyin = new String[1];
      pinyin[0] = String.valueOf(c);
    }

    Set<String> set = new HashSet<String>(Arrays.asList(pinyin));
    List<String> list = new ArrayList<String>();
    list.addAll(set);
    return list;
  }

  // 转换单个中文字符为常用拼音
  private static List<String> getCharacterPinYinTitleSet(char c) {
    try {
      pinyin = PinyinHelper.toHanyuPinyinStringArray(c, getFormat());
    } catch (BadHanyuPinyinOutputFormatCombination e) {
      e.printStackTrace();
    }
    if (null == pinyin) {
      pinyin = new String[1];
      pinyin[0] = String.valueOf(c);
    }
    String[] p = new String[pinyin.length];
    for (int i = 0; i < pinyin.length; i++) {
      p[i] = pinyin[i].substring(0, 1);
    }
    Set<String> set = new HashSet<String>(Arrays.asList(p));
    List<String> list = new ArrayList<String>();
    list.addAll(set);
    return list;
  }

  public static String getPinyin(String name) {
    name = name.trim().replace(" ", "");
    List<String> standardList = new ArrayList<String>();
    int n = name.length();
    for (int i = 0; i < n; i++) {
      List<String> l = getCharacterPinYinSet(name.charAt(i));
      if (l != null) {

        int num = l.size();

        if (i == 0) {
          for (int k = 0; k < num; k++) {
            standardList.add(l.get(k));
          }
        } else {
          List<String> elseList = new ArrayList<String>();
          for (int k = 0; k < num; k++) {
            List<String> list = new ArrayList<String>();
            list.addAll(standardList);
            for (int m = 0; m < list.size(); m++) {
              list.set(m, list.get(m) + l.get(k));
            }
            elseList.addAll(list);
          }
          standardList = elseList;

        }
      } else {
        return name;
        // standardList.add(String.valueOf(name.charAt(i)));
      }

    }
    StringBuilder returnString = new StringBuilder();
    returnString.append("|");
    for (int i = 0; i < standardList.size(); i++) {
      returnString.append(standardList.get(i) + "|");
    }

    return returnString.toString();
  }

  public static String getPinyinCapital(String name) {
    name = name.trim().replace(" ", "");
    List<String> standardList = new ArrayList<String>();
    int n = name.length();
    for (int i = 0; i < n; i++) {
      List<String> l = getCharacterPinYinTitleSet(name.charAt(i));
      if (l != null) {

        int num = l.size();

        if (i == 0) {
          for (int k = 0; k < num; k++) {
            standardList.add(l.get(k));
          }
        } else {
          List<String> elseList = new ArrayList<String>();
          for (int k = 0; k < num; k++) {
            List<String> list = new ArrayList<String>();
            list.addAll(standardList);
            for (int m = 0; m < list.size(); m++) {
              list.set(m, list.get(m) + l.get(k));
            }
            elseList.addAll(list);
          }
          standardList = elseList;

        }
      } else {
        return name;
      }

    }
    StringBuilder returnString = new StringBuilder();
    returnString.append("|");
    for (int i = 0; i < standardList.size(); i++) {
      returnString.append(standardList.get(i) + "|");
    }

    return returnString.toString();
  }

  public static void main(String[] args) {
    String name = "上海汉得信息技术股份有限公司";
    // PinyinUtils c = new PinyinUtils();
//    String a = PinyinUtils.getPinyin(name);
    RandomValueStringGenerator generator = new RandomValueStringGenerator(4);

    List<String> results=new ArrayList<String>();
    for(int i=0;i<10000;i++){

      String code = generator.generate();
      results.add(code);
      System.out.println(code);
    }

  }

}
