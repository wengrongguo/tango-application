package org.tango.heritrix.ext.extractor;

import au.id.jericho.lib.html.*;
import groovy.swing.factory.VBoxFactory;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.archive.io.ReplayCharSequence;
import org.archive.modules.CrawlURI;
import org.archive.modules.extractor.Extractor;
import org.archive.util.TextUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * User: tango
 * Date: 13-12-22
 * Time: 下午9:45
 */
public class ExtractorContent extends Extractor {

    private final static Log log = LogFactory.getLog(ExtractorContent.class);

    private Map<String, String> matches = new ConcurrentHashMap<String, String>();

    public static void main(String[] args) throws IOException {
        String i = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<h3 class=\"hm xw1\">\n" +
                "\n" +
                "<a href=\"thread-13934790-1-1.html\" onclick=\"return copyThreadUrl(this)\" id=\"thread_subject\" style=\"color:#1D50A1;\">找个温暖踏实靠谱男，一起走完酸甜苦辣的平凡人生，不离不弃。</a>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<a style=\"display:none;\" href=\"thread-13934790-1-1.html\" onclick=\"return copyThreadUrl(this)\" title=\"复制链接\"><img src=\"./static/image/common/copy.png\" border=0></a>\n" +
                "<!-- <a class=\"web_line_copy\" href=\"thread-13934790-1-1.html\" onclick=\"return copyThreadUrl(this)\" >[复制链接]</a> -->\n" +
                "\n" +
                "</h3>\n" +
                "    <p class=\"hm\">\n" +
                "<span title=\"发表于 2013-05-19 11:45\">更新于 2013-12-21 22:36</span>\t\t\t\t\t\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "</div>\n" +
                "</dd>\n" +
                "</dl>\n" +
                "</div>\t\t\t<div id=\"discuz_tips\" style=\"display:none;\"></div>\n" +
                "\t\t\t<script type=\"text/javascript\">\n" +
                "\t\t\t\tvar discuzSId = '12172848';\n" +
                "\t\t\t\tvar discuzVersion = 'X2.5';\n" +
                "\t\t\t\tvar discuzRelease = '20130403';\n" +
                "\t\t\t\tvar discuzApi = '0.6';\n" +
                "\t\t\t\tvar discuzIsFounder = '';\n" +
                "\t\t\t\tvar discuzFixbug = '25000004';\n" +
                "\t\t\t\tvar discuzAdminId = '0';\n" +
                "\t\t\t\tvar discuzOpenId = '';\n" +
                "\t\t\t\tvar discuzUid = '0';\n" +
                "\t\t\t\tvar discuzGroupId = '7';\n" +
                "\t\t\t\tvar ts = '1387728912';\n" +
                "\t\t\t\tvar sig = '0a62be2272c9b52d8948154132e667f9';\n" +
                "\t\t\t\tvar discuzTipsCVersion = '2';\n" +
                "\t\t\t</script>\n" +
                "\t\t\t<script src=\"http://discuz.gtimg.cn/cloud/scripts/discuz_tips.js?v=1\" type=\"text/javascript\" charset=\"UTF-8\"></script><script src=\"static/topic/home/js/index.js\" type=\"text/javascript\" type=\"text/javascript\"></script>\n" +
                "\n" +
                "<iframe width=\"0\" height=\"0\" src=\"http://bbs.hefei.cc/viewthreads.php?tid=13934790&amp;extra=page%3D1&amp;page=1\"></iframe>\t\n" +
                "<iframe width=\"0\" height=\"0\" src=\"http://bbs.hefei.cc/viewthreads.php?tid=13934787&amp;extra=page%3D1&amp;page=1\"></iframe>\n" +
                "</body>\n" +
                "</html>\n";
        System.out.println("i.replaceAll(\"\") = " + i.replaceAll("[\\s\\S.]*id=\"thread_subject\" style=\"color:#1D50A1;\">(.*)</a>[\\s\\S.]*", "$1"));
    }

