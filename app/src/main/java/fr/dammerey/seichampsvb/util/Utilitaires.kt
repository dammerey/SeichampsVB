package fr.dammerey.seichampsvb.util

fun String.capitalizeFirst(): String {
    return this.lowercase().replaceFirstChar { it.uppercaseChar() }
}