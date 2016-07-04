package com.yiwugou.reb.api.utils;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author zhanxiaoyong@yiwugou.com
 * 
 * @since 2016年6月17日 上午11:17:55
 */
public class PagerUtils {

    public static final String PAGE_BEGIN = "<div style=\"margin-right:20px;\"><div class=\"xyz-pager mb10\"><div class=\"page_right\"><ul class=\"right\">";
    public static final String PAGE_END = "</ul></div></div></div>";

    public static final String PREV = "Prev";
    public static final String NEXT = "Next";

    public static final String PAGE_LAST_YES = "<a class='page_last_yes' href=\"{0}\">{1}</a>";
    public static final String PAGE_LAST_NO = "<span class='page_last_no'>{0}</span>";
    public static final String PAGE_NEXT_YES = "<a class='page_next_yes' href=\"{0}\">{1}</a>";
    public static final String PAGE_NEXT_NO = "<span class='page_next_no'>{0}</span>";
    public static final String PAGE_YES = "<span class='page_yes'>{0}</span>";
    public static final String PAGE_NO = "<a class='page_no' href=\"{0}\">{1}</a>";
    public static final String PAGE_MORE = "<span class='font0550ff page_more'>…</span>";
    public static final String PAGE_SKIP = "<span class='page_skip'><p class='font999 page_sup'><input type='hidden' id='pageUrlFormat' value=\"{0}\">{1}<input type='text' size='5' class='page_input' name='cpage' id='cpage' value=\"{2}\">{3}</p><input id='pageConfirm' type='button' value=\"{4}\"><p></p></span>";

    public static String pageStr(Pager<?> pager, HttpServletRequest request, String formatUrl) {
        int currPage = pager.getCurrPage();
        int totalPage = pager.getTotalPage();

        StringBuffer sb = new StringBuffer();

        sb.append(PAGE_BEGIN);
        // 页码少于2个
        if (totalPage < 2) {
            return "";
        }

        if (currPage == 1) { // 第一页
            sb.append(MessageFormat.format(PAGE_LAST_NO, PREV));
        } else {
            sb.append(MessageFormat.format(PAGE_LAST_YES, processUrl(request, currPage - 1, formatUrl), PREV));
        }
        if (totalPage < 8) {
            for (int j = 1; j < (totalPage + 1); j++) {
                if (j == currPage) {
                    sb.append(MessageFormat.format(PAGE_YES, j));
                } else {
                    sb.append(MessageFormat.format(PAGE_NO, processUrl(request, j, formatUrl), j));
                }
            }
        } else {
            if (currPage < 6) {// 第一种条件，当前条小于6
                for (int j = 1; j < 7; j++) {
                    if (j == currPage) {
                        sb.append(MessageFormat.format(PAGE_YES, j));
                    } else {
                        sb.append(MessageFormat.format(PAGE_NO, processUrl(request, j, formatUrl), j));
                    }
                }
                sb.append(PAGE_MORE);
                sb.append(MessageFormat.format(PAGE_NO, processUrl(request, totalPage, formatUrl), totalPage));
            } else if (currPage >= 5 && (totalPage - currPage) <= 5) {// 显示前面...
                sb.append(MessageFormat.format(PAGE_NO, processUrl(request, 1, formatUrl), 1));
                sb.append(PAGE_MORE);
                for (int j = (totalPage - 5); j < (totalPage + 1); j++) {
                    if (j == currPage) {
                        sb.append(MessageFormat.format(PAGE_YES, j));
                    } else {
                        sb.append(MessageFormat.format(PAGE_NO, processUrl(request, j, formatUrl), j));
                    }
                }

            } else {// 二头都加...
                sb.append(MessageFormat.format(PAGE_NO, processUrl(request, 1, formatUrl), 1));
                sb.append(PAGE_MORE);
                for (int j = (currPage - 2); j < (currPage + 3); j++) {
                    if (j == currPage) {
                        sb.append(MessageFormat.format(PAGE_YES, j));
                    } else {
                        sb.append(MessageFormat.format(PAGE_NO, processUrl(request, j, formatUrl), j));
                    }
                }
                sb.append(PAGE_MORE);
                sb.append(MessageFormat.format(PAGE_NO, processUrl(request, totalPage, formatUrl), totalPage));
            }

        }

        if (currPage == totalPage || totalPage == 0) {// 末页去掉连接
            sb.append(MessageFormat.format(PAGE_NEXT_NO, NEXT));
        } else {
            sb.append(MessageFormat.format(PAGE_NEXT_YES, processUrl(request, currPage + 1, formatUrl), NEXT));
        }

        // sb.append(MessageFormat.format(PAGE_SKIP, this.getRequestUrl(), "到第",
        // currPage, "页", "确定"));

        sb.append(PAGE_END);
        return sb.toString();
    }

    private static String processUrl(HttpServletRequest request, int pageNum, String formatUrl) {
        String url = "";
        if (formatUrl.startsWith("http://")) {
            url = formatUrl;
        } else {
            url = request.getContextPath() + formatUrl;
        }
        // String queryString = request.getQueryString();
        // if (queryString != null) {
        // url = url + "?" + queryString;
        // }

        String resultUrl = MessageFormat.format(url, String.valueOf(pageNum));
        return resultUrl;
    }
}
