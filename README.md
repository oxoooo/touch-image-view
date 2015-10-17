Touch Image View
==========

[![](https://img.shields.io/github/tag/oxoooo/touch-image-view.svg?style=flat-square&label=jitpack.io)](https://jitpack.io/#oxoooo/touch-image-view)
[![](https://img.shields.io/github/license/oxoooo/touch-image-view.svg?style=flat-square)](LICENSE)
[![](https://img.shields.io/github/issues/oxoooo/touch-image-view.svg?style=flat-square)](https://github.com/oxoooo/touch-image-view/issues)

OXO Flavored [ImageViewZoom](https://github.com/sephiroth74/ImageViewZoom).

## Features

1. Works perfectly with `ViewPager` and [`PullBackLayout`](https://github.com/oxoooo/pull-back-layout)
2. Quick Scale enabled by default - double tap, hold and swipe to scale
3. Pre-configured minimum and maximum scale - the view's size and twice of the image's size

## Download

```gradle
repositories {
    // ...
    maven { url "https://jitpack.io" }
}

dependencies {
    // ... support library ...
    // ...
    compile 'com.github.oxoooo:touch-image-view:1.0.0'
}
```

## Usage

```
<ooo.oxo.library.widget.TouchImageView
    android:id="@+id/image"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

## License

[MIT License](LICENSE)
