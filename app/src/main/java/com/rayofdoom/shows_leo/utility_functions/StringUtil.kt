package com.rayofdoom.shows_leo.utility_functions

private const val START_CHAR = '['
private const val END_CHAR = ']'

fun String.parseError(): String{
    return this.substring(this.indexOf(START_CHAR)+2,this.indexOf(END_CHAR)-1)
}