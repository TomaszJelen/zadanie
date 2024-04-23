package com.zadanie.model;

import java.time.LocalTime;

public class DurationTime {
    private LocalTime start;
    private LocalTime end;

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "DurationTime{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
