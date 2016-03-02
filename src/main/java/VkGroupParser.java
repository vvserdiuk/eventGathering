
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by vvserdiuk on 29.02.2016.
 */
public class VkGroupParser {

    static Document doc;
    static Set<String> urls = new HashSet<>();

    public static Collection<String> getEvents(String url) throws IOException {
        WebClient webClient = new WebClient();
        HtmlPage page = webClient.getPage(url);
        try {
            page.executeJavaScript("Groups.showEvents();");
        } catch (ScriptException e) {
            page.executeJavaScript("Public.showEvents();");
        }
        String html = page.getPage().asXml();

        doc = Jsoup.parse(html);
        Elements linksPart1 = doc.getElementsByClass("wk_vk_link");
        Elements linksPart2 = doc.getElementsByAttributeValueStarting("href", "/event");
        addLinks(linksPart1);
        addLinks(linksPart2);

        return urls;
    }

    private static void addLinks(Elements elements){
        for (Element e : elements) {
            String link = e.attr("href");
            String prefix = "http://vk.com";
            if (!link.startsWith(prefix) && !link.startsWith("https://vk.com")) {
                link = prefix + link;
            }
            urls.add(link);
        }
    }
}
