package xyz.harbor.calendly.util

object Utils {
    // generate a random string of length n
    fun genId(n: Int = 20): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        return (1..n).map { chars.random() }.joinToString("")
    }
}