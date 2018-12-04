package days


private enum class EventType {
    SHIFT_START,
    SLEEP_START,
    SLEEP_END
}

private class Event private constructor(val guardId: Int, val timestampMinute: Int, val eventType: EventType) {
    companion object {
        fun parse(eventLine: String, prevEventGuardId: Int?): Event {
            val (_, time, firstWord, secondWord) = eventLine.split(" ")

            val minute = time.substring(3, 5).toInt()
            val eventType = when (firstWord) {
                "Guard" -> EventType.SHIFT_START
                "falls" -> EventType.SLEEP_START
                else    -> EventType.SLEEP_END
            }

            val guardId = when {
                eventType == EventType.SHIFT_START -> secondWord.replace("#", "").toInt()
                prevEventGuardId != null           -> prevEventGuardId
                else                               -> throw Exception("Error parsing, unable to determine current guard")
            }

            return Event(guardId, minute, eventType)
        }
    }
}

private class Guard private constructor(val guardId: Int, val sleepIntervals: List<SleepInterval>) {
    companion object {
        fun of(guardId: Int, events: List<Event>): Guard {
            val sleepIntervals: MutableList<SleepInterval> = mutableListOf()

            var currentShiftSleepEvents: MutableList<Event> = mutableListOf()

            val storeCurrentShiftSleepIntervals = {
                val currentShiftSleepIntervals =
                    sequence {
                        for (index in 0 until currentShiftSleepEvents.size step 2) {
                            yield(
                                SleepInterval(
                                    currentShiftSleepEvents[index].timestampMinute,
                                    currentShiftSleepEvents[index + 1].timestampMinute
                                )
                            )
                        }
                    }.toList()

                sleepIntervals.addAll(currentShiftSleepIntervals)
            }

            events.forEach {
                when (it.eventType) {
                    EventType.SHIFT_START -> {
                        if (currentShiftSleepEvents.isNotEmpty()) {
                            storeCurrentShiftSleepIntervals()
                        }
                        currentShiftSleepEvents = mutableListOf()
                    }
                    else -> currentShiftSleepEvents.add(it)
                }
            }
            storeCurrentShiftSleepIntervals()

            return Guard(guardId, sleepIntervals)
        }
    }

    val totalMinutesAsleep = sleepIntervals.sumBy { it.totalMinutes }

    fun getTimesAsleepAtMinute(): Map<Int, Int> {
        if (sleepIntervals.isEmpty()) {
            return mapOf()
        }

        val timesAsleepAtMinute: MutableMap<Int, Int> = mutableMapOf()
        sleepIntervals.forEach {
            for (minute in it.startMinute..it.endMinute) {
                timesAsleepAtMinute[minute] = timesAsleepAtMinute.getOrDefault(minute, 0) + 1
            }
        }
        return timesAsleepAtMinute
    }

    val mostOftenAsleepMinute: Int? = getTimesAsleepAtMinute().maxBy { it.value }?.key
}

private data class SleepInterval(val startMinute: Int, val endMinute: Int) {
    val totalMinutes = endMinute - startMinute
}

fun day04a(eventLines: List<String>): Int {
    val events =
        eventLines
            .sorted()
            .fold(Pair<MutableList<Event>, Int?>(mutableListOf(), null)) {
                    (events, prevEventGuardId), eventLine ->
                val event = Event.parse(eventLine, prevEventGuardId)
                events.add(event)
                Pair(events, event.guardId)
            }
            .first

    val guardsWithShifts =
        events
            .groupBy { it.guardId }
            .map { Guard.of(it.key, it.value) }

    val mostAsleepGuard = guardsWithShifts.maxBy { it.totalMinutesAsleep }!!

    return mostAsleepGuard.mostOftenAsleepMinute!! * mostAsleepGuard.guardId
}

fun day04b(eventLines: List<String>): Int {
    val events =
        eventLines
            .sorted()
            .fold(Pair<MutableList<Event>, Int?>(mutableListOf(), null)) {
                 (events, prevEventGuardId), eventLine ->
                    val event = Event.parse(eventLine, prevEventGuardId)
                    events.add(event)
                    Pair(events, event.guardId)
             }
            .first

    val guardsWithShifts =
        events
            .groupBy { it.guardId }
            .map { Guard.of(it.key, it.value) }

    val guardThatWasMostAsleepOnASingleMinute =
        guardsWithShifts
            .maxBy { guard -> guard.getTimesAsleepAtMinute().map { it.value }.max() ?: -1 }!!

    return guardThatWasMostAsleepOnASingleMinute.guardId * guardThatWasMostAsleepOnASingleMinute.mostOftenAsleepMinute!!
}
