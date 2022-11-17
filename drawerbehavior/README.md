# Drawer-Behavior
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Drawer--Behavior-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/6239)
![fdsfd](https://github.com/shiburagi/Drawer-Behavior/workflows/CI/badge.svg)

Drawer behavior is a library use **[Android DrawerLayout Support library](https://developer.android.com/training/implementing-navigation/nav-drawer)** as **Parent Class [Easy to migrate]**, that provide an **extra behavior on drawer**, such as, move view or scaling view's height while drawer on slide. 

If current project use **Android DrawerLayout Support library** and kinda boring with the effect. Then, just **change the layout code** and **calling** necessary **method for animation/effect**.


## New update
 * Change **Card barkground** color ([Jazcorra-Zero](https://github.com/jazcorra-zero))
 * **Support for get color from MaterialShapeDrawable** ([CamiloDelReal](https://github.com/CamiloDelReal))
 * Fix preview issue
 * **Migrate to AndroidX**
 * Transparent status bar for scale effect
 * Support RTL
 
## Features
 * New drawer class with 3D effect
 * Card Effect 
 * Zoom in & Zoom out effect
 * X-Translation effect
 
![Alt Text](https://raw.githubusercontent.com/shiburagi/Drawer-Behavior/preview/gif/preview1.gif)

**Android 9.0+ support**

---

### For **Flutter** : [Drawer-Behavior-Flutter](https://github.com/shiburagi/Drawer-Behavior-Flutter)

---

<a href='https://ko-fi.com/A0A0FB3V' target='_blank'><img height='36' style='border:0px;height:36px;' src='https://az743702.vo.msecnd.net/cdn/kofi4.png?v=0' border='0' alt='Buy Me a Coffee at ko-fi.com' /></a>
[![paypal](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=D9JKYQL8452AL)

## Including In Your Project

If you are a Maven user you can easily include the library by specifying it as
a dependency:

#### Maven
#### Gradle

```groovy
maven {
    name = "GitHubPackages"
    url = uri("https://maven.pkg.github.com/shiburagi/Drawer-Behavior") // Github Package
    credentials {
	username = "shiburagi"
	password = "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"
    }
}
``` 
```groovy
dependencies {
    implementation 'com.shiburagi.drawerbehavior:drawerbehavior:1.0.13'
}
```

**or**,
you can include it by **download this project** and **import /drawerbehavior** as **module**.



## How to use
**Creating the layout**

### Advance Drawer Layout
---
```xml
<com.infideap.drawerbehavior.AdvanceDrawerLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/drawer_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    android:background="@color/colorWhite"
    tools:openDrawer="start">

    <include
        layout="@layout/app_bar_default"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    <android.support.design.widget.NavigationView
        android:id="@+id/nav_view"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        android:fitsSystemWindows="true"
        android:background="@color/colorWhite"
        app:headerLayout="@layout/nav_header_main"
        app:menu="@menu/activity_main_drawer" />

    <android.support.design.widget.NavigationView
        android:id="@+id/nav_view_notification"
        android:background="@color/colorPrimary"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="end"
        android:fitsSystemWindows="false">
        <include layout="@layout/content_notification"/>
    </android.support.design.widget.NavigationView>

</com.infideap.drawerbehavior.AdvanceDrawerLayout>
```

**Initialize**
```java
drawer = (AdvanceDrawerLayout) findViewById(R.id.drawer_layout);
```

**Use custom behavior**
```java
drawer.useCustomBehavior(GravityCompat.START); //assign custom behavior for "Left" drawer
drawer.useCustomBehavior(GravityCompat.END); //assign custom behavior for "Right" drawer 
```
---

#### Card Effect

![Alt Text](https://raw.githubusercontent.com/shiburagi/Drawer-Behavior/preview/gif/preview-card-1.gif)


```java
drawer.setRadius(GravityCompat.START, 25);//set end container's corner radius (dimension)
```

---

### Advance 3D Drawer Layout
---
![Alt Text](https://raw.githubusercontent.com/shiburagi/Drawer-Behavior/preview/gif/preview-3d-2.gif)

```xml
<com.infideap.drawerbehavior.Advance3DDrawerLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/drawer_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    android:background="@color/colorPrimary"
    tools:openDrawer="start">

    <include
        layout="@layout/app_bar_default"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    <android.support.design.widget.NavigationView
        android:id="@+id/nav_view"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        android:fitsSystemWindows="true"
        android:theme="@style/ThemeOverlay.AppCompat.Dark"
        android:background="@color/colorPrimary"
        app:headerLayout="@layout/nav_header_main"
        app:menu="@menu/activity_main_drawer" />

    <android.support.design.widget.NavigationView
        android:id="@+id/nav_view_notification"
        android:background="@color/colorPrimary"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="end"
        android:fitsSystemWindows="false">
        <include layout="@layout/content_notification"/>
    </android.support.design.widget.NavigationView>

</com.infideap.drawerbehavior.Advance3DDrawerLayout>
```

**Initialize**
```java
drawer = (Advance3DDrawerLayout) findViewById(R.id.drawer_layout);
```

**Use custom behavior**
```java
drawer.setViewRotation(GravityCompat.START, 15); // set degree of Y-rotation ( value : 0 -> 45)
```
---

**Customize**
```java
drawer.setViewScale(GravityCompat.START, 0.9f); //set height scale for main view (0f to 1f)
drawer.setViewElevation(GravityCompat.START, 20); //set main view elevation when drawer open (dimension)
drawer.setViewScrimColor(GravityCompat.START, Color.TRANSPARENT); //set drawer overlay coloe (color)
drawer.setDrawerElevation(GravityCompat.START, 20); //set drawer elevation (dimension)
drawer.setContrastThreshold(3); //set maximum of contrast ratio between white text and background color.
drawer.setRadius(GravityCompat.START, 25); //set end container's corner radius (dimension)
```

## Contact
For any enquiries, please send an email to tr32010@gmail.com. 
