package no.iktdev.setting.ui.components

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StyleableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import no.iktdev.setting.R
import no.iktdev.setting.access.SettingDefined
import no.iktdev.setting.model.ComponentData
import no.iktdev.setting.model.SettingComponentDescriptorBase
import no.iktdev.setting.model.ThemeItem


/*abstract class SettingViewBase
    @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) */

abstract class SettingViewBase(
    context: Context,
    private val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    abstract val binding: ViewBinding?
    var settingDefined: SettingDefined? = null
        set(value) {
            field = value ?: return
            onSettingAssigned(value)
        }

    protected val defaultColor: Int = ContextCompat.getColor(context, R.color.colorAccent)
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }


    protected fun applyTextAttr(attr: TypedArray, text: TextView, @StyleableRes id: Int) {
        if (attr.hasValue(id)) {
            text.text = attr.getString(id)
        }
    }

    protected fun applyTextColorAttr(attr: TypedArray, text: TextView, @StyleableRes id: Int) {
        if (attr.hasValue(id)) {
            val color: Int = attr.getColor(id, 4054148)
            text.setTextColor(color)
        }
    }

    protected fun applySrcAttr(attr: TypedArray, image: ImageView, @StyleableRes id: Int) {
        if (attr.hasValue(id)) {
            image.setImageResource(attr.getResourceId(id, 0))
        }
    }

    protected fun applySrcTintAttr(attr: TypedArray, image: ImageView, @StyleableRes id: Int) {
        if (attr.hasValue(id)) {
            val color: Int = attr.getColor(id, 4054148)
            image.setColorFilter(color)
        }
    }

    protected fun getBooleanAttrValue(attr: TypedArray, @StyleableRes id: Int): Boolean {
        return if (attr.hasValue(id)) attr.getBoolean(id, false) else false
    }

    protected fun getIntAttrValue(attr: TypedArray, @StyleableRes id: Int, default: Int = 0): Int {
        return if (attr.hasValue(id)) attr.getInt(id, default) else default
    }

    protected fun applyBackgroundAttr(attr: TypedArray, view: View, @StyleableRes id: Int) {
        if (attr.hasValue(id)) {
            val drawable: Drawable? = attr.getDrawable(id)
            if (drawable == null) {
                // TODO: Inform that we have a problem
                return
            }
            view.background = drawable
        }
    }

    abstract fun onTypedArray(a: TypedArray)
    abstract fun applyAttrs(attrs: AttributeSet)
    abstract fun setTheme(theme: ThemeItem)
    abstract fun setDescriptorValues(base: SettingComponentDescriptorBase)

    abstract fun onSettingAssigned(settingDefined: SettingDefined)
    abstract fun setPayload(payload: ComponentData)
}