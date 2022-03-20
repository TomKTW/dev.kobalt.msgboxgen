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

package dev.kobalt.msgboxgen.jvm.generator

import dev.kobalt.msgboxgen.jvm.extension.drawRenderable
import dev.kobalt.msgboxgen.jvm.render.MsgboxWindowRender
import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage
import java.io.OutputStream
import javax.imageio.ImageIO
import javax.swing.JLabel
import kotlin.math.min

class MsgboxGenerator {

    companion object {
        val defaultIcon = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
        val defaultTitleBarStartColor = Color(0, 0, 128)
        val defaultTitleBarEndColor = Color(16, 132, 208)
        val defaultTitleBarTextColor = Color(255, 255, 255)
        val defaultWindowBackgroundColor = Color(192, 192, 192)
        val defaultMessageTextColor = Color(0, 0, 0)
        val defaultButtonTextColor = Color(0, 0, 0)
        val defaultButtonBackgroundColor = Color(192, 192, 192)
    }

    fun generate(
        outputStream: OutputStream,
        title: String? = null,
        message: String? = null,
        buttons: Array<String>? = null,
        icon: BufferedImage? = null,
        font: Font? = null,
        width: Int? = null,
        titleBarStartColor: Color? = null,
        titleBarEndColor: Color? = null,
        titleBarTextColor: Color? = null,
        windowBackgroundColor: Color? = null,
        messageTextColor: Color? = null,
        buttonTextColor: Color? = null,
        buttonBackgroundColor: Color? = null
    ) {

        val window = MsgboxWindowRender(
            x = 0,
            y = 0,
            w = min(720, width ?: 200),
            title = title?.take(512).orEmpty(),
            message = message?.take(512).orEmpty(),
            icon = icon ?: defaultIcon,
            buttons = buttons ?: emptyArray(),
            font = (font ?: JLabel().font).deriveFont(Font.PLAIN, 11f),
            titleBarStartColor = titleBarStartColor ?: defaultTitleBarStartColor,
            titleBarEndColor = titleBarEndColor ?: defaultTitleBarEndColor,
            titleBarTextColor = titleBarTextColor ?: defaultTitleBarTextColor,
            windowBackgroundColor = windowBackgroundColor ?: defaultWindowBackgroundColor,
            messageTextColor = messageTextColor ?: defaultMessageTextColor,
            buttonTextColor = buttonTextColor ?: defaultButtonTextColor,
            buttonBackgroundColor = buttonBackgroundColor ?: defaultButtonBackgroundColor
        )

        val image = BufferedImage(
            window.w,
            window.h,
            BufferedImage.TYPE_INT_ARGB
        ).apply {
            createGraphics().apply {
                drawRenderable(window)
                dispose()
            }
        }

        ImageIO.write(image, "PNG", outputStream)
    }

}