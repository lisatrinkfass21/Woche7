/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 *
 * @author bmayr
 */
// Recursive Action for forkJoinFramework from Java7
public class LinkFinderAction extends RecursiveAction {

    private String url;
    private ILinkHandler cr;
    /**
     * Used for statistics
     */
    private static final long t0 = System.nanoTime();

    public LinkFinderAction(String url, ILinkHandler cr) {
        this.url = url;
        this.cr = cr;
        // ToDo: Implement Constructor - done
    }

    @Override
    public void compute() {
        if (cr.visited(url) == false) {
            if (cr.size() >= 500) {
                System.out.println("Time: " + t0);
            } else {
                try {
                    List<RecursiveAction> liste = new ArrayList<>();
                    Parser parser = new Parser(new URL(url).openConnection());
                    NodeFilter filter = new HasAttributeFilter("href");
                    NodeList nl = parser.extractAllNodesThatMatch(filter);
                    cr.addVisited(url);
                    if (nl.size() > 0) {//kan auch keinen Link enthalten
                        for (int i = 0; i < nl.size(); i++) {
                            int index = nl.elementAt(i).getText().indexOf("http");
                            int index2 = nl.elementAt(i).getText().length();
                            String link = nl.elementAt(i).getText().substring(index, index2 - 2);
                            liste.add(new LinkFinderAction(link, cr));
                        }
                        invokeAll(liste);

                    }
                } catch (ParserException ex) {
                    Logger.getLogger(LinkFinderAction.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(LinkFinderAction.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(LinkFinderAction.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        // ToDo:
        // 1. if crawler has not visited url yet: - done
        // 2. Create new list of recursiveActions - done
        // 3. Parse url - done
        // 4. extract all links from url
        // 5. add new Action for each sublink
        // 6. if size of crawler exceeds 500 -> print elapsed time for statistics -done
        // -> Do not forget to call ìnvokeAll on the actions!
    }
}
