package com.gen.marketrss.common.constant;

public final class Html {
    public static final String EMAIL_VERTIFY = "<div style='margin : 3%%'>" +
            "<div style='text-align: center;\n" +
            "    background-color: #fff;\n" +
            "    border-radius: 8px;\n" +
            "    box-shadow: 0 4px 6px rgba(0,0,0,0.1);\n" +
            "    padding: 20px;\n" +
            "    width: 340px;'>" +
            "<h2 style='color: #333;\n" +
            "    margin-bottom: 10px;'>인증번호가 발송되었습니다</h2><p style='color: #666;'>받은 인증번호: <strong style='color: #007bff;'>%s</strong></p>" +
            "</div>" +
            "</div>";

    /**
     * url
     * title
     * summary
     * reg_date
     */
    public static final String NEWS_CONTENT = "<a href='%s'><div style=\"border: 1px solid #ddd; border-radius: 5px; overflow: hidden; width: 300px; margin: 20px; box-shadow: 0 2px 5px rgba(0,0,0,0.2);\">\n" +
            "    <img src=\"%s\" alt=\"뉴스 이미지\" style=\"width: 100%%; height: 200px; object-fit: cover;\">\n" +
            "    <div style=\"padding: 15px;\">\n" +
            "        <h4 style=\"margin-top: 0;\">%s</h4>\n" +
            "        <p>%s</p>\n" +
            "        <p style=\"color: #888; font-size: 0.8em;\">작성일: %s</p>\n" +
            "    </div>\n" +
            "</div></a>";
}
