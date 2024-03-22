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

package dev.kobalt.msgboxgen.jvm.render

import dev.kobalt.msgboxgen.jvm.extension.fontMetrics
import dev.kobalt.msgboxgen.jvm.renderable.MsgboxRenderable
import java.awt.Color
import java.awt.Font
import java.awt.GradientPaint
import java.awt.Graphics2D
import java.awt.font.TextAttribute

class MsgboxTitleBarRender(
    val x: Int,
    val y: Int,
    val w: Int,
    val h: Int,
    val text: String,
    val font: Font,
    val startColor: Color,
    val endColor: Color,
    val textColor: Color
) : MsgboxRenderable {

    val x1 = x
    val x2 = x + w
    val y1 = y
    val y2 = y + h

    val gradientPaint = GradientPaint(
        x1.toFloat(), y1.toFloat(), startColor,
        x2.toFloat(), y2.toFloat(), endColor
    )

    override fun render(graphics: Graphics2D) {
        graphics.apply {
            val oldColor = color
            paint = gradientPaint
            fillRect(x, y, w, h)
            color = textColor
            val attributes: MutableMap<TextAttribute, Any?> = HashMap()
            attributes[TextAttribute.TRACKING] = 0.09
            font = this@MsgboxTitleBarRender.font.deriveFont(attributes)
            val fontMetrics = font.fontMetrics
            var truncatedText = text
            var truncatedMeasure = fontMetrics.stringWidth(truncatedText)
            while (truncatedMeasure > w - 2 - 16) {
                truncatedText = truncatedText.substring(0, truncatedText.lastIndex - 1)
                truncatedMeasure = fontMetrics.stringWidth("$truncatedText...")
            }
            if (truncatedText != text) {
                truncatedText += "..."
            }
            drawString(truncatedText, x + 2, y + 2 + font.fontMetrics.ascent)
            drawString(truncatedText, x + 3, y + 2 + font.fontMetrics.ascent)
            color = oldColor
        }
    }

}