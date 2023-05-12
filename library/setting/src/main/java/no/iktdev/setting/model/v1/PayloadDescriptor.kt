package no.iktdev.setting.model.v1

import android.content.Context
import androidx.annotation.StringRes
import no.iktdev.setting.ui.activities.SettingsActivity.Companion.componentPassKey
import java.io.Serializable

@Deprecated("For now.. Did more harm than good")
class PayloadDescriptor {
    abstract class PayloadBase(): Serializable {
        @Transient open val value: Any? = null
    }

    data class LaunchPayload(
        val uri: String?,
        override var value: Serializable?
    ) : PayloadBase() {
        class Builder {
            private var uri: String? = null
            private var value: Serializable? = null

            fun uri(uri: String?): Builder {
                this.uri = uri
                return this
            }

            fun value(value: Serializable?): Builder {
                this.value = value
                return this
            }

            fun build(): LaunchPayload {
                return LaunchPayload(uri = uri, value = value)
            }
        }

        companion object {
            fun builder(): Builder {
                return Builder()
            }
        }
    }

    data class ActionPayload(
        val start: Class<*>? = null,
        val title: String? = null,
        val key: String = componentPassKey,
        override var value: Any?
    ) : PayloadBase() {



        companion object  {
            fun builder(context: Context? = null): Builder {
                return Builder(context)
            }
        }



        class Builder(val context: Context? = null) {
            private var title: String? = null
            private var start: Class<*>? = null
            private var key: String = componentPassKey
            private var value: Any? = null

            fun start(start: Class<*>?): Builder {
                this.start = start
                return this
            }

            fun key(key: String): Builder {
                this.key = key
                return this
            }

            fun value(value: Any?): Builder {
                this.value = value
                return this
            }

            fun title(title: String): Builder {
                this.title = title
                return this
            }

            fun title(@StringRes title: Int): Builder {
                this.title = context?.getString(title) ?: throw RuntimeException("Context is not declared! Please call builder(context)")
                return this
            }

            fun build(): ActionPayload {
                return ActionPayload(title = title, start = start, key = key, value = value)
            }
        }

    }

    data class Selectable(
        val displayValue: String,
        override var value: Serializable?,
        val payload: Serializable? = null
    ) : PayloadBase()
}