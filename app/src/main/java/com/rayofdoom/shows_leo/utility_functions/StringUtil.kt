package com.rayofdoom.shows_leo.utility_functions

private const val START_CHAR = '['
private const val END_CHAR = ']'
private const val START_OFFSET = 2
private const val END_OFFSET = 1

fun String.parseAPIError(): String{
    return this.substring(this.indexOf(START_CHAR)+ START_OFFSET,this.indexOf(END_CHAR)- END_OFFSET)
}