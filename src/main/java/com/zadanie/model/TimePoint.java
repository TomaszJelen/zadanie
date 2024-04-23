package com.zadanie.model;

import java.time.LocalTime;


public class TimePoint {
    public enum Status {
        START,
        END
    }
    LocalTime time;
    Status status;

    public TimePoint(LocalTime time, Status status) {
        this.time = time;
        this.status = status;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ListPair{" +
                "time=" + time +
                ", status=" + status +
                '}';
    }

}
