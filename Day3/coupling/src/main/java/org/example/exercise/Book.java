package org.example.exercise;

public class Book {
  private String id;
  private String title;
  private String author;
  private boolean available;

  public Book(String id, String title, String author) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.available = true;
  }

  // Getters and setters
  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }
}
