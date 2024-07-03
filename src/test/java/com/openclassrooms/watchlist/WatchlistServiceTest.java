package com.openclassrooms.watchlist;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.openclassrooms.watchlist.domain.Priority;
import com.openclassrooms.watchlist.domain.WatchlistItem;
import com.openclassrooms.watchlist.exception.DuplicateTitleException;
import com.openclassrooms.watchlist.exception.WatchlistRepositorySizeExceeded;
import com.openclassrooms.watchlist.repository.WatchlistRepository;
import com.openclassrooms.watchlist.service.MovieRatingService;
import com.openclassrooms.watchlist.service.WatchlistService;

@RunWith(MockitoJUnitRunner.class)
public class WatchlistServiceTest {
    @Mock
    private WatchlistRepository watchlistRepositoryMock;
    @Mock
    private MovieRatingService movieRatingServiceMock;
    @InjectMocks
    private WatchlistService watchlistService;
    @Captor
    private ArgumentCaptor<WatchlistItem> watchlistItemCaptor;

    @Test
    public void testGetWatchlistItems_ReturnsAllItemsFromRespository() {
        // Arrange
        WatchlistItem item1 = new WatchlistItem("Star Wars", 7.7, "Good Movie", Priority.MEDIUM);
        WatchlistItem item2 = new WatchlistItem("The Matrix", 8.5, "Good Sci-Fi movie", Priority.HIGH);
        WatchlistItem item3 = new WatchlistItem("The Lord of the Rings", 9.0, "Good Fantasy movie", Priority.HIGH);
        WatchlistItem item4 = new WatchlistItem("The Silence of the Lambs", 8.2, "Good Crime movie", Priority.MEDIUM);
        WatchlistItem item5 = new WatchlistItem("The Godfather", 9.2, "Good Crime movie", Priority.HIGH);
        List<WatchlistItem> mockItems = List.of(item1, item2, item3, item4, item5);
        when(watchlistRepositoryMock.getList()).thenReturn(mockItems);
        // Act
        List<WatchlistItem> result = watchlistService.getWatchlistItems();
        // Assert
        assertTrue(mockItems.size() == result.size());
        for (int i = 0; i < mockItems.size(); i++) {
            assertTrue(mockItems.get(i).getTitle() == result.get(i).getTitle());
            assertTrue(mockItems.get(i).getComment() == result.get(i).getComment());
            assertTrue(mockItems.get(i).getPriority() == result.get(i).getPriority());
            assertTrue(mockItems.get(i).getId() == result.get(i).getId());
        }
    }

    @Test
    public void testGetWatchlistItems_RatingFromOmdbServiceOverrideTheValueInItems() {
        // Arrange
        WatchlistItem item1 = new WatchlistItem("Star Wars", 5, "Good Movie", Priority.MEDIUM);
        List<WatchlistItem> mockItems = List.of(item1);
        when(watchlistRepositoryMock.getList()).thenReturn(mockItems);
        when(movieRatingServiceMock.getMovieRating(any(String.class))).thenReturn(10.0);
        // Act
        List<WatchlistItem> result = watchlistService.getWatchlistItems();
        // Assert
        assertTrue(result.get(0).getRating() == 10);
    }

    @Test
    public void testGetWatchlistItemsSize_ReturnsNumberOfItemsInRepositoriy() {
        // Arrange
        WatchlistItem item1 = new WatchlistItem("Star Wars", 7.7, "Good Movie", Priority.MEDIUM);
        WatchlistItem item2 = new WatchlistItem("The Matrix", 8.5, "Good Sci-Fi movie", Priority.HIGH);
        WatchlistItem item3 = new WatchlistItem("The Lord of the Rings", 9.0, "Good Fantasy movie", Priority.HIGH);
        WatchlistItem item4 = new WatchlistItem("The Silence of the Lambs", 8.2, "Good Crime movie", Priority.MEDIUM);
        WatchlistItem item5 = new WatchlistItem("The Godfather", 9.2, "Good Crime movie", Priority.HIGH);
        List<WatchlistItem> mockItems = List.of(item1, item2, item3, item4, item5);
        when(watchlistRepositoryMock.getList()).thenReturn(mockItems);
        // Act
        int result = watchlistService.getWatchlistItemsSize();
        // Assert
        assertTrue(result == 5);
    }

