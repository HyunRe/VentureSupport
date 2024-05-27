package com.example.venturesupport.model

import com.google.gson.annotations.SerializedName

data class DirectionsResponse(
    @SerializedName("route") val route: Route
)

data class Route(
    @SerializedName("trafast") val trafast: List<RoutePath>
)

data class RoutePath(
    @SerializedName("summary") val summary: RouteSummary,
    @SerializedName("path") val path: List<List<Double>>
)

data class RouteSummary(
    @SerializedName("start") val start: Point,
    @SerializedName("goal") val goal: Point,
    @SerializedName("distance") val distance: Int,
    @SerializedName("duration") val duration: Int
)

data class Point(
    @SerializedName("location") val location: List<Double>
)