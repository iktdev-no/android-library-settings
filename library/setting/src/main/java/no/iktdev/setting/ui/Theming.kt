package no.iktdev.setting.ui

import androidx.annotation.StyleRes

enum class ThemeType {
    START,
    END,
    SINGLE,
    NORMAL
}
data class Theming(val themeType: ThemeType = ThemeType.NORMAL, @StyleRes val theme: Int)