package com.openclassrooms.watchlist.actuator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Endpoint(id = "release-notes")
public class ReleaseNotesEndPoint {

    private final ReleaseNote version10 = new ReleaseNote("1.0", Arrays.asList(
            "Homepage added",
            "Item creation form added",
            "View Watchlist page added"));

    private final ReleaseNote version11 = new ReleaseNote("1.1", Arrays.asList(
            "Reading from OMDB API",
            "Actuator endpoints added"));

    private final ObjectMapper objectMapper = new ObjectMapper();

    @ReadOperation
    public String releaseNotes() throws Exception {
        Map<String, ReleaseNote> releaseNotes = new HashMap<>();
        releaseNotes.put(version10.getVersion(), version10);
        releaseNotes.put(version11.getVersion(), version11);

        return objectMapper.writeValueAsString(releaseNotes);
    }

    @ReadOperation
    public String selectReleaseNotes(@Selector String selector) throws Exception {
        Map<String, ReleaseNote> releaseNotes = new HashMap<>();
        releaseNotes.put(version10.getVersion(), version10);
        releaseNotes.put(version11.getVersion(), version11);

        if (releaseNotes.containsKey(selector)) {
            return objectMapper.writeValueAsString(releaseNotes.get(selector));
        } else {
            return releaseNotes();
        }
    }

    // Inner class to represent a release note
    private static class ReleaseNote {
        private String version;
        private List<String> actions;

        public ReleaseNote(String version, List<String> actions) {
            this.version = version;
            this.actions = actions;
        }

        public String getVersion() {
            return version;
        }

        public List<String> getActions() {
            return actions;
        }
    }
}
