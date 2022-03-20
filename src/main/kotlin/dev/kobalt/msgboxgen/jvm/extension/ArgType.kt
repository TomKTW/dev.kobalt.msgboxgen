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

import kotlinx.cli.ArgType
import java.awt.Color
import java.io.File


object ArgTypeSemiColonArray : ArgType<Array<String>>(true) {
    override val description: kotlin.String get() = "{ Strings separated by semicolon }"

    override fun convert(value: kotlin.String, name: kotlin.String): Array<kotlin.String> =
        value.split(";").toTypedArray()
}

object ArgTypeFile : ArgType<File>(true) {
    override val description: kotlin.String get() = "{ File }"

    override fun convert(value: kotlin.String, name: kotlin.String): File = value.toFile()
}

object ArgTypeColor : ArgType<Color>(true) {
    override val description: kotlin.String get() = "{ Color (Hexadecimal value) }"

    override fun convert(value: kotlin.String, name: kotlin.String): Color = value.toColor() ?: throw Exception()
}