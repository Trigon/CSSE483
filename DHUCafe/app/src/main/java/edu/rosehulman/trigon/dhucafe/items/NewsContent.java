package edu.rosehulman.trigon.dhucafe.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample title for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class NewsContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<NewsItem> ITEMS = new ArrayList<NewsItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, NewsItem> ITEM_MAP = new HashMap<String, NewsItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(creatNews(i));
        }
    }

    private static void addItem(NewsItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static NewsItem creatNews(int position) {
        return new NewsItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of title.
     */
    public static class NewsItem {
        public final String id;
        public final String title;
        public final String details;
        public final String pic;

        public NewsItem(String id, String title, String details) {
            this.id = id;
            this.title = title;
            this.details = details;
            this.pic = null;
        }
        public NewsItem(String id, String title, String details, String pic) {
            this.id = id;
            this.title = title;
            this.details = details;
            this.pic = pic;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
