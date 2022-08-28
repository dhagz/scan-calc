package tech.dhagz.scancalc

import androidx.core.text.buildSpannedString
import androidx.navigation.NamedNavArgument

/**
 * ...
 *
 *
 * Copyright (c) 2022 dhagz.tech. All rights reserved.
 *
 * @author Fernando "Dhagz" Dagoc Jr.
 * @since 2022-08-29 1:02 AM
 */
sealed class AppScreen(
    /**
     * The route to navigate to.
     */
    val parentRoute: String,
    /**
     * List of arguments to associate with destination
     */
    val arguments: List<NamedNavArgument> = emptyList()
) {
    /**
     * Returns the route based on the [parentRoute] including the [arguments] in this format:
     *   parent-route/{arg-1}/{arg-2}
     */
    val route: String = if (arguments.isEmpty()) {
        parentRoute
    } else {
        buildSpannedString {
            append(parentRoute)
            for (argument in arguments) {
                append("/{${argument.name}}")
            }
        }.toString()
    }

    /**
     * Route used to navigate to scan list
     */
    object ScanList : AppScreen("list")

    /**
     * Route used to navigate to image picker
     */
    object ImagePicker : AppScreen("image-picker")

    /**
     * Route used to navigate to camera
     */
    object Camera : AppScreen("camera")

    /**
     * Builder for the existing route to include arguments.
     * E.g:
     *   parent-route/arg-1/arg-2
     */
    fun withArgs(args: List<String>): String {
        return buildSpannedString {
            append(parentRoute)
            for (i in args)
                append("/$i")
        }.toString()
    }
}