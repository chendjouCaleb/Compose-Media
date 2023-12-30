package com.regolia.media.gallery

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
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


@Composable
fun FullScreenDialog(visible: Boolean,
                           onDismissRequest: () -> Unit,
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
            //visibleAnimatedDialog = false
            onDismissRequest()
        },
            properties = DialogProperties(usePlatformDefaultWidth = false, decorFitsSystemWindows = true)
        ) {
            (LocalView.current.parent as? DialogWindowProvider)?.window?.let { window ->
                window.setWindowAnimations(-1)
                window.setDimAmount(0f)
            }

            Box(modifier = Modifier.fillMaxSize().background(Color.Transparent)) {
                LaunchedEffect(Unit) {
                    delay(1)
                    animateIn = true }
                AnimatedVisibility(animateIn && visibleAnimatedDialog,
                    enter = slideInVertically(initialOffsetY = {it}),
                    exit = slideOutVertically(targetOffsetY = {it}) + fadeOut()
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
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