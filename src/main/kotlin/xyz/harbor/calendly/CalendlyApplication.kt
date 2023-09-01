package xyz.harbor.calendly

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@EnableR2dbcRepositories
@SpringBootApplication
class CalendlyApplication

fun main(args: Array<String>) {
	runApplication<CalendlyApplication>(*args)
}
