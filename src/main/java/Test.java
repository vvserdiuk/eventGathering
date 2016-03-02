import java.io.IOException;

/**
 * Created by vvserdiuk on 26.02.2016.
 */
public class Test {

    public static void main(String[] args) throws IOException {
//        VkEventParser parser = new VkEventParser("http://vk.com/event115436279");
//        System.out.println(parser.getStartTime());
//        System.out.println(parser.getEndTime());
//        System.out.println(parser.getTitle());
//        System.out.println(parser.getPosterUrl());
//        System.out.println(parser.getDescription());
//        parser.downloadImage();

//        VkGroupParser groupParser = new VkGroupParser("http://vk.com/a_ryba");

        System.out.println(VkGroupParser.getEvents("http://vk.com/vjdnepr"));
    }
}
