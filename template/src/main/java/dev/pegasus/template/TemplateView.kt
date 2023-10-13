package dev.pegasus.template

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.toRect
import dev.pegasus.template.utils.HelperUtils.TAG
import dev.pegasus.template.utils.ImageUtils

class TemplateView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val imageUtils by lazy { ImageUtils(context) }

    /**
     * @property backgroundBitmap: Bitmap of background of the template
     * @property imageDrawable: Drawable of the image provided by user (we need this ultimately)
     */

    private var backgroundBitmap: Bitmap? = null
    private var imageDrawable: Drawable? = null

    /**
     * @property viewRect: It's the main view named as "TemplateView"
     * @property backgroundRect: Coordinates of the background of current template
     * @property imageRect: Coordinates of the user's image (mutable)
     * @property imageRectFix: Coordinates of the user's image (immutable)
     */

    private val viewRect = RectF()
    private val backgroundRect = RectF()
    private val imageRect = RectF()
    private var imageRectFix = RectF()

    /**
     * Variables to track touch events
     * @property lastTouchX: Save x-axis of a touch inside a view
     * @property lastTouchY: Save y-axis of a touch inside a view
     * @property isDragging: Check if user's image can be drag-able (depends on touch events)
     */

    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var isDragging = false

    /**
     * @property viewRect: "
     */
    // Using this matrix we will show the bg template according to the aspect ratio
    private val matrix = Matrix()

    /**
     * @property viewRect: "
     */
    private val deviceScreenWidth = resources.displayMetrics.widthPixels
    private val deviceScreenHeight = resources.displayMetrics.heightPixels
    private var aspectRatio: Float = 1.0f

    /**
     * @property viewRect: "
     */
    // Calculate the transformed dimensions of imageRect
    private var transformedWidth = 0f
    private var transformedHeight = 0f

    /**
     * @property viewRect: "
     */
    // Calculate the left, top, right, and bottom values of the transformed imageRect
    private var transformedLeft = 0f
    private var transformedTop = 0f
    private var transformedRight = 0f
    private var transformedBottom = 0f

    /**
     * @property viewRect: "
     */
    // Initialize a float array to hold the matrix values
    private val matrixValues = FloatArray(9)

    /**
     * @property viewRect: "
     */
    private var isZooming = false
    private var scaleFactor = 1.0f
    private var zoomCenterX = 0f
    private var zoomCenterY = 0f

    /**
     * Set Backgrounds
     */
    override fun setBackgroundResource(@DrawableRes resId: Int) {
        backgroundBitmap = BitmapFactory.decodeResource(resources, resId)
        updateBackgroundRect()
        invalidate()
    }

    fun setBackgroundBitmap(bitmap: Bitmap?) {
        if (bitmap == null) {
            Log.e(TAG, "TemplateView: setBackgroundBitmap: ", NullPointerException("Bitmap is Null"))
            return
        }
        backgroundBitmap = bitmap
        updateBackgroundRect()
        invalidate()
    }

    @Deprecated("Deprecated in Java")
    override fun setBackgroundDrawable(drawable: Drawable?) {
        if (drawable == null) {
            Log.e(TAG, "TemplateView: setBackgroundDrawable: ", NullPointerException("Drawable is Null"))
            return
        }
        backgroundBitmap = imageUtils.createBitmapFromDrawable(drawable)
        updateBackgroundRect()
        invalidate()
    }

    /**
     * Set User Images
     */

    fun setImageResource(@DrawableRes imageId: Int) {
        imageDrawable = ContextCompat.getDrawable(context, imageId)
        invalidate()
    }

    fun setImageBitmap(bitmap: Bitmap?) {
        if (bitmap == null) {
            Log.e(TAG, "TemplateView: setImageBitmap: ", NullPointerException("Bitmap is Null"))
            return
        }
        imageDrawable = imageUtils.createDrawableFromBitmap(bitmap)
        invalidate()
    }

    fun setImageDrawable(drawable: Drawable?) {
        if (drawable == null) {
            Log.e(TAG, "TemplateView: setImageDrawable: ", NullPointerException("Drawable is Null"))
            return
        }
        imageDrawable = drawable
        invalidate()
    }

    private fun updateBackgroundRect() {
        backgroundBitmap?.let { backgroundRect.set(0f, 0f, it.width.toFloat(), it.height.toFloat()) }
        matrix.setRectToRect(backgroundRect, viewRect, Matrix.ScaleToFit.CENTER)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        val viewHeight = MeasureSpec.getSize(heightMeasureSpec)

        if (backgroundBitmap!!.width > 0) aspectRatio = backgroundBitmap!!.height.toFloat() / backgroundBitmap!!.width.toFloat()

        val measuredWidth: Int
        val measuredHeight: Int

        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            // If both width and height are fixed (e.g., match_parent or specific dimensions)
            if (viewWidth == deviceScreenWidth && viewHeight == deviceScreenHeight){
                measuredWidth = deviceScreenWidth
                measuredHeight = (measuredWidth * aspectRatio).toInt()
            }
            else {
                measuredWidth = viewWidth
                measuredHeight = viewHeight
            }
        } else if (widthMode == MeasureSpec.EXACTLY) {
            // If width is fixed (e.g., match_parent or specific dimension)
            measuredWidth = viewWidth
            measuredHeight = (measuredWidth * aspectRatio).toInt()
        } else if (heightMode == MeasureSpec.EXACTLY) {
            // If height is fixed (e.g., match_parent or specific dimension)
            measuredHeight = viewHeight
            measuredWidth = (measuredHeight / aspectRatio).toInt()
        } else {
            // If both width and height are wrap_content
            measuredWidth = if (suggestedMinimumWidth != 0) suggestedMinimumWidth else {
                if (deviceScreenWidth > backgroundBitmap!!.width) backgroundBitmap!!.width else deviceScreenWidth
            }
            measuredHeight = (measuredWidth * aspectRatio).toInt()
        }
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        imageRect.setEmpty()
        viewRect.set(0f, 0f, width.toFloat(), height.toFloat())
        matrix.setRectToRect(backgroundRect, viewRect, Matrix.ScaleToFit.CENTER)

        // Farooq's work
        // 1) update imageRect, imageRectFix, imageDrawable (sizing)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw the background template image.
        backgroundBitmap?.let {
            Log.e(TAG, "TemplateView: onDraw: backgroundBitmap is null")
            canvas.drawBitmap(it, matrix, null)
        }

        if (imageRect.isEmpty) {
            // Get the matrix values
            matrix.getValues(matrixValues)

            // Extract the scaling factors
            val scaleX = matrixValues[Matrix.MSCALE_X]
            val scaleY = matrixValues[Matrix.MSCALE_Y]

            // Calculate the transformed dimensions of imageRect
            transformedWidth = backgroundRect.width() * scaleX
            transformedHeight = backgroundRect.height() * scaleY

            // Calculate the coordinates for the user's image space based on the device's screen size
            val userImageSpaceWidth = transformedWidth * 0.475f // 513 / 1080
            val userImageSpaceHeight = transformedHeight * 0.6052f // (822 / 1354)
            val userImageSpaceX = transformedWidth * 0.4491f // 487 / 1080
            val userImageSpaceY = transformedHeight * 0.1949f // 264 / 1354

            // Calculate the coordinates for the user's image
            val userImageRight = userImageSpaceX + userImageSpaceWidth
            val userImageBottom = userImageSpaceY + userImageSpaceHeight

            imageRect.set(userImageSpaceX, userImageSpaceY, userImageRight, userImageBottom)
            imageRectFix.set(imageRect)
        }

        if (isZooming) {

            // Calculate the new size based on the scaleFactor
            val newWidth = transformedWidth * scaleFactor
            val newHeight = transformedHeight * scaleFactor

            // Calculate the new left and top
            var newLeft = zoomCenterX - newWidth / 2
            var newTop = zoomCenterY - newHeight / 2

            // Calculate the new right and bottom
            var newRight = newLeft + newWidth
            var newBottom = newTop + newHeight

            // Ensure the image remains within the view's bounds
            val viewLeft = 0f
            val viewTop = 0f
            val viewRight = width.toFloat()
            val viewBottom = height.toFloat()

            if (newLeft < viewLeft) {
                val deltaX = viewLeft - newLeft
                newLeft += deltaX
                newRight += deltaX
            }
            if (newTop < viewTop) {
                val deltaY = viewTop - newTop
                newTop += deltaY
                newBottom += deltaY
            }
            if (newRight > viewRight) {
                val deltaX = viewRight - newRight
                newLeft += deltaX
                newRight += deltaX
            }
            if (newBottom > viewBottom) {
                val deltaY = viewBottom - newBottom
                newTop += deltaY
                newBottom += deltaY
            }

            // Update the transformed coordinates
            transformedLeft = newLeft
            transformedTop = newTop
            transformedRight = newRight
            transformedBottom = newBottom

            isZooming = false

            // Update the imageRect
            imageRect.set(transformedLeft, transformedTop, transformedRight, transformedBottom)

            /*// Calculate the distance between the zoom center and the image edges
            val zoomCenterToLeft = zoomCenterX - transformedLeft
            val zoomCenterToTop = zoomCenterY - transformedTop
            val zoomCenterToRight = transformedRight - zoomCenterX
            val zoomCenterToBottom = transformedBottom - zoomCenterY

            // Apply the scale factor uniformly around the zoom center
            transformedLeft = zoomCenterX - (transformedRight * scaleFactor)
            transformedTop = zoomCenterY - (zoomCenterToBottom * scaleFactor)
            transformedRight = zoomCenterX + (zoomCenterToRight * scaleFactor)
            transformedBottom = zoomCenterY + (zoomCenterToBottom * scaleFactor)

            isZooming = false

            // Ensure the image remains within the view's bounds
            val viewLeft = 0f
            val viewTop = 0f
            val viewRight = width
            val viewBottom = height

            if (transformedLeft < viewLeft) {
                val delta = viewLeft - transformedLeft
                transformedLeft += delta
                transformedRight += delta
            }
            if (transformedTop < viewTop) {
                val delta = viewTop - transformedTop
                transformedTop += delta
                transformedBottom += delta
            }
            if (transformedRight > viewRight) {
                val delta = transformedRight - viewRight
                transformedLeft -= delta
                transformedRight -= delta
            }
            if (transformedBottom > viewBottom) {
                val delta = transformedBottom - viewBottom
                transformedTop -= delta
                transformedBottom -= delta
            }
*/

            /*// Apply the scaleFactor uniformly to all variables
            val scaledWidth = transformedWidth * scaleFactor
            val scaledHeight = transformedHeight * scaleFactor
            transformedLeft = (transformedLeft * scaleFactor)
            transformedTop = (transformedTop * scaleFactor)
            transformedRight = (transformedLeft + scaledWidth)
            transformedBottom = (transformedTop + scaledHeight)

            isZooming = false

            // Ensure the image remains within the view's bounds
            val viewLeft = 0f
            val viewTop = 0f
            val viewRight = width.toFloat()
            val viewBottom = height.toFloat()

            if (transformedLeft < viewLeft) {
                transformedRight += viewLeft - transformedLeft
                transformedLeft = viewLeft
            }
            if (transformedTop < viewTop) {
                transformedBottom += viewTop - transformedTop
                transformedTop = viewTop
            }
            if (transformedRight > viewRight) {
                transformedLeft -= transformedRight - viewRight
                transformedRight = viewRight
            }
            if (transformedBottom > viewBottom) {
                transformedTop -= transformedBottom - viewBottom
                transformedBottom = viewBottom
            }

            imageRect.set(transformedLeft, transformedTop, transformedRight, transformedBottom)*/
        }

        if (imageDrawable == null) {
            Log.e(TAG, "TemplateView: onDraw: imageDrawable is null")
        }

        /*// Calculate the space where the user's image should be displayed
        val userImageSpaceWidth = 513
        val userImageSpaceHeight = 822
        val userImageSpaceX = 487
        val userImageSpaceY = 264

        // Calculate the coordinates for the user's image
        val userImageLeft = userImageSpaceX.toFloat()
        val userImageTop = userImageSpaceY.toFloat()
        val userImageRight = (userImageSpaceX + userImageSpaceWidth).toFloat()
        val userImageBottom = (userImageSpaceY + userImageSpaceHeight).toFloat()

        imageRect.set(userImageLeft, userImageTop, userImageRight, userImageBottom)
        imageRectFix.set(imageRect)*/

        // Set the bounds for the selected image drawable.
        imageDrawable?.bounds = imageRect.toRect()

        // Clip the drawing of the selected image to the template bounds.
        canvas.clipRect(imageRectFix)

        imageDrawable?.draw(canvas)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {

        event.let { scaleGestureDetector.onTouchEvent(it) }
        // Check if a zoom gesture occurred and don't handle other touch events
        if (scaleGestureDetector.isInProgress) return true

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Check if the touch event is within the selected image bounds.
                if (imageRectFix.contains(event.x, event.y)) {
                    isDragging = true
                    lastTouchX = event.x
                    lastTouchY = event.y
                }
            }

            MotionEvent.ACTION_MOVE -> {
                if (isDragging) {
                    // Calculate the distance moved.
                    val dx = event.x - lastTouchX
                    val dy = event.y - lastTouchY

                    // Ensure the selected image stays within the template bounds.
                    imageRect.offset(dx, dy)

                    // Update the last touch position.
                    lastTouchX = event.x
                    lastTouchY = event.y

                    // Invalidate the view to trigger a redraw.
                    invalidate()
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> isDragging = false
        }

        // Consume the event to indicate that it's been handled.
        return true
    }

    // Initialize a scale gesture detector for pinch-to-zoom
    private val scaleGestureDetector: ScaleGestureDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            zoomCenterX = detector.focusX
            zoomCenterY = detector.focusY
            return true
        }
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor
            scaleFactor = 1.0f.coerceAtLeast(scaleFactor.coerceAtMost(4f))
            isZooming = true
            invalidate()
            return true
        }
    })

}
