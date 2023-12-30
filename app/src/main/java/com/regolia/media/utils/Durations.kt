package com.regolia.media.utils

import java.time.Duration


fun Duration.format(): String {
    val seconds = this.seconds
    val HH  = seconds / 3600
    val MM  = (seconds - (HH * 3600))/60
    val SS = seconds - (HH * 3600 + MM * 60)
    if(HH > 0){
        return String.format("%02d:%02d:%02d", HH, MM, SS)
    }
    return String.format("%02d:%02d", MM, SS)
}