package com.github.vvserdiuk.parsing;

import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HTMLParserListener;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by vvserdiuk on 29.02.2016.
 */
public class VkGroupParser {

    private static Document doc;
    private static Set<String> urls = new HashSet<>();

    public static Set<String> getEvents(String url) throws IOException {
        WebClient webClient = new WebClient();
        webClient.setIncorrectnessListener((s, o) -> {
            //IGNORE
        });
        webClient.setHTMLParserListener(new HTMLParserListener() {
            @Override
            public void error(String s, URL url, int i, int i1, String s1) {
                //IGNORE
            }

            @Override
            public void warning(String s, URL url, int i, int i1, String s1) {
                // IGNORE
            }
        });
        webClient.setJavaScriptErrorListener(new JavaScriptErrorListener() {
            @Override
            public void scriptException(HtmlPage htmlPage, ScriptException e) {

            }

            @Override
            public void timeoutError(HtmlPage htmlPage, long l, long l1) {

            }

            @Override
            public void malformedScriptURL(HtmlPage htmlPage, String s, MalformedURLException e) {

            }

            @Override
            public void loadScriptError(HtmlPage htmlPage, URL url, Exception e) {

            }
        });
        webClient.setThrowExceptionOnFailingStatusCode(false);
        webClient.setThrowExceptionOnScriptError(false);
        webClient.setPrintContentOnFailingStatusCode(false);
        webClient.setCssErrorHandler(new SilentCssErrorHandler());


        HtmlPage page = webClient.getPage(url);

        //TODO not to use exceptions in logic implementing
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
