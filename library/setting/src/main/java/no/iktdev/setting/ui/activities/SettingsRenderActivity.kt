package no.iktdev.setting.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlin.math.abs

abstract class SettingsRenderActivity : SettingsActivity() {
    companion object {
        val titlePassKey = "RenderTitle"
    }

    protected val titleChange: MutableLiveData<String> = MutableLiveData()

    protected var toolbar: Toolbar? = null
        set(value) {
            field = value
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            toolbar?.title = titleChange.value
        }

    private var collapsing: CollapsingToolbarLayout? = null

    protected fun setCollapsingToolbar(bar: AppBarLayout, collapsing: CollapsingToolbarLayout, content: View, hideCollapsingTitle: Boolean = false) {
        this.collapsing = collapsing
        if (!hideCollapsingTitle)
            collapsing.title = titleChange.value
        else
            collapsing.title = " "
        val listener = AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val cutter = -appBarLayout.totalScrollRange + abs(bar.totalScrollRange / 4)
            Log.d(this.javaClass.simpleName, "Total: ${appBarLayout.totalScrollRange} Current: $verticalOffset Cutter: $cutter")
            when {
                verticalOffset <= cutter -> {
                    content.visibility = View.VISIBLE
                    if (!hideCollapsingTitle)
                        collapsing.title = title
                }
                else -> {
                    collapsing.title = " "
                    content.visibility = View.INVISIBLE
                }

            }
        }
        bar.addOnOffsetChangedListener(listener)
    }


    @SuppressLint("MissingSuperCall") //Its faulty intellisense
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.hasExtra(titlePassKey))
            titleChange.postValue(intent.getStringExtra(titlePassKey))
        else if (!title.isNullOrEmpty())
            titleChange.postValue(title.toString())
        else
            titleChange.postValue("🥔")
    }

    override fun onDestroy() {
        super.onDestroy()
        titleChange.removeObservers(this)
    }
}