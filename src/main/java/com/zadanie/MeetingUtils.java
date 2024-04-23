package com.zadanie;

import com.zadanie.model.DurationTime;
import com.zadanie.model.MeetingCalendar;
import com.zadanie.model.TimePoint;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.zadanie.model.TimePoint.Status.END;
import static com.zadanie.model.TimePoint.Status.START;

public class MeetingUtils {
    private MeetingUtils() {
    }


    /**
     * Funkcja realizujaca zadanie "Spotkajmy sie"
     * @param durationInput String zawierajacy oczekiwany czas trwania spotkania (zamkniety w nawisach [])
     * @param calendarInputs Stringi zawierajace kalendarze z okreslonymi godzinami pracy oraz zaplanowanymi juz spotkaniami
     * @return String zawierajacy zakresy czasu, w ktorych mozna zorganizowac spotkanie o oczekiwanej dlugosci
     * @throws JsonProcessingException
     */
    public static String zadanie(String durationInput, String... calendarInputs) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES.mappedFeature());

        List<MeetingCalendar> calendars = new ArrayList<>();
        for (String calendarInput : calendarInputs) {
            MeetingCalendar calendar = objectMapper.readValue(calendarInput, MeetingCalendar.class);
            calendars.add(calendar);
        }

        List<List<LocalTime>> freeTimeList = freeTimeList(new ArrayList<>(calendars.stream().flatMap(meetingCalendar -> getTimePoints(meetingCalendar).stream())
                .toList()));

        List<List<LocalTime>> result = getPossibleMeetings(StringUtils.substringBetween(durationInput, "[", "]"), freeTimeList);
        List<List<String>> reformatedResult = result.stream().map(localTimes -> localTimes.stream().map(localTime -> '"' + localTime.toString() + '"')
                .toList()).toList();

        return reformatedResult.toString();
    }


    /**
     * Funkcja wybierajaca przedzialy czasu wolnego w ktorych zmiesci sie spotkanie o dlugosci podanej w durationInput
     * @param durationInput String zawierajacy oczekiwany czas trwania spotkania (bez nawiasow [])
     * @param freeTimeList lista list w ktorych zawieraja sie przedzialy wspolnego wolnego czasu
     * @return lista list w ktorych zawieraja sie przedzialy wspolnego wolnego czasu w ktorych mozna zorganizowac spotkanie o oczekiwanej dlugosci
     */
    private static List<List<LocalTime>> getPossibleMeetings(String durationInput, List<List<LocalTime>> freeTimeList) {
        Duration duration = Duration.between(LocalTime.MIN, LocalTime.parse(durationInput));
        List<List<LocalTime>> result = freeTimeList.stream().filter(
                localTimes -> (duration.compareTo(Duration.between(localTimes.get(0), localTimes.get(1))) <= 0)
        ).toList();
        return result;
    }

    /**
     * Funkcja przerabiajca MeetingCalendar na liste TimePoint - momentow zmiany statusu dostepnosci w ciagu dnia
     * @param calendar obiekt klasy MeetingCalendar zawierajacy kalendarz z okreslonymi godzinami pracy oraz zaplanowanymi juz spotkaniami
     * @return lista TimePoint - punktow w czasie zmieniajcej status dostepnosci w ciagu dnia
     */
    private static List<TimePoint> getTimePoints(MeetingCalendar calendar) {
        List<TimePoint> fromCalendList = new ArrayList<>();
        fromCalendList.add(new TimePoint(LocalTime.MIN, START)); //osoba nie jest dostepna od poczatku dnia
        fromCalendList.add(new TimePoint(calendar.getWorkingHours().getStart(), END)); //poczatek pracy koncem niedostepnosci
        fromCalendList.add(new TimePoint(calendar.getWorkingHours().getEnd(), START)); //koniec pracy poczatkiem niedostepnosci
        fromCalendList.add(new TimePoint(LocalTime.MAX, END)); //osoba nie jest dostepna do konca dnia
        for (DurationTime dt : calendar.getPlannedMeeting()) {
            fromCalendList.add(new TimePoint(dt.getStart(), START));
            fromCalendList.add(new TimePoint(dt.getEnd(), END));
        }
        return fromCalendList;
    }


    /**
     * Funkcja zamieniajaca liste momentow zmiany statusu dostepnosci na liste przedzialow czasu wolnego - dla jednej lub sumarycznie dla wielu osob
     * @param timePointList lista TimePoint - punktow w czasie zmieniajcych status dostepnosci w ciagu dnia (dla jednej lub wiekszej ilosci osob)
     * @return lista list w ktorych zawieraja sie przedzialy wolnego czasu (godziny rozpoczecia i konca). Dla wielu osob sa to przedzialy wspolnego czasu wolnego.
     */
    private static List<List<LocalTime>> freeTimeList(List<TimePoint> timePointList) {
        timePointList.sort(Comparator.comparing(TimePoint::getTime).thenComparing(TimePoint::getStatus));
        int meetingCount = 0; //oznacza rowniez czas poza godzinami pracy
        List<List<LocalTime>> freeTimeDurations = new ArrayList<>();
        List<LocalTime> freeTimeDuration = null;
        for (TimePoint timePoint : timePointList) {
            if (timePoint.getStatus() == START) {
                if (meetingCount == 0 && freeTimeDuration != null) {
                    freeTimeDuration.add(timePoint.getTime());
                    freeTimeDurations.add(freeTimeDuration);
                }
                meetingCount++;
            } else {
                meetingCount--;
                if (meetingCount == 0) {
                    freeTimeDuration = new ArrayList<>();
                    freeTimeDuration.add(timePoint.getTime());
                }
            }
        }
        return freeTimeDurations;
    }
}
