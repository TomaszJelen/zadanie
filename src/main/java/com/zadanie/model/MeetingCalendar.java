package com.zadanie.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MeetingCalendar {
    @JsonProperty("working_hours")
    private DurationTime workingHours;
    @JsonProperty("planned_meeting")
    private List<DurationTime> plannedMeeting;

    public DurationTime getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(DurationTime workingHours) {
        this.workingHours = workingHours;
    }

    public List<DurationTime> getPlannedMeeting() {
        return plannedMeeting;
    }

    public void setPlannedMeeting(List<DurationTime> plannedMeeting) {
        this.plannedMeeting = plannedMeeting;
    }

    @Override
    public String toString() {
        return "MeetingCalendar{" +
                "workingHours=" + workingHours +
                ", plannedMeeting=" + plannedMeeting +
                '}';
    }
}
