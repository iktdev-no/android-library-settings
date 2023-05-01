package no.iktdev.setting.model

import java.io.Serializable

/**
 * @param value Should only be value of primitive type (int, string, boolean, float etc..)
 * @param payload Value that can be assigned if you want to inform host activity of the item selected
 */
class DropdownItem(val displayValue: String, override var value: Any?, val payload: Serializable? = null) : ComponentData() {
}