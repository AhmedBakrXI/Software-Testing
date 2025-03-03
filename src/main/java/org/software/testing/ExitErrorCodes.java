package org.software.testing;

public enum ExitErrorCodes {
    MOVIE_TITLE_ERROR(1),
    MOVIE_ID_LETTERS_ERROR(2),
    MOVIE_ID_UNIQUE_ERROR(3),
    USER_NAME_ERROR(4);

    private final int code;
    ExitErrorCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
