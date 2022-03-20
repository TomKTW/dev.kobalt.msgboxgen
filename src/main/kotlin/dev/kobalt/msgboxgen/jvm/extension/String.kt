/*
 * dev.kobalt.msgboxgen
 * Copyright (C) 2022 Tom.K
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.kobalt.msgboxgen.jvm.extension

import java.awt.Color
import java.awt.FontMetrics
import java.io.File

fun String.toFile(): File {
    return File(this)
}

fun String.toColor(): Color? = runCatching { Color.decode(this) }.getOrNull()

/**
 * Returns an array of strings, one for each line in the string after it has
 * been wrapped to fit lines of <var>maxWidth</var>. Lines end with any of
 * cr, lf, or cr lf. A line ending at the end of the string will not output a
 * further, empty string.
 *
 * @param fm       needed for string width calculations
 * @param maxWidth the max line width, in points
 * @return a non-empty list of strings
 *
 * Original source: https://github.com/jimm/DataVision/blob/d5b33e88091e2b9e5ae94d9eba74ec462601853f/jimm/util/StringUtils.java (Apache-2.0)
 * @author Jim Menard, [jim@jimmenard.com](mailto:jim@jimmenard.com) (~~[jimm@io.com](mailto:jimm@io.com)~~)
 */
internal fun String.wrap(fm: FontMetrics, maxWidth: Int): List<*> {
    val lines = splitIntoLines()
    if (lines.isEmpty()) return lines
    val strings = mutableListOf<String>()
    val iterator = lines.iterator()
    while (iterator.hasNext()) iterator.next().wrapLineInto(strings, fm, maxWidth)
    return strings
}

/**
 * Returns an array of strings, one for each line in the string. Lines end
 * with any of cr, lf, or cr lf. A line ending at the end of the string will
 * not output a further, empty string.
 *
 * @return a non-empty list of strings
 *
 * Original source: https://github.com/jimm/DataVision/blob/d5b33e88091e2b9e5ae94d9eba74ec462601853f/jimm/util/StringUtils.java (Apache-2.0)
 * @author Jim Menard, [jim@jimmenard.com](mailto:jim@jimmenard.com) (~~[jimm@io.com](mailto:jimm@io.com)~~)
 */
internal fun String.splitIntoLines(): List<String> {
    val strings = mutableListOf<String>()
    val len = length
    if (len == 0) {
        strings.add("")
        return strings
    }
    var lineStart = 0
    var i = 0
    while (i < len) {
        val c = this[i]
        if (c == '\r') {
            var newlineLength = 1
            if (i + 1 < len && this[i + 1] == '\n') newlineLength = 2
            strings.add(substring(lineStart, i))
            lineStart = i + newlineLength
            if (newlineLength == 2) // skip \n next time through loop
                ++i
        } else if (c == '\n') {
            strings.add(substring(lineStart, i))
            lineStart = i + 1
        }
        ++i
    }
    if (lineStart < len) strings.add(substring(lineStart))
    return strings
}


/**
 * Given a line of text and font metrics information, wrap the line and add
 * the new line(s) to <var>list</var>.
 *
 * @param list     an output list of strings
 * @param fm       font metrics
 * @param maxWidth maximum width of the line(s)
 *
 * Original source: https://github.com/jimm/DataVision/blob/d5b33e88091e2b9e5ae94d9eba74ec462601853f/jimm/util/StringUtils.java (Apache-2.0)
 * @author Jim Menard, [jim@jimmenard.com](mailto:jim@jimmenard.com) (~~[jimm@io.com](mailto:jimm@io.com)~~)
 */
internal fun String.wrapLineInto(list: MutableList<String>, fm: FontMetrics, maxWidth: Int) {
    var line = this
    var len = line.length
    var width = 0
    while (len > 0 && fm.stringWidth(line).also { width = it } > maxWidth) {
        // Guess where to split the line. Look for the next space before or after the guess.
        val guess = len * maxWidth / width
        var before = line.substring(0, guess).trim { it <= ' ' }
        width = fm.stringWidth(before)
        var pos: Int
        if (width > maxWidth) // Too long
            pos = line.findBreakBefore(guess) else { // Too short or possibly just right
            pos = line.findBreakAfter(guess)
            if (pos != -1) { // Make sure this doesn't make us too long
                before = line.substring(0, pos).trim { it <= ' ' }
                if (fm.stringWidth(before) > maxWidth) pos = line.findBreakBefore(guess)
            }
        }
        if (pos == -1) pos = guess // Split in the middle of the word
        list.add(line.substring(0, pos).trim { it <= ' ' })
        line = line.substring(pos).trim { it <= ' ' }
        len = line.length
    }
    if (len > 0) list.add(line)
}

/**
 * Returns the index of the first whitespace character or '-' in <var>line</var>
 * that is at or before <var>start</var>. Returns -1 if no such character is
 * found.
 *
 * @param start where to star looking
 *
 * Original source: https://github.com/jimm/DataVision/blob/d5b33e88091e2b9e5ae94d9eba74ec462601853f/jimm/util/StringUtils.java (Apache-2.0)
 * @author Jim Menard, [jim@jimmenard.com](mailto:jim@jimmenard.com) (~~[jimm@io.com](mailto:jimm@io.com)~~)
 */
internal fun String.findBreakBefore(start: Int): Int {
    for (i in start downTo 0) {
        val c = this[i]
        if (Character.isWhitespace(c) || c == '-') return i
    }
    return -1
}

/**
 * Returns the index of the first whitespace character or '-' in <var>line</var>
 * that is at or after <var>start</var>. Returns -1 if no such character is
 * found.
 *
 * @param start where to star looking
 *
 * Original source: https://github.com/jimm/DataVision/blob/d5b33e88091e2b9e5ae94d9eba74ec462601853f/jimm/util/StringUtils.java (Apache-2.0)
 * @author Jim Menard, [jim@jimmenard.com](mailto:jim@jimmenard.com) (~~[jimm@io.com](mailto:jimm@io.com)~~)
 */
internal fun String.findBreakAfter(start: Int): Int {
    val len = length
    for (i in start until len) {
        val c = this[i]
        if (Character.isWhitespace(c) || c == '-') return i
    }
    return -1
}