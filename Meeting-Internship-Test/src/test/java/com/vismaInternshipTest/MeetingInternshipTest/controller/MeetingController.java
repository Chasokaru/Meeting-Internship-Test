
package com.vismaInternshipTest.MeetingInternshipTest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vismaInternshipTest.MeetingInternshipTest.model.Meeting;
import com.vismaInternshipTest.MeetingInternshipTest.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MeetingController {

    private static final String MEETINGS_FILE_PATH = "meetings.json";

    private final ObjectMapper objectMapper;

    @Autowired
    public MeetingController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @PostMapping("/meetings")
    public void createMeeting(@RequestBody Meeting meeting) throws IOException {
        List<Meeting> meetings = getMeetings();
        meetings.add(meeting);
        writeMeetings(meetings);
    }

    @DeleteMapping("/meetings/{meetingId}")
    public void deleteMeeting(@PathVariable int meetingId, @RequestParam String responsiblePerson) throws IOException {
        List<Meeting> meetings = getMeetings();
        boolean foundMeeting = false;
        for (Meeting meeting : meetings) {
            if (meeting.getId() == meetingId) {
                foundMeeting = true;
                if (!meeting.getResponsiblePerson().equals(responsiblePerson)) {
                    throw new IllegalArgumentException("Only the responsible person can delete the meeting.");
                }
                meetings.remove(meeting);
                break;
            }
        }
        if (!foundMeeting) {
            throw new IllegalArgumentException("Meeting not found.");
        }
        writeMeetings(meetings);
    }

    @PostMapping("/meetings/{meetingId}/attendees")
    public void addAttendee(@PathVariable int meetingId, @RequestBody Person person) throws IOException {
        List<Meeting> meetings = getMeetings();
        boolean foundMeeting = false;
        for (Meeting meeting : meetings) {
            if (meeting.getId() == meetingId) {
                foundMeeting = true;
                meeting.getAttendees().add(person);
                break;
            }
        }
        if (!foundMeeting) {
            throw new IllegalArgumentException("Meeting not found.");
        }
        writeMeetings(meetings);
    }
    

    @DeleteMapping("/meetings/{meetingId}/attendees/{personId}")
    public void removeAttendee(@PathVariable int meetingId, @PathVariable int personId) throws IOException {
        List<Meeting> meetings = getMeetings();
        boolean foundMeeting = false;
        for (Meeting meeting : meetings) {
            if (meeting.getId() == meetingId) {
                foundMeeting = true;
                meeting.getAttendees().removeIf(person -> person.getId() == personId);
                break;
            }
        }
        if (!foundMeeting) {
            throw new IllegalArgumentException("Meeting not found.");
        }
        writeMeetings(meetings);
    }

    @GetMapping("/meetings")
    public List<Meeting> getMeetings(
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String responsiblePerson,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to,
            @RequestParam(required = false) Integer minAttendees
    ) throws IOException {
        List<Meeting> meetings = getMeetings();
        if (description != null) {
            meetings = meetings.stream()
                    .filter(meeting -> meeting.getDescription().contains(description))
                    .collect(Collectors.toList());
        }
        if (responsiblePerson != null) {
            meetings = meetings.stream()
                    .filter(meeting -> meeting.getResponsiblePerson().equals(responsiblePerson))
                    .collect(Collectors.toList());
        }
        if (category != null) {
            meetings = meetings.stream()
                    .filter(meeting -> meeting.getCategory().equals(category))
                    .collect(Collectors.toList());
        }
        if (type != null) {
            meetings = meetings.stream()
                    .filter(meeting -> meeting.getType().equals(type))
                    .collect(Collectors.toList());
        }
        if (from != null) {
            meetings = meetings.stream()
                    .filter(meeting -> meeting.getDate().compareTo(from) >= 0)
                    .collect(Collectors.toList());
        }
        if (to != null) {
            meetings = meetings.stream()
                    .filter(meeting -> meeting.getDate().compareTo(to) <= 0)
                    .collect(Collectors.toList());
        }
        if (minAttendees != null) {
            meetings = meetings.stream()
                    .filter(meeting -> meeting.getAttendees().size() >= minAttendees)
                    .collect(Collectors.toList());
        }
        return meetings;
    }

    private List<Meeting> getMeetings() throws IOException {
        File file = new ClassPathResource(MEETINGS_FILE_PATH).getFile();
        String json = new String(FileCopyUtils.copyToByteArray(file), StandardCharsets.UTF_8);
        return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, Meeting.class));
    }

    private void writeMeetings(List<Meeting> meetings) throws IOException {
        File file = new ClassPathResource(MEETINGS_FILE_PATH).getFile();
        FileWriter writer = new FileWriter(file);
        objectMapper.writeValue(writer, meetings);
        writer.close();
    }
}
