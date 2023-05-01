package no.iktdev.demoapplication

import android.content.Context
import no.iktdev.setting.access.SettingDefined
import no.iktdev.setting.access.SettingInstance
import no.iktdev.setting.model.Setting
import no.iktdev.setting.model.SettingKey

class Settings {
    sealed class Wireless(value: String) : SettingKey(value) {
        companion object {
            val group: String = "Wireless"
            fun get(context: Context) = SettingInstance<Wireless>(context, group)
        }
        class Offline : Wireless("Offline mode")
    }

    class Test: Setting() {
        val testVal = "Potato"
    }

    val testTing = SettingDefined(null, Test().testVal)

}