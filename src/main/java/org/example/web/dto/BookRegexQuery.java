package org.example.web.dto;

import javax.validation.constraints.NotBlank;

public class BookRegexQuery {

    @NotBlank(message = "Regex cannot be empty.")
    private String queryRegex;

    public String getQueryRegex() {
        return queryRegex;
    }

    public void setQueryRegex(String queryRegex) {
        this.queryRegex = queryRegex;
    }
}
