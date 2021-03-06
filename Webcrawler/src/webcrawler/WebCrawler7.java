package webcrawler;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.ForkJoinPool;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bmayr
 */
public class WebCrawler7 implements ILinkHandler {

    private final Collection<String> visitedLinks = Collections.synchronizedSet(new HashSet<String>());
//    private final Collection<String> visitedLinks = Collections.synchronizedList(new ArrayList<String>());
    private String url;
    private ForkJoinPool mainPool;

    public WebCrawler7(String startingURL, int maxThreads) {
        this.url = startingURL;
        mainPool = new ForkJoinPool(maxThreads);
        // ToDo: Initialize "mainPool" - done
    }

    private void startCrawling() {
        LinkFinderAction root = new LinkFinderAction(url, new WebCrawler7(url, 1));
        mainPool.invoke(root);
        // ToDo: Invoke LinkFinderAction on threadpool - done
    }

    @Override
    public int size() {
        return visitedLinks.size();
    }

    @Override
    public void addVisited(String s) {
        visitedLinks.add(s);
    }

    @Override
    public boolean visited(String s) {
        return visitedLinks.contains(s);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        new WebCrawler7("http://www.orf.at", 64).startCrawling();
        //new WebCrawler7("https://de.wikipedia.org", 64).startCrawling();
    }

    // Just override - we do not need this methode when using forkJoinPool
    @Override
    public void queueLink(String link) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
