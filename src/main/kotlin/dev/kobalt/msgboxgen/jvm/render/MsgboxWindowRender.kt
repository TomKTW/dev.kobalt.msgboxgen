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

import dev.kobalt.msgboxgen.jvm.extension.drawRenderable
import dev.kobalt.msgboxgen.jvm.renderable.MsgboxRenderable
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import kotlin.math.max

class MsgboxWindowRender(
    val x: Int,
    val y: Int,
    val w: Int,
    val title: String,
    val message: String,
    val icon: BufferedImage,
    val font: Font,
    val buttons: Array<String>,
    val titleBarStartColor: Color,
    val titleBarEndColor: Color,
    val titleBarTextColor: Color,
    val windowBackgroundColor: Color,
    val messageTextColor: Color,
    val buttonTextColor: Color,
    val buttonBackgroundColor: Color
) : MsgboxRenderable {

    val x1 = x
    val x2 = x + w
    val y1 = y

    val ox1 = x1
    val oy1 = y1
    val ox2 = x2 - 1
    val ow = ox2 - ox1

    val ix1 = x1 + 1
    val iy1 = y1 + 1
    val ix2 = x2 - 2
    val iw = ix2 - ix1

    val borderOuterLightColor = Color(223, 223, 223)
    val borderOuterDarkColor = Color(0, 0, 0)
    val borderInnerLightColor = Color(255, 255, 255)
    val borderInnerDarkColor = Color(128, 128, 128)

    val titleBar = MsgboxTitleBarRender(
        x = ix1 + 2,
        y = iy1 + 2,
        w = iw - 3,
        h = 18,
        text = title,
        font = font,
        startColor = titleBarStartColor,
        endColor = titleBarEndColor,
        textColor = titleBarTextColor
    )

    val titleBarButton = MsgboxTitleBarButtonRender(
        x = titleBar.x2 - 18,
        y = titleBar.y1 + 2,
        backgroundColor = buttonBackgroundColor,
        textColor = buttonTextColor
    )

    val image = MsgboxIconRender(
        x = ix1 + 13,
        y = titleBar.y + titleBar.h + 12,
        icon = icon
    )

    val text = MsgboxTextRender(
        x = image.x + image.w + 16,
        y = titleBar.y + titleBar.h + 14,
        w = w - 96,
        text = message,
        font = font,
        textColor = messageTextColor
    )

    val buttonY = max(80, text.y + text.h) + 2
    val width = 78

    val buttonsRender = buttons.mapIndexed { index, text ->
        MsgboxButtonRender(
            x = when (buttons.size) {
                1 -> x2 / 2 - width / 2
                2 -> when (index) {
                    0 -> x2 / 2 - width - 24
                    1 -> x2 / 2 + 24
                    else -> x2 + 1
                }
                3 -> when (index) {
                    0 -> (x2 / 2) - (width / 2) - width - 6
                    1 -> (x2 / 2) - (width / 2)
                    2 -> (x2 / 2) - (width / 2) + width + 6
                    else -> x2 + 1
                }
                else -> x2 + 1
            },
            y = buttonY,
            w = width,
            text = text,
            textColor = buttonTextColor,
            backgroundColor = buttonBackgroundColor,
            font = font
        )
    }

    val h = (buttonsRender.firstOrNull()?.y2 ?: (text.y2 + 4)) + 14

    val y2 = y + h
    val oy2 = y2 - 1
    val oh = oy2 - oy1
    val iy2 = y2 - 2
    val ih = iy2 - iy1

    override fun render(graphics: Graphics2D) {
        graphics.apply {
            val oldColor = color
            color = windowBackgroundColor
            fillRect(x, y, w, h)
            color = borderOuterLightColor
            drawLine(ox1, oy1, ox1, oy2)
            drawLine(ox1, oy1, ox2, oy1)
            color = borderOuterDarkColor
            drawLine(ox2, oy1, ox2, oy2)
            drawLine(ox1, oy2, ox2, oy2)
            color = borderInnerLightColor
            drawLine(ix1, iy1, ix1, iy2)
            drawLine(ix1, iy1, ix2, iy1)
            color = borderInnerDarkColor
            drawLine(ix2, iy1, ix2, iy2)
            drawLine(ix1, iy2, ix2, iy2)
            color = oldColor
            drawRenderable(titleBar)
            drawRenderable(titleBarButton)
            drawRenderable(image)
            drawRenderable(text)
            buttonsRender.forEach { drawRenderable(it) }
        }
    }

}