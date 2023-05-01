package no.iktdev.setting.model

import androidx.annotation.StyleRes
import no.iktdev.setting.factory.ThemeType

data class ThemeItem(val themeType: ThemeType = ThemeType.NORMAL, @StyleRes val theme: Int)