package no.iktdev.setting.ui.components

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import no.iktdev.setting.R
import no.iktdev.setting.access.ReactiveSetting
import no.iktdev.setting.access.SettingAccess
import no.iktdev.setting.databinding.PopoutAdapterSingleTextviewBinding
import no.iktdev.setting.databinding.SettingViewPopoutSelectBinding
import no.iktdev.setting.model.ComponentData
import no.iktdev.setting.model.SettingComponentDescriptor
import no.iktdev.setting.model.SettingComponentDescriptorBase
import no.iktdev.setting.model.*
import no.iktdev.setting.ui.Theming
import java.io.Serializable
import kotlin.math.roundToInt

class SettingViewPopoutSelect(context: Context, attrs: AttributeSet? = null) :
    SettingViewBase(context, attrs) {


    override val binding: SettingViewPopoutSelectBinding = SettingViewPopoutSelectBinding.inflate(
        LayoutInflater.from(context), this, true)
    var selectedTextColor: Int = ContextCompat.getColor(context, R.color.colorAccent)
    override fun onTypedArray(a: TypedArray) {
        applyTextAttr(a, binding.title, R.styleable.SettingViewPopoutSelect_text)
        applyTextColorAttr(a, binding.title, R.styleable.SettingViewPopoutSelect_textColor)
        applyTextColorAttr(a, binding.subText, R.styleable.SettingViewPopoutSelect_subTextColor)
        applyBackgroundAttr(a, binding.root, R.styleable.SettingViewPopoutSelect_background)
        applyBackgroundAttr(a, binding.dropdown, R.styleable.SettingViewPopoutSelect_popupBackground)


        applySrcAttr(a, binding.icon, R.styleable.SettingViewPopoutSelect_src)
        applySrcTintAttr(a, binding.icon, R.styleable.SettingViewPopoutSelect_srcTint)
        binding.dropdown.setPopupBackgroundResource(a.getResourceId(R.styleable.SettingViewPopoutSelect_background, selectedTextColor))
        //binding.dropdown
    }

    override fun applyAttrs(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SettingViewPopoutSelect)
        onTypedArray(a)
        a.recycle()
    }


    private var adapter: PoputSelectAdapter? = null

    override fun setTheme(theme: Theming) {
        val attr = ContextThemeWrapper(context, theme.theme).theme.obtainStyledAttributes(R.styleable.SettingViewPopoutSelect)
        onTypedArray(attr)
        attr.recycle()
    }

    override fun setDescriptorValues(base: SettingComponentDescriptorBase) {
        (if (base is SettingComponentDescriptor) base else null)?.let { desc ->
            binding.title.text = desc.title
            desc.icon?.let { icon ->
                binding.icon.setImageResource(icon)
            }
        }

    }

    override fun onSettingAssigned(setting: SettingAccess) {
        val value: Any? = setting.getSettings(context)?.get(setting.key)
        val item = adapter?.items?.find { it.value == value }

        if (item != null) {
            val index = adapter?.items?.indexOf(item) ?: 0
            binding.dropdown.setSelection(index, false)
            item.let {
                binding.subText.text = it.displayValue
            }
        } else {
            binding.dropdown.setSelection(0)
        }
        binding.dropdown.onItemSelectedListener = selectionChange

    }

    override fun setPayload(payload: ComponentData) {
        if (payload.value !is List<*> || !(payload.value as List<*>).all { it is DropdownItem }) {
            throw SettingComponentInvalidValueException("Payload does not contain " + DropdownItem::class.java.simpleName)
        }
        binding.dropdown.onItemSelectedListener = null // Resetting in order to prevent notification on drawing
        if (adapter == null) {
            @Suppress("UNCHECKED_CAST")
            adapter = PoputSelectAdapter(context, payload.value as List<DropdownItem>)
            binding.dropdown.adapter = adapter
        } else {
            @Suppress("UNCHECKED_CAST")
            adapter?.items = payload.value as List<DropdownItem>
            adapter?.notifyDataSetChanged()
        }
    }

    val selectionChange = object: AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val item = adapter?.items?.get(position)
            setting?.let { setting ->
                val payload = if (item?.payload != null) item.payload else if (item?.value is Serializable) item.value else null
                if (setting is ReactiveSetting && payload != null && payload is Serializable) {
                    (setting as ReactiveSetting).setPayload(payload)
                }
                item?.let {
                    binding.subText.text = it.displayValue
                }
                when (item?.value) {
                    is String -> setting.setString(context, item.value as String)
                    is Int -> setting.setInt(context, item.value as Int)
                    is Float -> setting.setFloat(context, item.value as Float)
                    is Boolean -> setting.setBoolean(context, item.value as Boolean)
                    else -> Log.e(this::class.simpleName, "Unsupported item provided!")
                }
            }
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}

    }

    class PoputSelectAdapter(val context: Context, var items: List<DropdownItem>): BaseAdapter() {
        override fun getCount(): Int {
            return items.size
        }

        override fun getItem(p0: Int): Any {
            return items[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(position: Int, view: View?, parent: ViewGroup): View {
            var holder = view
            var binding: PopoutAdapterSingleTextviewBinding?
            if (holder == null) {
                binding = PopoutAdapterSingleTextviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                holder = binding.root
            } else {
                binding = holder.tag as PopoutAdapterSingleTextviewBinding
            }
            holder.tag = binding
            holder.setPadding(fromDP(context, 12),fromDP(context, 12),fromDP(context, 12),fromDP(context, 12))
            
            val item = items[position]
            binding.singleTextView.text = item.displayValue
            return holder
        }
        fun fromDP(context: Context, dp: Int): Int {
            val scale = context.resources.displayMetrics.density
            return (dp * scale + 0.5f).roundToInt()
        }
    }



    init {
        if (attrs != null) this.applyAttrs(attrs)
        binding.root.setOnClickListener { binding.dropdown.performClick() }
    }

}