    @Test
    public void testFindWatchlistItemById_ReturnsAnItemWithASpecificId() {
        // Arrange
        WatchlistItem item1 = new WatchlistItem("Star Wars", 7.7, "Good Movie", Priority.MEDIUM);
        item1.setId(1);
        when(watchlistRepositoryMock.findWatchlistItemById(1)).thenReturn(item1);

        // Act
        WatchlistItem result = watchlistService.findWatchlistItemById(1);

        // Assert
        assertTrue(result != null);
        assertTrue(result.equals(item1));
    }

    @Test(expected = DuplicateTitleException.class)
    public void testAddOrUpdateWatchlistItems_throwsDuplicateTitleException()
            throws DuplicateTitleException, WatchlistRepositorySizeExceeded {
        // Arrange
        WatchlistItem newItem = new WatchlistItem("Star Wars", 7.7, "Good Movie", Priority.MEDIUM);
        when(watchlistRepositoryMock.findWatchlistItemById(newItem.getId())).thenReturn(null);
        when(watchlistRepositoryMock.findByTitle("Star Wars")).thenReturn(new WatchlistItem());

        // Act
        watchlistService.addOrUpdateWatchlistItems(newItem);
    }

    @Test(expected = WatchlistRepositorySizeExceeded.class)
    public void testAddOrUpdateWatchlistItems_throwsWatchlistRepositorySizeExceeded()
            throws DuplicateTitleException, WatchlistRepositorySizeExceeded {
        // Arrange
        WatchlistItem newItem = new WatchlistItem("Star Wars", 7.7, "Good Movie", Priority.MEDIUM);
        when(watchlistRepositoryMock.findWatchlistItemById(newItem.getId())).thenReturn(null);
        when(watchlistRepositoryMock.getMaxItems()).thenReturn(1);
        when(watchlistRepositoryMock.getList()).thenReturn(List.of(new WatchlistItem()));

        // Act
        watchlistService.addOrUpdateWatchlistItems(newItem);
    }

    @Test
    public void testAddOrUpdateWatchlistItems_addsNewItemToRepository()
            throws DuplicateTitleException, WatchlistRepositorySizeExceeded {
        // Arrange
        WatchlistItem newItem = new WatchlistItem("Star Wars", 7.7, "Good Movie", Priority.MEDIUM);
        when(watchlistRepositoryMock.findWatchlistItemById(newItem.getId())).thenReturn(null);
        when(watchlistRepositoryMock.findByTitle("Star Wars")).thenReturn(null);
        when(watchlistRepositoryMock.getList()).thenReturn(List.of());
        when(watchlistRepositoryMock.getMaxItems()).thenReturn(10);

        // Act
        watchlistService.addOrUpdateWatchlistItems(newItem);

        // Assert

        verify(watchlistRepositoryMock, times(1)).addItem(watchlistItemCaptor.capture());
        WatchlistItem capturedItem = watchlistItemCaptor.getValue();

        assertTrue(capturedItem.equals(newItem));
    }

    @Test
    public void testAddOrUpdateWatchlistItems_updatesAnItemInRepository()
            throws DuplicateTitleException, WatchlistRepositorySizeExceeded {
        // Arrange
        WatchlistItem existingItem = new WatchlistItem("Star Wars", 7.7, "Good Movie", Priority.MEDIUM);
        existingItem.setId(1);
        WatchlistItem updatedItem = new WatchlistItem("Star Wars", 8.0, "Updated Comment", Priority.HIGH);
        updatedItem.setId(1);
        when(watchlistRepositoryMock.findWatchlistItemById(1)).thenReturn(existingItem);

        // Act
        watchlistService.addOrUpdateWatchlistItems(updatedItem);

        // Assert
        assertTrue(existingItem.equals(updatedItem));
    }
}
