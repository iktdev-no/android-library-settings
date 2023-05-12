package no.iktdev.setting.ui.components

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import no.iktdev.setting.R
import no.iktdev.setting.databinding.SettingViewLaunchBinding
import no.iktdev.setting.model.ComponentData
import no.iktdev.setting.model.LaunchableComponentData
import no.iktdev.setting.model.SettingComponentDescriptor
import no.iktdev.setting.model.SettingComponentDescriptorBase
import no.iktdev.setting.ui.Theming

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


    override fun setTheme(theme: Theming) {
        val attr = context.obtainStyledAttributes(theme.theme, R.styleable.SettingViewLaunch)
        onTypedArray(attr)
        attr.recycle()
    }

    override fun setDescriptorValues(base: SettingComponentDescriptorBase) {
        (if (base is SettingComponentDescriptor) base else null)?.let { desc ->
            desc.icon?.let { icon ->
                binding.icon.setImageResource(icon)
            }
            binding.text.text = desc.title
            if (desc.description != null) {
                binding.subText.text = desc.description
                binding.subText.visibility = VISIBLE
            } else binding.subText.visibility = GONE
        }
    }

    override fun setPayload(payload: ComponentData) {
        (if (payload is LaunchableComponentData) payload else null)?.let { load ->
            setOnClickListener(Uri.parse(load.uri))
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