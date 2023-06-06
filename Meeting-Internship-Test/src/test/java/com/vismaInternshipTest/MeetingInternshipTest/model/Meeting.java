package com.vismaInternshipTest.MeetingInternshipTest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Meeting {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("responsiblePerson")
    private Person responsiblePerson;

    @JsonProperty("description")
    private String description;

    @JsonProperty("category")
    private Category category;

    @JsonProperty("type")
    private Type type;

    @JsonProperty("startDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @JsonProperty("endDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    @JsonProperty("attendees")
    private List<Person> attendees;

    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

 

    public Meeting(int id, String name, Person responsiblePerson, String description, Category category, Type type, LocalDateTime startDate, LocalDateTime endDate, List<Person> attendees) {
        this.id = id;
        this.name = name;
        this.responsiblePerson = responsiblePerson;
        this.description = description;
        this.category = category;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.attendees = attendees;
        this.date = startDate.toLocalDate();
    }

    // Getter and setter methods

    public Meeting(long l, String string, LocalDate now, String string2, Category categoryA, Type typeA, int i) {
    }

    public enum Category {
        @JsonProperty("CodeMonkey")
        CODE_MONKEY,
        @JsonProperty("Hub")
        HUB,
        @JsonProperty("Short")
        SHORT,
        @JsonProperty("TeamBuilding")
        TEAM_BUILDING, CATEGORY_B, CATEGORY_A
    }

    public enum Type {
        @JsonProperty("Live")
        LIVE,
        @JsonProperty("InPerson")
        IN_PERSON, TYPE_A, TYPE_B
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(Person responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public List<Person> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<Person> attendees) {
        this.attendees = attendees;
    }

    public LocalDate getDate() {
        return date;
    }
}