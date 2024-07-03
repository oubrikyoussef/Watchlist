package com.openclassrooms.watchlist.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.openclassrooms.watchlist.domain.WatchlistItem;

@Repository
public class WatchlistRepository {
    private List<WatchlistItem> watchlistItems = new ArrayList<>();
    private static int index;
    private final int MAX_ITEMS = 20;

    public int getMaxItems() {
        return MAX_ITEMS;
    }

    public List<WatchlistItem> getList() {
        return watchlistItems;
    }

    public void addItem(WatchlistItem watchlistItem) {
        watchlistItem.setId(++index);
        watchlistItems.add(watchlistItem);
    }

    public WatchlistItem findWatchlistItemById(Integer id) {
        if (id == null)
            return null;
        for (WatchlistItem watchlistItem : watchlistItems) {
            if (watchlistItem.getId() == id)
                return watchlistItem;
        }
        return null;
    }

    public WatchlistItem findByTitle(String title) {
        for (WatchlistItem watchlistItem : watchlistItems) {
            if (watchlistItem.getTitle().equals(title))
                return watchlistItem;
        }
        return null;
    }

    public boolean itemAlreadyExists(String title) {
        for (WatchlistItem watchlistItem : watchlistItems) {
            if (watchlistItem.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }
}
