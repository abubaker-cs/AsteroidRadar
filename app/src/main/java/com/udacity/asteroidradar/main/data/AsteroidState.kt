package com.udacity.asteroidradar.main.data

import com.udacity.asteroidradar.data.model.Asteroid

data class AsteroidState(
    val loading: Boolean,
    val asteroids: List<Asteroid>
)
