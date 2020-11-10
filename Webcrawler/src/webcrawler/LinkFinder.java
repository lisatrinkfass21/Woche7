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
import java.net.URL;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            try {
                Parser parser = new Parser(url);
                NodeList n = parser.parse(null);
                NodeList nl = n.extractAllNodesThatMatch(new NodeClassFilter());
            } catch (Exception ex) {
                Logger.getLogger(LinkFinder.class.getName()).log(Level.SEVERE, null, ex);
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
