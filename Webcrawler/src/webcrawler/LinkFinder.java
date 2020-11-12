/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

/**
 *
 * @author bmayr
 */
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.NodeFilter;
import org.htmlparser.util.ParserException;

public class LinkFinder implements Runnable {

    private String url;
    private ILinkHandler linkHandler;
    /**
     * Used fot statistics
     */
    private static final long t0 = System.nanoTime();

    public LinkFinder(String url, ILinkHandler handler) {
        this.url = url;
        this.linkHandler = handler;
        //ToDo: Implement Constructor - done
    }

    @Override
    public void run() {
        getSimpleLinks(url);
    }

    private void getSimpleLinks(String url) {
        if (linkHandler.visited(url) == false) {
            if (linkHandler.size() >= 500) {
                System.out.println("Time: " + t0 + "ns");
                System.exit(0);
            } else {
                try {
                    NodeFilter filter = new NodeClassFilter(LinkTag.class);
                    linkHandler.addVisited(url);
                    Parser parser = new Parser(new URL(url).openConnection());
                    NodeList nl = parser.extractAllNodesThatMatch(filter);
                    for (int i = 0; i < nl.size(); i++) {
                        if (nl.elementAt(i).getText().contains("http:") || nl.elementAt(i).getText().contains("https:")) {
                            int index = nl.elementAt(i).getText().indexOf("http");
                            int index2 = nl.elementAt(i).getText().indexOf("\"", index);
                            String link = nl.elementAt(i).getText().substring(index, index2);
                            linkHandler.queueLink(link);
                        }
                    }

                } catch (MalformedURLException ex) {
                    System.out.println(url + ": konnte nicht geöffnet werden");
                } catch (IOException ex) {
                    System.out.println(url + ": konnte nicht geöffnet werden");
                } catch (ParserException ex) {
                    System.out.println(url + ": konnte nicht geöffnet werden");
                } catch (Exception ex) {
                    System.out.println(url + ": konnte nicht geöffnet werden");
                }
            }

            // ToDo: Implement
            // 1. if url not already visited, visit url with linkHandler
            // 2. get url and Parse Website
            // 3. extract all URLs and add url to list of urls which should be visited
            //    only if link is not empty and url has not been visited before
            // 4. If size of link handler equals 500 -> print time elapsed for statistics
        }
    }
}
