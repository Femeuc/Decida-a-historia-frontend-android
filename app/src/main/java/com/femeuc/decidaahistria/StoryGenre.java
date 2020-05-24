package com.femeuc.decidaahistria;

import android.widget.Toast;

public enum StoryGenre {
    ACTION (0) {
        @Override
        public String toString() {
            return "action";
        }
    },
    ADVENTURE (1) {
        @Override
        public String toString() {
            return "adventure";
        }
    },
    COMEDY (2) {
        @Override
        public String toString() {
            return "comedy";
        }
    },
    DRAMA (3) {
        @Override
        public String toString() {
            return "drama";
        }
    },
    FANTASY (4) {
        @Override
        public String toString() {
            return "fantasy";
        }
    },
    HORROR (5) {
        @Override
        public String toString() {
            return "horror";
        }
    },
    ISEKAI (6) {
        @Override
        public String toString() {
            return "isekai";
        }
    },
    MISTERY (7) {
        @Override
        public String toString() {
            return "mistery";
        }
    },
    ROMANCE (8) {
        @Override
        public String toString() {
            return "romance";
        }
    },
    SCIENCE_FICTION (9) {
        @Override
        public String toString() {
            return "science_fiction";
        }
    },
    OTHER (10) {
        @Override
        public String toString() {
            return "other";
        }
    },
    UNKNOWN (-1) {
        @Override
        public String toString() {
            return "unknown";
        }
    };

    public int id;

    StoryGenre(int id) {
        this.id = id;
    }

    @Override
    public abstract String toString();

    public static StoryGenre getById(int id) {
        for(StoryGenre e : values()) {
            if(e.id == id) return e;
        }
        return UNKNOWN;
    }

    public static int getGenreId(String genre) {
        return valueOf(genre.toUpperCase()).id;
    }
}
