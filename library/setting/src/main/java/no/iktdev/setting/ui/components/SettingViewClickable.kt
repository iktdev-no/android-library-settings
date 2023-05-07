package no.iktdev.setting.ui.components

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import no.iktdev.setting.Assist
import no.iktdev.setting.R
import no.iktdev.setting.databinding.SettingViewClickableBinding
import no.iktdev.setting.model.*


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


    override fun setTheme(theme: ThemeItem) {
        val attr = context.obtainStyledAttributes(theme.theme, R.styleable.SettingViewClickable)
        onTypedArray(attr)
        attr.recycle()
    }

    override fun setDescriptorValues(base: SettingComponentDescriptorBase) {
        if (base !is SettingComponentDescriptor)
            return
        if (base.icon != null)
            binding.icon.setImageResource(base.icon)
        binding.text.text = base.title
        if (base.description != null) {
            binding.subText.text = base.description
            binding.subText.visibility = VISIBLE
        } else binding.subText.visibility = GONE
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

                Assist().isSerializable(payload.value)

                if (bundle != null) {
                    i.putExtras(bundle)
                }
                context.startActivity(i)

            }
        }
    }


    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        binding.root.setOnClickListener(l)
    }

    fun setText(text: String) {
        binding.text.text = text;
    }

    fun setSubText(text: String) {
        if (text.isNotEmpty()) {
            binding.subText.text = text
            binding.subText.visibility = VISIBLE
        } else binding.subText.visibility = GONE
    }


}