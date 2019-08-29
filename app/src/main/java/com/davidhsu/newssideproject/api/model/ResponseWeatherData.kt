package com.davidhsu.newssideproject.api.model

/**
 * @author : david.hsu on 2019/8/29
 */
data class ResponseWeatherData(
    val records: Records,
    val result: Result,
    val success: String
)

data class Result(
    val fields: List<Field>,
    val resource_id: String
)

data class Field(
    val id: String,
    val type: String
)

data class Records(
    val datasetDescription: String,
    val location: List<Location>
)

data class Location(
    val locationName: String,
    val weatherElement: List<WeatherElement>
)

data class WeatherElement(
    val elementName: String,
    val time: List<Time>
)

data class Time(
    val endTime: String,
    val parameter: Parameter,
    val startTime: String
)

data class Parameter(
    val parameterName: String,
    val parameterValue: String
)