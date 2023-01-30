package com.example.getrecipe

fun removeTagsFromString(string: String): String {
    return string.replace(Regex("<[^>]*>"), "")
}