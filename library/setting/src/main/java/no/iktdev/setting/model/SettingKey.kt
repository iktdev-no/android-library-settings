package no.iktdev.setting.model


abstract class SettingKey(val value: String) {
    companion object {
        val group: String = ""
    }
}

abstract class Setting {
    companion object {
        val group: String = ""
    }
}

/*
interface SettingKeyCompanion {
    fun <T : SettingKey> get(context: Context) = SettingInstance<T>(context, SettingKey.group)
}*/
