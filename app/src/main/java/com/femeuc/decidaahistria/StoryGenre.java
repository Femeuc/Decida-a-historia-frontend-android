package com.femeuc.decidaahistria;

import android.widget.Toast;

public enum StoryGenre {
    ACTION (0) {
        @Override
        public String toString() {
            return "action";
        }

        @Override
        public String inPortuguese() {
            return "ação";
        }
    },
    ADVENTURE (1) {
        @Override
        public String toString() {
            return "adventure";
        }

        @Override
        public String inPortuguese() {
            return "aventura";
        }
    },
    COMEDY (2) {
        @Override
        public String toString() {
            return "comedy";
        }

        @Override
        public String inPortuguese() {
            return "comédia";
        }
    },
    DRAMA (3) {
        @Override
        public String toString() {
            return "drama";
        }

        @Override
        public String inPortuguese() {
            return "drama";
        }
    },
    FANTASY (4) {
        @Override
        public String toString() {
            return "fantasy";
        }

        @Override
        public String inPortuguese() {
            return "fantasia";
        }
    },
    HORROR (5) {
        @Override
        public String toString() {
            return "horror";
        }

        @Override
        public String inPortuguese() {
            return "terror";
        }
    },
    ISEKAI (6) {
        @Override
        public String toString() {
            return "isekai";
        }

        @Override
        public String inPortuguese() {
            return "isekai";
        }
    },
    MISTERY (7) {
        @Override
        public String toString() {
            return "mistery";
        }

        @Override
        public String inPortuguese() {
            return "mistério";
        }
    },
    ROMANCE (8) {
        @Override
        public String toString() {
            return "romance";
        }

        @Override
        public String inPortuguese() {
            return "romance";
        }
    },
    SCIENCE_FICTION (9) {
        @Override
        public String toString() {
            return "science_fiction";
        }

        @Override
        public String inPortuguese() {
            return "ficção científica";
        }
    },
    OTHER (10) {
        @Override
        public String toString() {
            return "other";
        }

        @Override
        public String inPortuguese() {
            return "outro";
        }
    },
    UNKNOWN (-1) {
        @Override
        public String toString() {
            return "unknown";
        }

        @Override
        public String inPortuguese() {
            return "desconhecido";
        }
    };

    public int id;

    StoryGenre(int id) {
        this.id = id;
    }

    @Override
    public abstract String toString();

    public abstract String inPortuguese();

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
