package com.github.vvserdiuk.parsing;

import com.github.vvserdiuk.model.Event;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

/**
 * Created by vvserdiuk on 26.02.2016.
 */
public class VkEventParser {
    Logger LOG = Logger.getLogger(this.getClass());


    private static final String DESCRIPTION_RU = "Описание:";
    private static final String START_TIME_RU = "Начало:";
    private static final String END_TIME_RU = "Окончание:";
    private static final String START_TIME_EN = "Start Time:";
    private static final String END_TIME_EN = "End time:";

    private Document doc;
    private String url;
    //TODO Jsoup.connect in one method
    public VkEventParser(String url) throws IOException {
        while (true) {
            try {
                doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1")
                        .header("Accept-Language", "ru")
                        .timeout(10_000)
                        .get();
                break;
            } catch (SocketTimeoutException e) {
                // retry
            }
        }
        this.url = url;
    }

    public Event getEvent() throws IOException {
        return new Event(getPosterUrl(), getTitle(), getStartDateTime(), getDescription());
    }

    public LocalDateTime getStartDateTime() throws IOException {
        Document tmpDoc = null;
        while (true) {
            try {
                tmpDoc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1")
                        .header("Accept-Language", "en")
                        .timeout(10_000)
                        .get();
                break;
            } catch (SocketTimeoutException e) {
                // retry
            }
        }

        LOG.debug("!!!!!!!" + url);
        LOG.debug(tmpDoc.getElementsContainingOwnText(START_TIME_EN).first());
        String startDateTime = tmpDoc.getElementsContainingOwnText(START_TIME_EN).first().nextElementSibling().html();
        String ruStartDateTime = doc.getElementsContainingOwnText(START_TIME_RU).first().nextElementSibling().html();
        return parseDateTime(startDateTime, ruStartDateTime);
    }

    public LocalDateTime getEndDateTime() throws IOException {
        Document tmpDoc = null;
        while (true) {
            try {
                tmpDoc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1")
                        .header("Accept-Language", "en")
                        .timeout(10_000)
                        .get();
                break;
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
            }
        }

        Elements timeElement = tmpDoc.getElementsContainingOwnText(END_TIME_EN);
        Elements ruEndTimeElement = doc.getElementsContainingOwnText(END_TIME_RU);
        if (timeElement==null || timeElement.isEmpty()){
            return getStartDateTime();
        }
        String endDateTime = timeElement.first().nextElementSibling().html();
        String ruEndDateTime = ruEndTimeElement.first().nextElementSibling().html();

        return parseDateTime(endDateTime, ruEndDateTime);
    }


    private LocalDateTime parseDateTime(String datTime, String ruDateTime){
        String[] timeArr = datTime.split(" ");

        int dayOfMonth = 1;
        Month month = null;
        int hour = 0;
        int minute = 0;
        int year = 0;

        if (timeArr.length==5){ //if some date
            dayOfMonth = Integer.parseInt(timeArr[0]);
            month      = Month.valueOf(timeArr[1].toUpperCase());
            year       = getYear(month);

            LOG.debug(ruDateTime);
            String onlyTime = ruDateTime.substring(ruDateTime.lastIndexOf(" ")+1);
            if (onlyTime.startsWith("0")){ // cannot parse "0:xx", so I had to do "00:xx"
                onlyTime="0"+onlyTime;
            }
            LocalTime localTime = LocalTime.parse(onlyTime);
            hour       = localTime.minusHours(1).getHour();
            minute     = localTime.getMinute();
        }
        else if (timeArr.length==4){
            switch (timeArr[0]) {
                case "today": { // if today
                    dayOfMonth = LocalDateTime.now().getDayOfMonth();
                    month = LocalDateTime.now().getMonth();
                    year = getYear(month);

                    LocalTime localTime = LocalTime.parse(timeArr[2] + " " + timeArr[3].toUpperCase(), DateTimeFormatter.ofPattern("K:m a"));
                    hour = localTime.minusHours(1).getHour();
                    minute = localTime.getMinute();
                    break;
                }
                case "yesterday": { // if yesterday
                    dayOfMonth = LocalDateTime.now().minusDays(1).getDayOfMonth();
                    month = LocalDateTime.now().getMonth();
                    year = getYear(month);

                    LocalTime localTime = LocalTime.parse(timeArr[2] + " " + timeArr[3].toUpperCase(), DateTimeFormatter.ofPattern("K:m a"));
                    hour = localTime.minusHours(1).getHour();
                    minute = localTime.getMinute();
                    break;
                }
                default: { //if tomorrow
                    dayOfMonth = LocalDateTime.now().plusDays(1).getDayOfMonth();
                    month = LocalDateTime.now().plusDays(1).getMonth();
                    year = getYear(month);

                    LocalTime localTime = LocalTime.parse(timeArr[2] + " " + timeArr[3].toUpperCase(), DateTimeFormatter.ofPattern("K:m a"));
                    hour = localTime.minusHours(1).getHour();
                    minute = localTime.getMinute();
                    break;
                }
            }
        }

        return LocalDateTime.of(
                year,
                month,
                dayOfMonth,
                hour,
                minute
        );
    }

    private int getYear(Month month){
        int year;
        if (month.getValue()< LocalDate.now().getMonth().getValue()){
            year = LocalDate.now().plusYears(1).getYear();
        }
        else {
            year = LocalDate.now().getYear();
        }
        return  year;
    }

    public String getDescription() throws IOException {
        Elements descriptionElements = doc.getElementsContainingOwnText(DESCRIPTION_RU);
        if(descriptionElements==null || descriptionElements.isEmpty()){
            return "";
        }
        Element descriptionElement = descriptionElements.first().nextElementSibling();
        Element showMore = descriptionElement.select("a.wall_post_more").first();
        if (showMore!=null){
            showMore.remove();
           descriptionElement.getElementsByTag("span").removeAttr("style");
           descriptionElement =  descriptionElement.child(0);
        }
        return descriptionElement.html();
    }

    public String getTitle() throws IOException {
        Element title = doc.select("title").first();
        return title.html();
    }

    public String getPosterUrl() throws IOException {
        LOG.debug(doc.getElementById("group").select("a[href]").first());
        String posterInnerLink = doc.getElementById("group").select("a[href]").first().attr("href");
        String posterLink = "http://vk.com" + posterInnerLink;

        Document tmpDoc = null;
        while (true) {
            try {
                tmpDoc = Jsoup.connect(posterLink)
                        .userAgent("Opera/12.02 (Android 4.1; Linux; Opera Mobi/ADR-1111101157; U; en-US) Presto/2.9.201 Version/12.02")
                        .header("Accept-Language", "ru")
                        .timeout(10_000)
                        .get();
                break;
            } catch (SocketTimeoutException e) {
                // retry
            }
        }
        String link = tmpDoc.select("div.pv_photo_wrap").first().getElementsByAttribute("src").first().attr("src");
        return link;
    }


    //TODO or delete
    public void downloadImage(URL posterUrl, String path, String imageName) throws IOException {
        if (!path.endsWith("/")){
            path += "/";
        }
        try(InputStream in = posterUrl.openStream()){
            Files.copy(in, Paths.get(path + imageName));
        }
    }

}
