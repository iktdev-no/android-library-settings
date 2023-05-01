package no.iktdev.setting.model

import java.io.Serializable

enum class SettingComponentType : Serializable {
    CLICKABLE, // <- Click + Default
    SWITCH,
    LAUNCH, // <- External
    DROPDOWN,
    POPOUT_SELECT,
    SLIDER // <- Seekbar
}