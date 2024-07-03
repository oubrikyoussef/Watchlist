package com.openclassrooms.watchlist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.watchlist.domain.WatchlistItem;
import com.openclassrooms.watchlist.exception.DuplicateTitleException;
import com.openclassrooms.watchlist.exception.WatchlistRepositorySizeExceeded;
import com.openclassrooms.watchlist.repository.WatchlistRepository;

@Service
public class WatchlistService {
    private WatchlistRepository watchlistRepository;
    private MovieRatingService movieRatingService;

    @Autowired
    public WatchlistService(WatchlistRepository watchlistRepository, MovieRatingService movieRatingService) {
        this.watchlistRepository = watchlistRepository;
        this.movieRatingService = movieRatingService;
    }

    public List<WatchlistItem> getWatchlistItems() {
        List<WatchlistItem> watchlistItems = watchlistRepository.getList();
        for (WatchlistItem watchlistItem : watchlistItems) {
            Double rating = movieRatingService.getMovieRating(watchlistItem.getTitle());
            if (rating != null) {
                watchlistItem.setRating(rating);
            }
        }
        return watchlistItems;
    }

    public int getWatchlistItemsSize() {
        return watchlistRepository.getList().size();
    }

    public WatchlistItem findWatchlistItemById(Integer id) {
        return watchlistRepository.findWatchlistItemById(id);
    }

    public void addOrUpdateWatchlistItems(WatchlistItem watchlistItem)
            throws DuplicateTitleException, WatchlistRepositorySizeExceeded {
        WatchlistItem watchlistExisitingItem = findWatchlistItemById(watchlistItem.getId());
        if (watchlistExisitingItem == null) {
            if (watchlistRepository.findByTitle(watchlistItem.getTitle()) != null) {
                throw new DuplicateTitleException("An item with same title exists in your watchlist");
            }
            if (getWatchlistItemsSize() >= watchlistRepository.getMaxItems()) {
                throw new WatchlistRepositorySizeExceeded(
                        "WatchlistRepository size exceeded consider deleteing some items from watchlist");
            }
            watchlistItem.setId(getWatchlistItemsSize() + 1);
            watchlistRepository.addItem(watchlistItem);
        } else {
            watchlistExisitingItem.setTitle(watchlistItem.getTitle());
            watchlistExisitingItem.setComment(watchlistItem.getComment());
            watchlistExisitingItem.setPriority(watchlistItem.getPriority());
            watchlistExisitingItem.setRating(watchlistItem.getRating());
        }
    }

}