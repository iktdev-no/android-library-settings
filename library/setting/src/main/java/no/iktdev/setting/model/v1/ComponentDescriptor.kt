package no.iktdev.setting.model.v1

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import no.iktdev.setting.access.SettingReference
import no.iktdev.setting.model.SettingComponentType

@Deprecated("For now.. Did more harm than good")
class ComponentDescriptor {

    abstract class Base(
        @Transient @DrawableRes open val icon: Int?,
        @Transient open val group: String,
        @Transient open val title: String,
        @Transient open val description: String,
        @Transient open val payload: PayloadDescriptor.PayloadBase? = null,
        @Transient open val type: SettingComponentType = SettingComponentType.CLICKABLE
    )

    data class SimpleDescriptor(
        @DrawableRes override val icon: Int?,
        override val group: String,
        override val title: String,
        override val description: String = "",
        override val payload: PayloadDescriptor.PayloadBase?,
        override val type: SettingComponentType,
    ) : Base(icon = icon, group = group, payload = payload, type = type, title = title, description = description) {



        companion object {

            fun builder(context: Context? = null): Builder {
                return Builder(context)
            }
        }


        class Builder(val context: Context? = null) {
            private var icon: Int? = null
            private lateinit var group: String
            private lateinit var title: String
            private var description: String = ""
            private var payload: PayloadDescriptor.PayloadBase? = null
            private var type: SettingComponentType = SettingComponentType.CLICKABLE

            fun icon(icon: Int?): Builder {
                this.icon = icon
                return this
            }

            fun group(group: String): Builder {
                this.group = group
                return this
            }
            fun group(@StringRes group: Int): Builder {
                this.group = context?.getString(group) ?: throw RuntimeException("Context is not declared! Please call builder(context)")
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

            fun description(description: String): Builder {
                this.description = description
                return this
            }
            fun description(@StringRes group: Int): Builder {
                this.description = context?.getString(group) ?: throw RuntimeException("Context is not declared! Please call builder(context)")
                return this
            }

            fun payload(payload: PayloadDescriptor.PayloadBase?): Builder {
                this.payload = payload
                return this
            }

            fun type(type: SettingComponentType): Builder {
                this.type = type
                return this
            }


            fun build(): SimpleDescriptor {
                return SimpleDescriptor(
                    icon = icon,
                    group = group,
                    title = title,
                    description = description,
                    payload = payload,
                    type = type,
                )
            }
        }

    }

    data class SettingDescriptor(
        @DrawableRes override val icon: Int?,
        override val group: String,
        override val title: String,
        override val description: String = "",
        override val payload: PayloadDescriptor.PayloadBase?,
        override val type: SettingComponentType,
        val setting: SettingReference? = null
    ) : Base(icon = icon, group = group, payload = payload, type = type, title = title, description = description) {

        class Builder(val context: Context? = null) {
            private var icon: Int? = null
            private lateinit var group: String
            private lateinit var title: String
            private var description: String = ""
            private var payload: PayloadDescriptor.PayloadBase? = null
            private var type: SettingComponentType = SettingComponentType.CLICKABLE
            private var setting: SettingReference? = null

            fun icon(icon: Int?): Builder {
                this.icon = icon
                return this
            }

            fun group(group: String): Builder {
                this.group = group
                return this
            }
            fun group(@StringRes group: Int): Builder {
                this.group = context?.getString(group) ?: throw RuntimeException("Context is not declared! Please call builder(context)")
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

            fun description(description: String): Builder {
                this.description = description
                return this
            }
            fun description(@StringRes group: Int): Builder {
                this.description = context?.getString(group) ?: throw RuntimeException("Context is not declared! Please call builder(context)")
                return this
            }

            fun payload(payload: PayloadDescriptor.PayloadBase?): Builder {
                this.payload = payload
                return this
            }

            fun type(type: SettingComponentType): Builder {
                this.type = type
                return this
            }

            fun setting(setting: SettingReference): Builder {
                this.setting = setting
                return this
            }

            fun build(): SettingDescriptor {
                return SettingDescriptor(
                    icon = icon,
                    group = group,
                    title = title,
                    description = description,
                    payload = payload,
                    type = type,
                    setting = setting
                )
            }
        }

        companion object {
            fun builder(context: Context? = null): Builder {
                return Builder(context)
            }
        }
    }


}