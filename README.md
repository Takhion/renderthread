This library wraps the native **RenderThread**, providing a type-safe way to run custom animations off the UI thread.

Read about what it means in this [Medium article](https://medium.com/@workingkills/understanding-the-renderthread-4dc17bcaf979#.b9o414c29).

It transparently falls back to using the standard drawing methods if the hidden APIs aren't available, or whenever hardware acceleration is not enabled on the specified `Canvas`.

By default it will automatically try to access the RenderThread on Android versions that are known to work (Lollipop and Marshmallow), but you can force it to try on any version (like the N preview) by calling this code once before using it:

```Java
RenderThread.init(true);
```

## Capabilities

So far the RenderThread is capable of making the following drawing calls, which mirror the respective `Canvas` ones, but where each property can be animated:

* `drawCircle(centerX, centerY, radius, paint)`
* `drawRoundRect(left, top, right, bottom, cornerRadiusX, cornerRadiusY, paint)`

The `paint` values that can be animated are the following:

* alpha
* stroke width

## Usage

To use it, there are generally 3 steps involved:

```Java
CanvasProperty<Float> centerXProperty;
CanvasProperty<Float> centerYProperty;
CanvasProperty<Float> radiusProperty;
CanvasProperty<Paint> paintProperty;

Animator radiusAnimator;
Animator alphaAnimator;

@Override
protected void onDraw(Canvas canvas) {

    if (!animationInitialised) {

      // 1. create as many CanvasProperty as needed with the initial animation values
      centerXProperty = RenderThread.createCanvasProperty(canvas, initialCenterX);
      centerYProperty = RenderThread.createCanvasProperty(canvas, initialCenterY);
      radiusProperty = RenderThread.createCanvasProperty(canvas, initialRadius);
      paintProperty = RenderThread.createCanvasProperty(canvas, paint);

      // 2. create one or more Animator with the properties you want to animate
      radiusAnimator = RenderThread.createFloatAnimator(this, canvas, radiusProperty, targetRadius);
      alphaAnimator = RenderThread.createPaintAlphaAnimator(this, canvas, paintProperty, targetAlpha);
      radiusAnimator.start();
      alphaAnimator.start();
    }

    // 3. draw to the Canvas
    RenderThread.drawCircle(canvas, centerXProperty, centerYProperty, radiusProperty, paintProperty);
}
```
Check the [sample](sample/src/main/java/me/eugeniomarletti/renderthread/sample/TestView.java) for a complete implementation.

## Download

#### Gradle

```Gradle
repositories {
    maven {
        url 'https://dl.bintray.com/takhion/maven/'
    }
}

dependencies {
    compile 'me.eugeniomarletti:renderthread:1.0.0'
}
```