package org.wordpress.aztec.spans

import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.style.ImageSpan
import android.view.View
import android.widget.Toast

class AztecCommentSpan(val comment: AztecCommentSpan.Comment, val width: Int, drawable: Drawable) : ImageSpan(drawable) {
    companion object {
        private val HTML_MORE: String = "more"
        private val HTML_PAGE: String = "nextpage"
    }

    enum class Comment constructor(val html: String) {
        MORE(HTML_MORE),
        PAGE(HTML_PAGE)
    }

    override fun getSize(paint: Paint?, text: CharSequence?, start: Int, end: Int, metrics: Paint.FontMetricsInt?): Int {
        val drawable = drawable
        val bounds = getBounds(drawable)

        if (metrics != null) {
            metrics.ascent = -bounds.bottom
            metrics.descent = 0

            metrics.top = metrics.ascent
            metrics.bottom = 0
        }

        return bounds.right
    }

    private fun getBounds(drawable: Drawable): Rect {
        if (drawable.intrinsicWidth === 0) {
            return Rect(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        }

        val height = drawable.intrinsicHeight * width / drawable.intrinsicWidth
        drawable.setBounds(0, 0, width, height)

        return drawable.bounds
    }

    fun onClick(view: View) {
        Toast.makeText(view.context, comment.html.toString(), Toast.LENGTH_SHORT).show()
    }
}
