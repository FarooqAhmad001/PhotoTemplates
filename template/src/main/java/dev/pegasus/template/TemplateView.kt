package dev.pegasus.template

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.VelocityTracker
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.toRect
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import dev.pegasus.template.helpers.dataClasses.TemplateModel
import dev.pegasus.template.helpers.dataClasses.FrameType
import dev.pegasus.template.helpers.state.CustomViewState
import dev.pegasus.template.utils.HelperUtils.TAG
import dev.pegasus.template.utils.ImageUtils
import dev.pegasus.template.utils.RotationGestureDetector
import dev.pegasus.template.viewModels.TemplateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Float.min

class TemplateView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val imageUtils by lazy { ImageUtils(context) }

    /**
     * @property backgroundBitmap: Bitmap of background of the template
     * @property templateBitmap: Transparent template
     * @property imageBitmap: Holds the image the user select
     * @property imageDrawable: Drawable of the image provided by user (we need this ultimately)
     */
    private var backgroundBitmap: Bitmap? = null
    private var templateBitmap: Bitmap? = null
    private var imageBitmap: Bitmap? = null
    private var imageDrawable: Drawable? = null

    /**
     * @property matrix: This matrix object is used to scale the background template according to the device screen and maintain the aspect ratio
     * @property matrixValues: It holds the matrix values i.e. scale values which we need to get the location (width, height) of our background template
     * @property imageMatrix: It is used for holding the zooming, rotation and dragging values and apply during onDraw function
     * @property tempMatrix: This matrix will temporarily holds the scaling, zooming, rotation and dragging values and then assign it to the imageMatrix
     */
    private val matrix = Matrix()
    private val matrixValues = FloatArray(9)
    private val imageMatrix = Matrix()
    private val tempMatrix = Matrix()

    /**
     * @property lifecycleOwner: to obtain coroutine scope and execute some tasks on the background and avoid blocking the main thread
     */
    private lateinit var lifecycleOwner: LifecycleOwner

    /**
     * @property viewRect: It's the main view named as "TemplateView"
     * @property backgroundRect: Coordinates of the background of current template
     * @property imageRect: Coordinates of the user's image (mutable)
     * @property imageRectFix: Coordinates of the user's image (immutable)
     * @property clipPath: It will be used for a circular templates
     */
    private val viewRect = RectF()
    private val backgroundRect = RectF()
    private val imageRect = RectF()
    private val imageRectFix = RectF()
    private val clipPath = Path()

    /**
     * @property templateModel: Complete specifications or attributes for a template
     */
    private var templateModel: TemplateModel? = null

    /**
     * Variables to track touch events
     * @property activePointerId: Save the active pointer (finger touch) Id
     * @property lastTouchX: Save x-axis of a touch inside a view
     * @property lastTouchY: Save y-axis of a touch inside a view
     * @property dragValueX: Save the overall x-axis drag value, so to keep the user image in-place after screen configuration
     * @property dragValueY: Save the overall y-axis drag value, so to keep the user image inplace after screen configuration
     * @property isConfigurationTrigger: this value is a flag to indicate that whether configuration happened or not.
     * @property flingAnimator: for smooth pinch to zoom functionality
     * @property velocityTracker: used in zoom feature
     */
    private var activePointerId = -1
    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var dragValueX = 0f
    private var dragValueY = 0f
    private var isConfigurationTrigger = false
    private var flingAnimator: ValueAnimator? = null
    private val velocityTracker = VelocityTracker.obtain()

    /**
     * @property deviceScreenHeight: It saves the device screen height value, and we use it in onMeasure function, for properly locating our custom view
     * @property deviceScreenWidth: It saves the device screen width value, and we use it in onMeasure function to properly locate our custom view on the device screen
     * @property templateAspectRatio: to hold the aspect ratio of the background template
     * @property imageAspectRatio: to hold the aspect ratio of the user selected image
     */
    private val deviceScreenHeight = resources.displayMetrics.heightPixels
    private val deviceScreenWidth = resources.displayMetrics.widthPixels
    private var templateAspectRatio: Float = 1.0f
    private var imageAspectRatio: Float = 1.0f

    /**
     * @property transformedWidth: It holds the width value of the background template after scaling by the matrix
     * @property transformedHeight: It holds the height value of the background template after scaling by the matrix
     * @property transformedLeft: This holds the left value of the frame (where we want to show the user image) inside a background template
     * @property transformedTop: This holds the top value of the frame (where we want to show the user image) inside a background template
     * @property transformedRight: This holds the right value of the frame (where we want to show the user image) inside a background template
     * @property transformedBottom: This holds the bottom value of the frame (where we want to show the user image) inside a background template
     */
    private var transformedWidth = 0f
    private var transformedHeight = 0f
    private var transformedLeft = 0f
    private var transformedTop = 0f
    private var transformedRight = 0f
    private var transformedBottom = 0f

    /**
     * @property zoomScaleFactor: holds the zoom scale ratio.
     * @property maxScaleFactor: define the maximum scale for zoom
     * @property minScaleFactor: define the minimum scale for zoom
     * @property zoomCenterX: holds the zoom center x value for equal zooming on all sides
     * @property zoomCenterY: holds the zoom center y value for equal zooming on all sides
     * @property rotationAngleDelta: holds the value of the rotation
     */
    private var zoomScaleFactor = 1.0f
    private val maxScaleFactor = 4.0f
    private val minScaleFactor = 0.5f
    private var zoomCenterX = 0f
    private var zoomCenterY = 0f
    private var rotationAngleDelta = 0f

    /**
     * @property viewModel: holds the user selected image during configuration changes
     */
    private val viewModel: TemplateViewModel? by lazy {
        ViewModelProvider(context as ViewModelStoreOwner)[TemplateViewModel::class.java]
    }

    /**
     * @property setLifecycleOwner: A lifecycle scope to launch coroutine in a scope of the activity or fragment lifecycle
     */
    fun setLifecycleOwner(owner: LifecycleOwner) {
        lifecycleOwner = owner
    }

    /**
     * Set Backgrounds
     *//*override fun setBackgroundResource(@DrawableRes resId: Int) {
        backgroundBitmap = BitmapFactory.decodeResource(resources, resId)
        updateBackgroundRect()
        invalidate()
    }*/

    /**
     * Set Backgrounds from the model received from server
     */
    fun setBackgroundFromModel(model: TemplateModel?) {
        // Extract the necessary data from the model and set the background accordingly
        model?.let {
            templateModel = it
            backgroundBitmap = BitmapFactory.decodeResource(resources, it.bgImage)
            templateBitmap = BitmapFactory.decodeResource(resources, it.template)

            // Reset these rectangles at this point, bcz we have to update its values now
            imageRect.setEmpty()
            imageRectFix.setEmpty()
            resetTransformation()

            updateBackgroundRect()

            requestLayout()
            invalidate()
        }
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

    /**
     * Set User Images
     */
    fun setImageResource(@DrawableRes imageId: Int) {
        imageDrawable = ContextCompat.getDrawable(context, imageId)
        imageDrawable?.let {
            imageAspectRatio = it.intrinsicWidth.toFloat() / it.intrinsicHeight.toFloat()
        }

        updateUserImageRect()
        dragValueX = 0f
        dragValueY = 0f
        invalidate()
    }

    fun setImageBitmap(bitmap: Bitmap?) {
        bitmap?.let {
            imageBitmap = it
            imageAspectRatio = it.width.toFloat() / it.height.toFloat()
            imageDrawable = imageUtils.createDrawableFromBitmap(it)

            resetTransformation()
            updateUserImageRect()
            dragValueX = 0f
            dragValueY = 0f
            invalidate()
        }
    }

    fun setImageDrawable(drawable: Drawable?) {
        drawable?.let {
            imageDrawable = it
            imageAspectRatio = it.intrinsicWidth.toFloat() / it.intrinsicHeight.toFloat()

            resetTransformation()
            updateUserImageRect()
            dragValueX = 0f
            dragValueY = 0f
            invalidate()
        }
    }

    private fun updateBackgroundRect() {
        backgroundBitmap?.let { backgroundRect.set(0f, 0f, it.width.toFloat(), it.height.toFloat()) }

        // To ensure correct positioning everytime the template changed
        matrix.reset()
        matrix.setRectToRect(backgroundRect, viewRect, Matrix.ScaleToFit.CENTER)
    }

    private fun updateUserImageRect() {
        if (imageRectFix.isEmpty) return
        Log.d(TAG, "updateUserImageRect: is called")
        // Calculate the available width and height while maintaining aspect ratio
        var availableHeight = imageRectFix.height().toInt()
        var availableWidth = (availableHeight * imageAspectRatio).toInt()

        if (availableWidth < imageRectFix.width()) {
            availableWidth = imageRectFix.width().toInt()
            availableHeight = (availableWidth / imageAspectRatio).toInt()
        }

        // Calculate the position to center the imageRect inside imageRectFix
        val left = imageRectFix.centerX() - (availableWidth / 2f)
        val top = imageRectFix.centerY() - (availableHeight / 2f)
        val right = left + availableWidth
        val bottom = top + availableHeight

        // Set the calculated values to imageRect
        imageRect.set(left, top, right, bottom)
    }

    private fun setImageFixRectangle() {
        // Get the matrix values
        matrix.getValues(matrixValues)
        // Extract the scaling factors
        val scaleX = matrixValues[Matrix.MSCALE_X]
        val scaleY = matrixValues[Matrix.MSCALE_Y]
        // Calculate the transformed dimensions of imageRect
        transformedWidth = backgroundRect.width() * scaleX
        transformedHeight = backgroundRect.height() * scaleY

        Log.i(TAG, "setImageFixRectangle: template width: $width")
        Log.i(TAG, "setImageFixRectangle: template height: $height")
        Log.i(TAG, "setImageFixRectangle: transformed width: $transformedWidth")
        Log.i(TAG, "setImageFixRectangle: transformed height: $transformedHeight")

        // Calculate the coordinates for the user's image space based on the device's screen size
        templateModel?.let {
            val scaleFactor = if (it.width > it.height) {
                transformedWidth / it.width
            } else {
                transformedHeight / it.height
            }
            val userImageSpaceWidth = it.frameWidth * scaleFactor
            val userImageSpaceHeight = it.frameHeight * scaleFactor
            val userImageSpaceX = transformedWidth * (it.frameX / it.width)
            val userImageSpaceY = transformedHeight * (it.frameY / it.height)
            // Calculate the coordinates for the user's image
            val userImageRight = userImageSpaceX + userImageSpaceWidth
            val userImageBottom = userImageSpaceY + userImageSpaceHeight

            imageRectFix.set(userImageSpaceX, userImageSpaceY, userImageRight, userImageBottom)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d(TAG, "onMeasure: is called")

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        val viewHeight = MeasureSpec.getSize(heightMeasureSpec)

        if (backgroundBitmap == null || backgroundBitmap!!.width <= 0) {
            setMeasuredDimension(viewWidth, viewHeight)
            return
        }

        templateAspectRatio = backgroundBitmap!!.height.toFloat() / backgroundBitmap!!.width.toFloat()

        val measuredWidth: Int
        val measuredHeight: Int

        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            // If both width and height are fixed (e.g., match_parent or specific dimensions)
            if (viewWidth == deviceScreenWidth && viewHeight == deviceScreenHeight) {
                measuredWidth = deviceScreenWidth
                measuredHeight = (measuredWidth * templateAspectRatio).toInt()
            } else {
                measuredWidth = viewWidth
                measuredHeight = viewHeight
            }
        } else if (widthMode == MeasureSpec.EXACTLY) {
            // If width is fixed (e.g., match_parent or specific dimension)
            measuredWidth = viewWidth
            measuredHeight = (measuredWidth * templateAspectRatio).toInt()
        } else if (heightMode == MeasureSpec.EXACTLY) {
            // If height is fixed (e.g., match_parent or specific dimension)
            measuredHeight = viewHeight
            measuredWidth = (measuredHeight / templateAspectRatio).toInt()
        } else {
            // If both width and height are wrap_content
            measuredWidth = if (suggestedMinimumWidth != 0) suggestedMinimumWidth else {
                if (deviceScreenWidth > backgroundBitmap!!.width) backgroundBitmap!!.width else deviceScreenWidth
            }
            measuredHeight = (measuredWidth * templateAspectRatio).toInt()
        }
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.d(TAG, "onSizeChanged: is called")

        imageRectFix.setEmpty()
        imageRect.setEmpty()
        viewRect.set(0f, 0f, w.toFloat(), h.toFloat())

        matrix.reset()
        matrix.setRectToRect(backgroundRect, viewRect, Matrix.ScaleToFit.CENTER)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.d(TAG, "onDraw: is called")

        if (!::lifecycleOwner.isInitialized) return

        lifecycleOwner.lifecycleScope.launch {
            // Draw the background template image.
            backgroundBitmap?.let { canvas.drawBitmap(it, matrix, null) }

            if (imageRect.isEmpty) {
                Log.d(TAG, "onDraw: imageRect is empty")
                setImageFixRectangle()
                updateUserImageRect()
            }

            if (isConfigurationTrigger) {
                updateMatrix()
                isConfigurationTrigger = false
            }

            // Save the current state of the canvas
            canvas.save()

            // Clip the drawing of the selected image to the template bounds.
            if (templateModel?.frameType == FrameType.Rectangle) {

                // Save the canvas state before applying rotation
                //canvas.save()

                // Rotate the canvas around the center of the image rectangle
                templateModel?.rotation?.let { canvas.rotate(it, imageRectFix.centerX(), imageRectFix.centerY()) }

                // Draw the rotated user image
                //drawUserImage(canvas)

                // Restore the canvas state after drawing the rotated user image
                //canvas.restore()

                //canvas.clipRect(imageRectFix)
            }
            else if (templateModel?.frameType == FrameType.Circle) {
                // Set up the circular clip path
                val centerX = imageRectFix.centerX()
                val centerY = imageRectFix.centerY()
                val width = imageRectFix.width()
                val height = imageRectFix.height()

                // Calculate the radius as half of the smaller dimension (width or height)
                val radius = min(width / 2f, height / 2f)

                // Reset the clipPath before adding a new circle
                clipPath.reset()
                clipPath.addCircle(centerX, centerY, radius, Path.Direction.CW)
                canvas.clipPath(clipPath)
            }

            // Apply the matrix for zooming, rotation, and translation
            canvas.concat(imageMatrix)

            // Set the bounds for the selected image drawable.
            imageDrawable?.bounds = imageRect.toRect()

            // Draw the image
            imageDrawable?.draw(canvas)

            // Restore the canvas to its original state
            canvas.restore()

            // Draw the transparent template image.
            templateBitmap?.let { canvas.drawBitmap(it, matrix, null) }
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        Log.d(TAG, "onSaveInstanceState: is called")
        // Save the user selected image in a view model.
        viewModel?.updateImage(imageBitmap)
        return CustomViewState(super.onSaveInstanceState()).apply {
            photoAspectRatio = imageAspectRatio
            zoomFactor = zoomScaleFactor
            centerX = zoomCenterX
            centerY = zoomCenterY
            dx = dragValueX
            dy = dragValueY
            rotationAngle = rotationAngleDelta
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        Log.d(TAG, "onRestoreInstanceState: is called")
        if (state is CustomViewState) {
            super.onRestoreInstanceState(state.superState)
            // Get the image from the view-model
            imageBitmap = viewModel?.getImage()
            imageBitmap?.let { imageDrawable = imageUtils.createDrawableFromBitmap(it) }
            imageAspectRatio = state.photoAspectRatio
            zoomScaleFactor = state.zoomFactor
            zoomCenterX = state.centerX
            zoomCenterY = state.centerY
            dragValueX = state.dx
            dragValueY = state.dy
            rotationAngleDelta = state.rotationAngle
            isConfigurationTrigger = true
        } else super.onRestoreInstanceState(state)
    }

    private fun updateMatrix() {
        Log.d(TAG, "updateMatrix: is called")
        tempMatrix.reset()
        // Apply scaling
        if (zoomCenterX != 0f || zoomCenterY != 0f) tempMatrix.setScale(zoomScaleFactor, zoomScaleFactor, zoomCenterX, zoomCenterY)
        // Apply rotation
        if (rotationAngleDelta != 0f) tempMatrix.postRotate(rotationAngleDelta, imageRectFix.centerX(), imageRectFix.centerY())
        // Apply translation (dragging)
        if (dragValueX != 0f && dragValueY != 0f) tempMatrix.postTranslate(dragValueX, dragValueY)

        if (!tempMatrix.isIdentity) {
            imageMatrix.set(tempMatrix)
            invalidate()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            lifecycleOwner.lifecycleScope.launch {
                if (it.pointerCount > 1) {
                    // Handle zoom gesture
                    scaleGestureDetector.onTouchEvent(it)
                    // Handle the two-finger rotation gesture
                    rotationGestureDetector.onTouchEvent(it)
                    // Handle drag feature when both the fingers are on the screen
                    gestureDetector.onTouchEvent(it)
                }
                // Handle single finger drag feature.
                // Single finger should in position of the image when dragging
                if (it.pointerCount == 1 && imageRectFix.contains(event.x, event.y)) gestureDetector.onTouchEvent(it)

                when (it.action) {
                    MotionEvent.ACTION_UP -> {
                        velocityTracker.computeCurrentVelocity(1000)
                        val velocityX = velocityTracker.xVelocity
                        val velocityY = velocityTracker.yVelocity
                        // Start fling animation when the user releases the touch
                        startFlingAnimation(velocityX, velocityY)
                    }
                }
            }
        }
        // Consume the event to indicate that it's been handled.
        return true
    }

    private fun startFlingAnimation(velocityX: Float, velocityY: Float) {
        flingAnimator?.cancel()
        flingAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 500
            interpolator = DecelerateInterpolator()
            addUpdateListener {
                val fraction = it.animatedValue as Float
                imageMatrix.postTranslate(fraction * velocityX / 2, fraction * velocityY / 2)
                invalidate()
            }
            start()
        }
    }

    private val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(event: MotionEvent): Boolean {
            if (imageRectFix.contains(event.x, event.y)) {
                Log.d(TAG, "gestureDetector: onDown is called")
                lastTouchX = event.x
                lastTouchY = event.y

                // Save the ID of the pointer
                activePointerId = event.getPointerId(0)

                // clear velocityTracker and addMovement everytime the user touch the image
                velocityTracker.clear()
                velocityTracker?.addMovement(event)
            }
            return true
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            Log.d(TAG, "onDoubleTap: gestureDetector is called")
            if (imageRectFix.contains(e.x, e.y)) resetTransformation()
            return true
        }

        override fun onScroll(e1: MotionEvent?, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            Log.d(TAG, "gestureDetector: onScroll is called")
            val pointerIndex = e2.findPointerIndex(activePointerId)
            if (pointerIndex != -1) {
                // Calculate the distance moved.
                val dx = e2.getX(pointerIndex) - lastTouchX
                val dy = e2.getY(pointerIndex) - lastTouchY

                dragValueX += dx
                dragValueY += dy

                // Update the last touch position.
                lastTouchX = e2.x
                lastTouchY = e2.y

                // Invalidate the view to trigger a redraw.
                updateMatrix()
            }
            return true
        }
    })

    // Initialize a scale gesture detector for pinch-to-zoom
    private val scaleGestureDetector: ScaleGestureDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            zoomScaleFactor *= detector.scaleFactor
            zoomScaleFactor = zoomScaleFactor.coerceIn(minScaleFactor, maxScaleFactor) // Adjust the limits as needed

            zoomCenterX = detector.focusX
            zoomCenterY = detector.focusY

            updateMatrix()
            return true
        }
    })

    private fun resetTransformation() {
        Log.d(TAG, "resetTransformation: is called")
        dragValueX = 0f
        dragValueY = 0f
        zoomScaleFactor = 0f
        rotationAngleDelta = 0f
        zoomCenterX = 0f
        zoomCenterY = 0f
        zoomScaleFactor = 1f
        imageMatrix.reset()
        rotationGestureDetector.resetRotation()
        invalidate()
    }

    // Initialize a rotation detector
    private val rotationGestureDetector = RotationGestureDetector(object : RotationGestureDetector.OnRotationGestureListener {
        override fun onRotation(rotationAngle: Float) {
            Log.d(TAG, "onRotation: is called and rotationAngle: $rotationAngle")
            // Handle the rotation gesture here.
            if (rotationAngle != rotationAngleDelta) {
                Log.d(TAG, "onRotation: is happened")
                rotationAngleDelta = rotationAngle
                updateMatrix()
            }
        }
    })

    fun getViewAsBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        draw(canvas)
        return bitmap
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        // To release any resources taken from the velocity-tracker
        velocityTracker?.clear()
        flingAnimator?.cancel()
    }

}
