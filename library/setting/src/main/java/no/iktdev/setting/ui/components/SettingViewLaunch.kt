package no.iktdev.setting.ui.components

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import no.iktdev.setting.R
import no.iktdev.setting.access.SettingDefined
import no.iktdev.setting.databinding.SettingViewLaunchBinding
import no.iktdev.setting.model.*

class SettingViewLaunch(context: Context, attrs: AttributeSet? = null) :
    SettingViewBase(context, attrs) {

    override val binding: SettingViewLaunchBinding = SettingViewLaunchBinding.inflate(LayoutInflater.from(context), this, true)
    override fun onTypedArray(a: TypedArray) {
        applyTextAttr(a, binding.text, R.styleable.SettingViewLaunch_text)
        applyTextAttr(a, binding.subText, R.styleable.SettingViewLaunch_subtext)

        applyTextColorAttr(a, binding.text, R.styleable.SettingViewLaunch_textColor)
        applyTextColorAttr(a, binding.subText, R.styleable.SettingViewLaunch_subTextColor)

        applySrcAttr(a, binding.icon, R.styleable.SettingViewLaunch_src)
        applySrcTintAttr(a, binding.icon, R.styleable.SettingViewLaunch_srcTint)

        applySrcTintAttr(a, binding.launchIcon, R.styleable.SettingViewLaunch_openSrcTint)

        applyBackgroundAttr(a, binding.root, R.styleable.SettingViewClickable_background)
    }

    override fun applyAttrs(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SettingViewLaunch)
        onTypedArray(a)
        a.recycle()
    }


    override fun setTheme(theme: ThemeItem) {
        val attr = context.obtainStyledAttributes(theme.theme, R.styleable.SettingViewLaunch)
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

    override fun onSettingAssigned(settingDefined: SettingDefined) {

    }

    override fun setPayload(payload: ComponentData) {
        if (payload is LaunchableComponentData) {
            setOnClickListener(Uri.parse(payload.uri))
        }
    }

    fun setOnClickListener(uri: Uri) {
        binding.root.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        }
    }

    init {
        if (attrs != null) this.applyAttrs(attrs)
    }
}