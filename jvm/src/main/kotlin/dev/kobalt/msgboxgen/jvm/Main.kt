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

package dev.kobalt.msgboxgen.jvm

import dev.kobalt.msgboxgen.jvm.extension.*
import dev.kobalt.msgboxgen.jvm.generator.MsgboxGenerator
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType

fun main(args: Array<String>) {
    val parser = ArgParser("msgboxgen")
    val title by parser.option(ArgType.String, "title", null, null)
    val message by parser.option(ArgType.String, "message", null, null)
    val buttons by parser.option(ArgTypeSemiColonArray, "buttons", null, null)
    val iconPath by parser.option(ArgTypeFile, "iconPath", null, null)
    val fontPath by parser.option(ArgTypeFile, "fontPath", null, null)
    val width by parser.option(ArgType.Int, "width", null, null)
    val titleBarStartColor by parser.option(ArgTypeColor, "titleBarStartColor", null, null)
    val titleBarEndColor by parser.option(ArgTypeColor, "titleBarEndColor", null, null)
    val titleBarTextColor by parser.option(ArgTypeColor, "titleBarTextColor", null, null)
    val windowBackgroundColor by parser.option(ArgTypeColor, "windowBackgroundColor", null, null)
    val messageTextColor by parser.option(ArgTypeColor, "messageTextColor", null, null)
    val buttonTextColor by parser.option(ArgTypeColor, "buttonTextColor", null, null)
    val buttonBackgroundColor by parser.option(ArgTypeColor, "buttonBackgroundColor", null, null)
    val outputPath by parser.option(ArgTypeFile, "outputPath", null, null)
    parser.parse(args)
    val outputStream = outputPath?.outputStream()
    MsgboxGenerator().generate(
        outputStream ?: System.out,
        title,
        message,
        buttons,
        iconPath?.toBufferedImage(),
        fontPath?.toFont(),
        width,
        titleBarStartColor,
        titleBarEndColor,
        titleBarTextColor,
        windowBackgroundColor,
        messageTextColor,
        buttonTextColor,
        buttonBackgroundColor
    )
    outputStream?.close()
}