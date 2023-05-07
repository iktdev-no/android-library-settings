package no.iktdev.demoapplication

import android.content.Context
import no.iktdev.setting.access.GroupBasedSetting

class Settings {

    sealed class PreferenceTest(key: String) : GroupBasedSetting("Preference", key) {
        class BeFancy : PreferenceTest("Im Very fancy")
    }

    sealed class SwitchTest(key: String): GroupBasedSetting("Switchy", key) {

        class ImFancyNoOne : SwitchTest("FancyNoOne")
        class ImFancyNoTwo : SwitchTest("FancyNoTwo")
        class ImFancyNoThree : SwitchTest("FancyNoThree")

        class ToggleMe: SwitchTest("ImToggled")
        class ReactiveToggle: SwitchTest("ReactiveToggle")

    }

    sealed class ValueSelector(key: String): GroupBasedSetting("Valuy", key) {
        class PercentageIsFun : ValueSelector("PercentalgeIsFun")
        class SelectSomething : ValueSelector("SelectSomething")
    }


}