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
import java.awt.Graphics2D
import java.awt.image.BufferedImage

class MsgboxIconRender(
    val x: Int,
    val y: Int,
    val icon: BufferedImage
) : MsgboxRenderable {

    val w = icon.width
    val h = icon.height

    val x1 = x
    val x2 = x + w
    val y1 = y
    val y2 = y + h

    override fun render(graphics: Graphics2D) {
        graphics.apply {
            drawImage(icon, x, y, icon.width, icon.height, null)
        }
    }

}