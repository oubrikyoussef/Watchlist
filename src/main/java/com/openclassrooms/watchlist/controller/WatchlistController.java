package com.openclassrooms.watchlist.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.openclassrooms.watchlist.domain.WatchlistItem;
import com.openclassrooms.watchlist.exception.DuplicateTitleException;
import com.openclassrooms.watchlist.exception.WatchlistRepositorySizeExceeded;
import com.openclassrooms.watchlist.service.WatchlistService;

import jakarta.validation.Valid;

@Controller
public class WatchlistController {
    @Autowired
    private WatchlistService watchlistService;
    private final Logger logger = LoggerFactory.getLogger(WatchlistController.class);

    public WatchlistController(WatchlistService watchlistService) {
        this.watchlistService = watchlistService;
    }

    @PostMapping("/watchlistItemForm")
    public ModelAndView submitWatchListItemForm(@Valid WatchlistItem watchlistItem, BindingResult bindingResult) {
        logger.info("HTTP POST Request Received From This Url : /watchlistItemForm");
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            logger.error("Error submitting watchlist item");
            for (FieldError fieldError : fieldErrors) {
                logger.error("Field: " + fieldError.getField());
                logger.error("Rejected Value: " + fieldError.getRejectedValue());
                logger.error("Error Message: " + fieldError.getDefaultMessage());
            }
            return new ModelAndView("watchlistItemForm");
        }
        try {
            watchlistService.addOrUpdateWatchlistItems(watchlistItem);
        } catch (WatchlistRepositorySizeExceeded e) {
            String viewName = "watchlistItemForm";
            bindingResult.rejectValue(null, "", e.getMessage());
            logger.error("WatchlistRepositorySizeExceeded: {}", e.getMessage());
            return new ModelAndView(viewName);
        } catch (DuplicateTitleException e) {
            String viewName = "watchlistItemForm";
            bindingResult.rejectValue("title", "", e.getMessage());
            logger.error("DuplicateTitleException: {}", e.getMessage());
            return new ModelAndView(viewName);
        }
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/watchlist");

        logger.info("Watchlist item submitted successfully, Redirection to /watchlist");
        return new ModelAndView(redirectView);
    }

    @GetMapping("/watchlistItemForm")
    public ModelAndView showWatchListItemForm(@RequestParam(required = false) Integer id) {
        logger.info("HTTP GET Request Received From This Url : /watchlistItemForm");

        Map<String, Object> model = new HashMap<>();
        String viewName = "watchlistItemForm";

        WatchlistItem watchlistItem = watchlistService.findWatchlistItemById(id);
        model.put("watchlistItem", watchlistItem == null ? new WatchlistItem() : watchlistItem);

        logger.debug("Showing watchlist item form for id: {}", id);
        return new ModelAndView(viewName, model);
    }

    @GetMapping("/watchlist")
    public ModelAndView getWatchlist() {
        logger.info("HTTP GET Request Received From This Url : /watchlist");

        String viewName = "watchlist";
        Map<String, Object> model = new HashMap<String, Object>();

        model.put("numberOfMovies", watchlistService.getWatchlistItemsSize());
        model.put("watchlistItems", watchlistService.getWatchlistItems());

        logger.debug("Returning watchlist view with {} movies", watchlistService.getWatchlistItemsSize());
        return new ModelAndView(viewName, model);
    }
}
