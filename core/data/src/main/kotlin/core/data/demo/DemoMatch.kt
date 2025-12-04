package core.data.demo

import core.model.entity.Club
import core.model.entity.Match
import core.model.entity.Stadium
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import kotlin.random.Random

object DemoMatch {
    val matchs = generateRandomMatches()
}

private fun generateRandomMatches(): List<Match> {
    return List(50) { generateRandomMatch() }
}

private fun generateRandomMatch(): Match {
    return Match(
        club1 = getRandomClub(),
        club2 = getRandomClub(),
        date = generateRandomDate(
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusYears(1)
        ),
        stadium = getRandomStadium()
    )
}

private fun getRandomStadium(): Stadium {
    return DemoStadium.stadium.random()
}

private fun getRandomClub(): Club {
    return DemoClub.teams.flatMap { it.clubs }.random()
}

private fun generateRandomDate(startDate: LocalDate, endDate: LocalDate): LocalDateTime {
    val daysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 1
    val randomDays = Random.nextLong(daysBetween)
    val randomDate = startDate.plusDays(randomDays)
    val secondsInDay = 24L * 60L * 60L
    val randomSeconds = Random.nextLong(secondsInDay)
    val randomTime = LocalTime.ofSecondOfDay(randomSeconds)
    return randomDate.atTime(randomTime)
}