    @Override
    protected void extract(CrawlURI uri) {
        try {
            System.out.println("uri.getURI() = " + uri.getURI());
            ReplayCharSequence cs = uri.getRecorder().getContentReplayCharSequence();
           /* Source source = new Source(cs);
            List elements = source.findAllElements(StartTagType.NORMAL);
            for (Iterator elementIterator = elements.iterator(); elementIterator.hasNext(); ) {
                Element element = (Element) elementIterator.next();
                String elementName = element.getName();
                Attributes attributes;
                if (elementName.equals(HTMLElementName.BODY)) {
                    if (processMeta(curi, element)) {
                        // meta tag included NOFOLLOW; abort processing
                        break;
                    }
                } else if (elementName.equals(HTMLElementName.SCRIPT)) {
                    processScript(curi, element);
                } else if (elementName.equals(HTMLElementName.STYLE)) {
                    processStyle(curi, element);
                } else if (elementName.equals(HTMLElementName.FORM)) {
                    processForm(curi, element);
                } else if (!(attributes = element.getAttributes()).isEmpty()) {
                    processGeneralTag(curi, element, attributes);
                }
            }*/

            String content = cs.toString();
            for (Iterator<Map.Entry<String, String>> iterator = matches.entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
                String reg = entry.getValue();
                if (content.matches(reg)) {
                    String temp = content.replaceAll(entry.getValue(), "$1");
                    System.out.println(entry.getKey() + " = " + temp);
                }
            }
        } catch (IOException e) {
            log.error("Failed get of replay char sequence in " + Thread.currentThread().getName());
        }
    }


    @Override
    protected boolean shouldProcess(CrawlURI uri) {
        if (getIgnoreUnexpectedHtml()) {
            try {
                // HTML was not expected (eg a GIF was expected) so ignore
                // (as if a soft 404)
                if (!isHtmlExpectedHere(uri)) {
                    return false;
                }
            } catch (URIException e) {
                log.error("Failed expectedHTML test: " + e.getMessage());
                // assume it's okay to extract
            }
        }

        String mime = uri.getContentType().toLowerCase();
        return mime.startsWith("text/html")
                || mime.startsWith("application/xhtml")
                || mime.startsWith("text/vnd.wap.wml")
                || mime.startsWith("application/vnd.wap.wml")
                || mime.startsWith("application/vnd.wap.xhtml");
    }

    {
        setIgnoreUnexpectedHtml(true);
    }

    public boolean getIgnoreUnexpectedHtml() {
        return (Boolean) kp.get("ignoreUnexpectedHtml");
    }

    public void setIgnoreUnexpectedHtml(boolean ignoreUnexpectedHtml) {
        this.kp.put("ignoreUnexpectedHtml", ignoreUnexpectedHtml);
    }

    protected boolean isHtmlExpectedHere(CrawlURI curi) throws URIException {
        String path = curi.getUURI().getPath();
        if (path == null) {
            // no path extension, HTML is fine
            return true;
        }
        int dot = path.lastIndexOf('.');
        if (dot < 0) {
            // no path extension, HTML is fine
            return true;
        }
        if (dot < (path.length() - 5)) {
            // extension too long to recognize, HTML is fine
            return true;
        }
        String ext = path.substring(dot + 1);
        return !TextUtils.matches(NON_HTML_PATH_EXTENSION, ext);
    }

    public Map<String, String> getMatches() {
        return matches;
    }

    public void setMatches(Map<String, String> matches) {
        this.matches = matches;
    }

    static final String NON_HTML_PATH_EXTENSION =
            "(?i)(gif)|(jp(e)?g)|(png)|(tif(f)?)|(bmp)|(avi)|(mov)|(mp(e)?g)" +
                    "|(mp3)|(mp4)|(swf)|(wav)|(au)|(aiff)|(mid)";
}
