package crawler4j;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by gaorui on 17/4/20.
 */
public class MyCraler extends WebCrawler {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp3|zip|gz))$");

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "http://www.ics.uci.edu/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches();
    }

    /**
     * This function is called when a page is fetched and ready
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        System.out.println("URL: " + url);

        if (page.getParseData() instanceof HtmlParseData) {
            System.out.println("###########################");
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();
            //System.out.println(htmlParseData.toString());
            //System.out.println("Text length: " + text);

            try {
                Document doc = Jsoup.parse(html);

                //doc = new Document(text);
                if ("http://www.kxdaili.com/".equals(url)) {

                    //System.out.println(doc.toString());
                    Elements trs = doc.select("table").get(1).select("tr");
                    Elements tds = trs.get(1).select("td");

                    String ip = tds.get(0).text();

                    System.out.println("$:" + ip);
                } else if ("http://www.ip181.com/".equals(url)) {
                    Elements trs = doc.select("table").select("tr");
                    Elements tds = trs.get(1).select("td");

                    String ip = tds.get(0).text();

                    System.out.println("$:" + ip);
                }

                //doc = new Document(html);

            } catch (Exception e) {
                e.printStackTrace();
            }

           /* for (int i = 1; i < 50; i++) {
                Elements tds = trs.get(i).select("td");

                String ip = tds.get(0).text();
                int port = Integer.parseInt(tds.get(1).text());
                String area = tds.get(5).text();
                System.out.println("$:"+ip);
            }*/
            //System.out.println("Html length: " + html);
            //System.out.println("Number of outgoing links: " + links.size());
        }
    }
}