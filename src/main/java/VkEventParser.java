import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
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

    private static final String DESCRIPTION_RU = "Описание:";
    private static final String START_TIME_RU = "Начало";
    private static final String END_TIME_RU = "Окончание:";
    private static final String START_TIME_EN = "Date and time:";
    private static final String END_TIME_EN = "End time:";

    private Document doc;
    private String url;
    public VkEventParser(String url) throws IOException {
        doc = Jsoup.connect(url)
                .header("Accept-Language", "ru")
                .timeout(15_000)
                .get();
        this.url = url;
    }


    public LocalDateTime getStartTime() throws IOException {
        Document tmpDoc = Jsoup.connect(url)
                .header("Accept-Language", "en")
                .timeout(10_000)
                .get();

        String time = tmpDoc.getElementsContainingOwnText(START_TIME_EN).first().nextElementSibling().html();
        String ruStartTime = doc.getElementsContainingOwnText(START_TIME_RU).first().nextElementSibling().html();
        return parseTime(time, ruStartTime);
    }

    public LocalDateTime getEndTime() throws IOException {
        Document tmpDoc = Jsoup.connect(url)
                .header("Accept-Language", "en")
                .timeout(10_000)
                .get();

        String time = null;
        String ruEndTime = null;
        try {
            time = tmpDoc.getElementsContainingOwnText(END_TIME_EN).first().nextElementSibling().html();
            ruEndTime = doc.getElementsContainingOwnText(END_TIME_RU).first().nextElementSibling().html();
        } catch (NullPointerException e) {
            return getStartTime();
        }

        return parseTime(time, ruEndTime);
    }


    private LocalDateTime parseTime (String time, String ruTime){
        String[] timeArr = time.split(" ");

        int dayOfMonth = 1;
        Month month = null;
        int hour = 0;
        int minute = 0;
        int year = 0;

        if (timeArr.length==5){
            dayOfMonth = Integer.parseInt(timeArr[0]);
            month      = Month.valueOf(timeArr[1].toUpperCase());
            year       = getYear(month);

            LocalTime localTime = LocalTime.parse(ruTime.substring(ruTime.lastIndexOf(" ")+1));
            hour       = localTime.minusHours(1).getHour();
            minute     = localTime.getMinute();
        }
        else if (timeArr.length==4){
            if (timeArr[0].equals("today")){ // if today
                dayOfMonth = LocalDateTime.now().getDayOfMonth();
                month      = LocalDateTime.now().getMonth();
                year       = getYear(month);

                LocalTime localTime = LocalTime.parse(timeArr[2] + " " + timeArr[3].toUpperCase(), DateTimeFormatter.ofPattern("K:m a"));
                hour       = localTime.minusHours(1).getHour();
                minute     = localTime.getMinute();
            }
            else { //if tomorrow
                dayOfMonth = LocalDateTime.now().plusDays(1).getDayOfMonth();
                month      = LocalDateTime.now().plusDays(1).getMonth();
                year       = getYear(month);

                LocalTime localTime = LocalTime.parse(timeArr[2] + " " + timeArr[3].toUpperCase(), DateTimeFormatter.ofPattern("K:m a"));
                hour       = localTime.minusHours(1).getHour();
                minute     = localTime.getMinute();
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
        Element description = doc.getElementsContainingOwnText(DESCRIPTION_RU).first().nextElementSibling();

        Element showMore = description.select("a.pi_text_more").first();
        if (showMore!=null){
            showMore.remove();
            return description.getElementsByTag("span").html(); //remove <span> tags
        }
        return description.html();
    }

    public String getTitle() throws IOException {
        Element title = doc.select("h2.op_header").first();
        return title.html();
    }

    public String getPosterUrl() throws IOException {
        String posterInnerLink = doc.getElementById("mcont").select("a[href]").first().toString();
        String posterLink = "http://vk.com" + posterInnerLink.substring(posterInnerLink.indexOf("/"), posterInnerLink.indexOf("?"));


        Document tmpDoc = Jsoup.connect(posterLink)
                .header("Accept-Language", "ru")
                .timeout(10_000)
                .get();
        return tmpDoc.select("div.pv_photo_wrap").first().getElementsByAttribute("src").first().attr("src");
    }


    public void downloadImage(URL posterUrl, String path, String imageName) throws IOException {
        if (!path.endsWith("/")){
            path += "/";
        }
        try(InputStream in = posterUrl.openStream()){
            Files.copy(in, Paths.get(path + imageName));
        }
    }

}
