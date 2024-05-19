package no.iktdev.setting.ui.components

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import no.iktdev.setting.R
import no.iktdev.setting.databinding.SettingViewClickableBinding
import no.iktdev.setting.model.ActionableComponentData
import no.iktdev.setting.model.ComponentData
import no.iktdev.setting.model.SettingComponentDescriptor
import no.iktdev.setting.model.SettingComponentDescriptorBase
import no.iktdev.setting.ui.Theming


class SettingViewClickable(context: Context, attrs: AttributeSet? = null) : SettingViewBase(context, attrs) {
    override val binding: SettingViewClickableBinding = SettingViewClickableBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        if (attrs != null) this.applyAttrs(attrs)
    }


    override fun applyAttrs(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SettingViewClickable)
        onTypedArray(a)
        a.recycle()
    }


    override fun setTheme(theme: Theming) {
        val attr = context.obtainStyledAttributes(theme.theme, R.styleable.SettingViewClickable)
        onTypedArray(attr)
        attr.recycle()
    }

    override fun setDescriptorValues(base: SettingComponentDescriptorBase) {
        (if (base is SettingComponentDescriptor) base else null)?.let { base ->
            base.icon?.let { icon ->
                binding.icon.setImageResource(icon)
            }
            binding.text.text = base.title
            if (!base.description.isNullOrBlank()) {
                binding.subText.text = base.description
                binding.subText.visibility = VISIBLE
            } else binding.subText.visibility = GONE
        }
    }



    override fun onTypedArray(a: TypedArray) {
        applyTextAttr(a, binding.text, R.styleable.SettingViewClickable_text)
        applyTextAttr(a, binding.subText, R.styleable.SettingViewClickable_subtext)

        applyTextColorAttr(a, binding.text, R.styleable.SettingViewClickable_textColor)
        applyTextColorAttr(a, binding.subText, R.styleable.SettingViewClickable_subTextColor)

        if (a.hasValue(R.styleable.SettingViewClickable_subtext) && !a.getString(R.styleable.SettingViewClickable_subtext).isNullOrEmpty()) {
            binding.subText.visibility = VISIBLE
        }

        applySrcAttr(a, binding.icon, R.styleable.SettingViewClickable_src)
        applySrcTintAttr(a, binding.icon, R.styleable.SettingViewClickable_srcTint)

        applyBackgroundAttr(a, binding.root, R.styleable.SettingViewClickable_background)
    }

    override fun setPayload(payload: ComponentData) {
        if (payload is ActionableComponentData) {
            binding.root.setOnClickListener {
                val i = Intent(context, payload.target)
                val bundle = payload.value

                if (bundle != null) {
                    i.putExtras(bundle)
                }
                context.startActivity(i)

            }
        }
    }

    @Suppress("unused")
    fun setSrc(@DrawableRes drawable: Int) {
        binding.icon.setImageResource(drawable)
    }

    @Suppress("unused")
    fun setSrc(bitmap: Bitmap) {
        binding.icon.setImageBitmap(bitmap)
    }

    @Suppress("unused")
    fun setSrc(drawable: Drawable) {
        binding.icon.setImageDrawable(drawable)
    }


    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        binding.root.setOnClickListener(l)
    }

    @Suppress("unused")
    fun setText(text: String) {
        binding.text.text = text
    }

    @Suppress("unused")
    fun setSubText(text: String) {
        if (text.isNotEmpty()) {
            binding.subText.text = text
            binding.subText.visibility = VISIBLE
        } else binding.subText.visibility = GONE
    }



}