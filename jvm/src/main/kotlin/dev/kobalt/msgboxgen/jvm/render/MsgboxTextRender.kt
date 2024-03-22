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

@file:Suppress("unused")

package dev.kobalt.msgboxgen.jvm.render

import dev.kobalt.msgboxgen.jvm.extension.fontMetrics
import dev.kobalt.msgboxgen.jvm.extension.wrap
import dev.kobalt.msgboxgen.jvm.renderable.MsgboxRenderable
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D

class MsgboxTextRender(
    val x: Int,
    val y: Int,
    val w: Int,
    val text: String,
    val font: Font,
    val textColor: Color
) : MsgboxRenderable {

    private val fontMetrics = font.fontMetrics

    private val stringList = text.wrap(fontMetrics, w).map { it as String }

    val y1Offset = when (stringList.size) {
        1 -> 6
        2 -> 2
        else -> -1
    }

    val h = stringList.let { list ->
        return@let list.size * (fontMetrics.ascent + 2) + 14 + y1Offset
    }

    val x1 = x
    val x2 = x + w
    val y1 = y
    val y2 = y + h

    override fun render(graphics: Graphics2D) {
        graphics.apply {
            val oldColor = color
            font = this@MsgboxTextRender.font.deriveFont(Font.PLAIN)
            val fontMetrics = font.fontMetrics
            color = textColor
            stringList.forEachIndexed { index, string ->
                drawString(string, x, y + y1Offset + fontMetrics.ascent + index * (fontMetrics.ascent + 2))
            }
            //    drawRect(x, y, w, h)
            color = oldColor
        }
    }

}