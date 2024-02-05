package dev.pegasus.phototemplates.helpers.extensions

import android.view.View
import android.view.ViewGroup

fun View.gone() = run { visibility = View.GONE }

fun View.visible() = run { visibility = View.VISIBLE }

fun View.invisible() = run { visibility = View.INVISIBLE }

infix fun View.visibleIf(condition: Boolean) =
    run { visibility = if (condition) View.VISIBLE else View.GONE }

infix fun View.goneIf(condition: Boolean) =
    run { visibility = if (condition) View.GONE else View.VISIBLE }

infix fun View.invisibleIf(condition: Boolean) =
    run { visibility = if (condition) View.INVISIBLE else View.VISIBLE }


/**
 *  -> e.g. frameLayout.addCleanView(adView)
 * @param  view: Here AdView is Child
 */

fun ViewGroup.addCleanView(view: View?) {
    (view?.parent as? ViewGroup)?.removeView(view)
    this.removeAllViews()
    this.addView(view)
}

fun ViewGroup.addCleanView(view: View?, layoutParams: ViewGroup.LayoutParams?) {
    (view?.parent as? ViewGroup)?.removeView(view)
    this.removeAllViews()
    this.addView(view, layoutParams)
}