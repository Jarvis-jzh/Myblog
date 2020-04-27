package com.jzh.myblog.util;

import org.springframework.stereotype.Component;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/4 17:47
 * @description markdown截取文章生成摘要
 */
@Component
public class BuildArticleTabloidUtil {

    public String buildArticleTabloid(String htmlArticleComment){

        String regex = "\\s+";
        String str = htmlArticleComment.trim();
        //去掉所有空格
        String articleTabloid = str.replaceAll(regex,"");

        int beginIndex = articleTabloid.indexOf("<");
        int endIndex = articleTabloid.indexOf(">");
        StringBuilder myArticleTabloid = new StringBuilder();
        String nowStr = "";
        while (beginIndex != -1){
            nowStr = articleTabloid.substring(0, beginIndex);
            if(nowStr.length() > 197){
                nowStr = nowStr.substring(0,197);
                myArticleTabloid.append(nowStr);
            } else {
                myArticleTabloid.append(nowStr);
            }

            articleTabloid = articleTabloid.substring(endIndex + 1);
            beginIndex = articleTabloid.indexOf("<");
            if(myArticleTabloid.length() < 197){
                //过滤掉<pre>标签中的代码块
                if(articleTabloid.length() > 4){
                    if(articleTabloid.charAt(beginIndex) == '<' && articleTabloid.charAt(beginIndex+1) == 'p'  && articleTabloid.charAt(beginIndex+2) == 'r'  && articleTabloid.charAt(beginIndex+3) == 'e' ){
                        endIndex = articleTabloid.indexOf("</pre>");
                        endIndex = endIndex + 5;
                    } else {
                        endIndex = articleTabloid.indexOf(">");
                    }
                } else {
                    endIndex = articleTabloid.indexOf(">");
                }
            } else {
                break;
            }

        }

        String myArticleTabloidStr = myArticleTabloid.toString();

        if(myArticleTabloidStr.length() > 197){
            myArticleTabloidStr = myArticleTabloidStr.substring(0, 197);
        }

        return myArticleTabloidStr + "...";
    }
}
