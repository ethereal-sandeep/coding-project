package xyz.harbor.calendly

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CalendlyApplication

fun main(args: Array<String>) {
	runApplication<CalendlyApplication>(*args)
}
