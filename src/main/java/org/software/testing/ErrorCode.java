package org.software.testing;

public enum ErrorCode {
    MOVIE_TITLE_ERROR(1),
    MOVIE_ID_LETTERS_ERROR(2),
    MOVIE_ID_UNIQUE_ERROR(3),
    USER_NAME_ERROR(4),
    USER_ID_ERROR(5),
    MOVIE_GENRE_ERROR(6),
    USER_ID_LENGTH_ERROR(7),
    MOVIE_NOT_FOUND_ERROR(8);

    private final int code;
    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
