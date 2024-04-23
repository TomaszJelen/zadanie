package com.zadanie;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MeetingUtilsTest {
	private final String CALENDAR_INPUT1 = """
				{
				working_hours: {
				start: "09:00",
				end: "19:55"
				},
				planned_meeting: [
				{
				start: "09:00",
				end: "10:30"
				},
				{
				start: "12:00",
				end: "13:00"
				},
				{
				start: "16:00",
				end: "18:00"
				}
				]
				}""";
	private final String CALENDAR_INPUT2 = """
				{
				working_hours: {
				start: "10:00",
				end: "18:30"
				},
				planned_meeting: [
				{
				start: "10:00",
				end: "11:30"
				},
				{
				start: "12:30",
				end: "14:30"
				},
				{
				start: "14:30",
				end: "15:00"
				},
				{
				start: "16:00",
				end: "17:00"
				}
				]
				}""";
	private final String CALENDAR_INPUT3 = """
				{
				working_hours: {
				start: "09:00",
				end: "19:55"
				},
				planned_meeting: [
				{
				start: "10:30",
				end: "12:00"
				},
				{
				start: "13:00",
				end: "16:00"
				},
				{
				start: "18:00",
				end: "19:55"
				}
				]
				}""";
	private final String CALENDAR_INPUT4 = """
				{
				working_hours: {
				start: "10:00",
				end: "18:30"
				},
				planned_meeting: [
				{
				start: "11:30",
				end: "12:30"
				},
				{
				start: "15:00",
				end: "16:00"
				},
				{
				start: "17:00",
				end: "18:30"
				}
				]
				}""";
	private final String CALENDAR_INPUT5 = """
				{
				working_hours: {
				start: "00:00",
				end: "13:25"
				},
				planned_meeting: [
				{
				start: "01:00",
				end: "02:00"
				},
				{
				start: "04:05",
				end: "05:15"
				},
				{
				start: "07:55",
				end: "09:30"
				},
				{
				start: "11:00",
				end: "11:30"
				}
				]
				}""";
	private final String CALENDAR_INPUT6 = """
				{
				working_hours: {
				start: "08:00",
				end: "16:00"
				},
				planned_meeting: [
				{
				start: "10:15",
				end: "11:45"
				},
				{
				start: "13:45",
				end: "15:15"
				}
				]
				}""";
	private final String CALENDAR_INPUT7 = """
				{
				working_hours: {
				start: "08:30",
				end: "15:15"
				},
				planned_meeting: [
				{
				start: "08:30",
				end: "10:00"
				},
				{
				start: "10:30",
				end: "13:00"
				},
				{
				start: "13:45",
				end: "15:15"
				}
				]
				}""";
	private final String CALENDAR_INPUT8 = """
				{
				working_hours: {
				start: "00:00",
				end: "00:30"
				},
				planned_meeting: []
				}""";
	private final String DURATION_INPUT1 = "[00:30]";
	private final String DURATION_INPUT2 = "[01:00]";
	private final String DURATION_INPUT3 = "[01:30]";

    @Test
    void zadanieTest11() throws JsonProcessingException {
        assertEquals("[[\"11:30\", \"12:00\"], [\"15:00\", \"16:00\"], [\"18:00\", \"18:30\"]]", MeetingUtils.zadanie(DURATION_INPUT1, CALENDAR_INPUT1, CALENDAR_INPUT2));
    }
	@Test
	void zadanieTest12() throws JsonProcessingException {
		assertEquals("[[\"12:00\", \"12:30\"], [\"17:00\", \"18:00\"]]", MeetingUtils.zadanie(DURATION_INPUT1, CALENDAR_INPUT3, CALENDAR_INPUT2));
	}
	@Test
	void zadanieTest13() throws JsonProcessingException {
		assertEquals("[[\"10:30\", \"11:30\"], [\"13:00\", \"15:00\"]]", MeetingUtils.zadanie(DURATION_INPUT1, CALENDAR_INPUT1, CALENDAR_INPUT4));
	}
	@Test
	void zadanieTest14() throws JsonProcessingException {
		assertEquals("[[\"10:00\", \"10:30\"], [\"12:30\", \"13:00\"], [\"16:00\", \"17:00\"]]", MeetingUtils.zadanie(DURATION_INPUT1, CALENDAR_INPUT3, CALENDAR_INPUT4));
	}
	@Test
	void zadanieTest15() throws JsonProcessingException {
		assertEquals("[[\"10:30\", \"11:00\"], [\"11:30\", \"12:00\"]]", MeetingUtils.zadanie(DURATION_INPUT1, CALENDAR_INPUT1, CALENDAR_INPUT5));
	}
	@Test
	void zadanieTest16() throws JsonProcessingException {
		assertEquals("[[\"11:30\", \"12:30\"]]", MeetingUtils.zadanie(DURATION_INPUT1, CALENDAR_INPUT5, CALENDAR_INPUT2));
	}
	@Test
	void zadanieTest17() throws JsonProcessingException {
		assertEquals("[[\"00:00\", \"00:30\"]]", MeetingUtils.zadanie(DURATION_INPUT1, CALENDAR_INPUT5, CALENDAR_INPUT8));
	}

	@Test
	void zadanieTest111() throws JsonProcessingException {
		assertEquals("[[\"12:00\", \"12:30\"]]", MeetingUtils.zadanie(DURATION_INPUT1, CALENDAR_INPUT3, CALENDAR_INPUT2, CALENDAR_INPUT6));
	}
	@Test
	void zadanieTest112() throws JsonProcessingException {
		assertEquals("[[\"13:00\", \"13:45\"]]", MeetingUtils.zadanie(DURATION_INPUT1, CALENDAR_INPUT1, CALENDAR_INPUT4, CALENDAR_INPUT7));
	}

	@Test
	void zadanieTest21() throws JsonProcessingException {
		assertEquals("[[\"15:00\", \"16:00\"]]", MeetingUtils.zadanie(DURATION_INPUT2, CALENDAR_INPUT1, CALENDAR_INPUT2));
	}
	@Test
	void zadanieTest22() throws JsonProcessingException {
		assertEquals("[[\"17:00\", \"18:00\"]]", MeetingUtils.zadanie(DURATION_INPUT2, CALENDAR_INPUT3, CALENDAR_INPUT2));
	}
	@Test
	void zadanieTest23() throws JsonProcessingException {
		assertEquals("[[\"10:30\", \"11:30\"], [\"13:00\", \"15:00\"]]", MeetingUtils.zadanie(DURATION_INPUT2, CALENDAR_INPUT1, CALENDAR_INPUT4));
	}
	@Test
	void zadanieTest24() throws JsonProcessingException {
		assertEquals("[[\"10:00\", \"10:30\"], [\"12:30\", \"13:00\"], [\"16:00\", \"17:00\"]]", MeetingUtils.zadanie(DURATION_INPUT1, CALENDAR_INPUT3, CALENDAR_INPUT4));
	}
	@Test
	void zadanieTest25() throws JsonProcessingException {
		assertEquals("[]", MeetingUtils.zadanie(DURATION_INPUT2, CALENDAR_INPUT1, CALENDAR_INPUT5));
	}
	@Test
	void zadanieTest26() throws JsonProcessingException {
		assertEquals("[[\"11:30\", \"12:30\"]]", MeetingUtils.zadanie(DURATION_INPUT2, CALENDAR_INPUT5, CALENDAR_INPUT2));
	}

	@Test
	void zadanieTest31() throws JsonProcessingException {
		assertEquals("[]", MeetingUtils.zadanie(DURATION_INPUT3, CALENDAR_INPUT1, CALENDAR_INPUT2));
	}
	@Test
	void zadanieTest32() throws JsonProcessingException {
		assertEquals("[]", MeetingUtils.zadanie(DURATION_INPUT3, CALENDAR_INPUT3, CALENDAR_INPUT2));
	}
	@Test
	void zadanieTest33() throws JsonProcessingException {
		assertEquals("[[\"13:00\", \"15:00\"]]", MeetingUtils.zadanie(DURATION_INPUT3, CALENDAR_INPUT1, CALENDAR_INPUT4));
	}
	@Test
	void zadanieTest34() throws JsonProcessingException {
		assertEquals("[]", MeetingUtils.zadanie(DURATION_INPUT3, CALENDAR_INPUT3, CALENDAR_INPUT4));
	}
	@Test
	void zadanieTest35() throws JsonProcessingException {
		assertEquals("[]", MeetingUtils.zadanie(DURATION_INPUT3, CALENDAR_INPUT1, CALENDAR_INPUT5));
	}
	@Test
	void zadanieTest36() throws JsonProcessingException {
		assertEquals("[]", MeetingUtils.zadanie(DURATION_INPUT3, CALENDAR_INPUT5, CALENDAR_INPUT2));
	}
}