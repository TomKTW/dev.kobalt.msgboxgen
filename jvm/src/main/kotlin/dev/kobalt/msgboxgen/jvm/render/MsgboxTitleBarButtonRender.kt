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

import dev.kobalt.msgboxgen.jvm.renderable.MsgboxRenderable
import java.awt.Color
import java.awt.Graphics2D

class MsgboxTitleBarButtonRender(
    val x: Int,
    val y: Int,
    val backgroundColor: Color,
    val textColor: Color
) : MsgboxRenderable {

    val w = 16
    val h = 14

    val x1 = x
    val x2 = x + w
    val y1 = y
    val y2 = y + h

    val borderOuterLightColor = Color(255, 255, 255)
    val borderOuterDarkColor = Color(0, 0, 0)
    val borderInnerLightColor = Color(223, 223, 223)
    val borderInnerDarkColor = Color(128, 128, 128)

    val oW = w - 1
    val oH = h - 1
    val oX1 = x
    val oY1 = y
    val oX2 = oX1 + oW
    val oY2 = oY1 + oH

    val iW = w - 3
    val iH = h - 3
    val iX1 = x + 1
    val iY1 = y + 1
    val iX2 = iX1 + iW
    val iY2 = iY1 + iH

    override fun render(graphics: Graphics2D) {
        graphics.apply {
            val oldColor = color
            color = backgroundColor
            fillRect(x, y, w, h)
            color = borderOuterLightColor
            drawLine(oX1, oY1, oX1, oY2)
            drawLine(oX1, oY1, oX2, oY1)
            color = borderOuterDarkColor
            drawLine(oX2, oY1, oX2, oY2)
            drawLine(oX1, oY2, oX2, oY2)
            color = borderInnerLightColor
            drawLine(iX1, iY1, iX1, iY2)
            drawLine(iX1, iY1, iX2, iY1)
            color = borderInnerDarkColor
            drawLine(iX2, iY1, iX2, iY2)
            drawLine(iX1, iY2, iX2, iY2)

            color = textColor
            repeat(2) {
                drawLine(iX1 + 3 + it, iY1 + 2, iX2 - 4 + it, iY2 - 3)
                drawLine(iX2 - 4 + it, iY1 + 2, iX1 + 3 + it, iY2 - 3)
            }

            color = oldColor
        }
    }

}