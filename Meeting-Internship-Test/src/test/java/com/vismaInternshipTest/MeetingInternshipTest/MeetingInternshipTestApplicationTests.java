package com.vismaInternshipTest.MeetingInternshipTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vismaInternshipTest.MeetingInternshipTest.model.Meeting;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class MeetingInternshipTestApplicationTests {

    private static final String FILE_NAME = "meetings.json";

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MeetingInternshipTestApplication.class, args);

        // Example usage of saveMeeting and getMeetings methods
        Meeting meeting1 = new Meeting(1L, "Meeting 1", LocalDate.now(), "Person 1", Meeting.Category.CATEGORY_A, Meeting.Type.TYPE_A, 5);
		Meeting meeting2 = new Meeting(2L, "Meeting 2", LocalDate.now(), "Person 2", Meeting.Category.CATEGORY_B, Meeting.Type.TYPE_A, 10);


        saveMeeting(meeting1);
        saveMeeting(meeting2);

        List<Meeting> meetings = getMeetings();
        for (Meeting meeting : meetings) {
            System.out.println(meeting);
        }
    }

	

    public static List<Meeting> getMeetings() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Meeting.class));
    }

    public static void saveMeeting(Meeting meeting) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(FILE_NAME);
        List<Meeting> meetings = getMeetings();
        meetings.add(meeting);
        objectMapper.writeValue(file, meetings);
    }
}
