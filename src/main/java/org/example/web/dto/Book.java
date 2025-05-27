package org.example.web.dto;

import org.example.web.validation.ValidationGroups;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Book {
    private Integer id;

    @NotBlank(message = "Title must not be empty.")
    @Size(min = 1, max = 150, message = "Title must be between 1 and 150 characters.")
    @Pattern(regexp = "^[\\p{L}\\d\\s\\-:!?.,'\"()]+$", message = "Title contains invalid characters.")
    private String author;

    @NotBlank(message = "Title must not be empty.")
    @Size(min = 1, max = 150, message = "Title must be between 1 and 150 characters.")
    @Pattern(regexp = "^[\\p{L}\\d\\s\\-:!?.,'\"()]+$", message = "Title contains invalid characters.")
    private String title;

    @Digits(integer = 4, fraction = 0)
    private Integer size;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString(){
        return "Book{"+"id="+id+", author='"+author+'\''+", title='"+title+'\''+", size="+size+'\'';
    }
}
