package com.regolia.media.gallery

import android.view.WindowManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenDialog(visible: Boolean,
                     enterTransition: EnterTransition = slideInHorizontally(initialOffsetX = {it}),
                     exitTransition: ExitTransition = slideOutVertically(targetOffsetY = {it}) + fadeOut(),
                     onDismissRequest: () -> Unit,
                     insets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
                     content: @Composable () -> Unit) {

    var visibleAnimatedDialog by remember { mutableStateOf(false) }
    var animateIn by remember { mutableStateOf(false) }

    LaunchedEffect(visible) {
        if(visible)
            visibleAnimatedDialog = true
        else {
            animateIn = false
        }
    }

    if(visibleAnimatedDialog){
        Dialog(onDismissRequest = {
            visibleAnimatedDialog = false
            onDismissRequest()
        },
            properties = DialogProperties(
                usePlatformDefaultWidth = true,
                decorFitsSystemWindows = false)
        ) {
            val window =  (LocalView.current.parent as? DialogWindowProvider)?.window
            window?.let { window ->
                window.setWindowAnimations(-1)
                window.setDimAmount(0f)
                window.statusBarColor = 0
                window.navigationBarColor = 0

                val params: WindowManager.LayoutParams = window.attributes

                params.height =  WindowManager.LayoutParams.MATCH_PARENT
                params.width =  WindowManager.LayoutParams.MATCH_PARENT
                window.attributes = params
            }


            Box(modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .windowInsetsPadding(insets)
                //.border(1.dp, Color.Blue)
                .background(Color.Transparent)
            ) {
                LaunchedEffect(Unit) {
                    delay(1)
                    animateIn = true }
                AnimatedVisibility(animateIn && visibleAnimatedDialog,
                    enter = enterTransition,
                    exit = exitTransition
                ) {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Blue)
                    ) {
                        content()
                    }


                    DisposableEffect(Unit) {
                        onDispose {
                            visibleAnimatedDialog = false
                        }
                    }
                }
            }
        }
    }
}