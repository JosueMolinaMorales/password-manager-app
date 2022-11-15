package com.example.password_manager_app.data

fun getRandomStreamingService(): String {
    val services = listOf<String>(
        "XtremeHD",
        "YouTube TV",
        "Netflix",
        "Sling TV",
        "Apple TV +",
        "Hulu Plus Live TV",
        "HBO Max",
        "Acorn TV",
        "CBS All Access",
        "DirecTV Now",
        "Showtime",
        "DirecTV Stream",
        "Amazon Prime TV",
        "Philo",
        "Fubo",
        "Disney Plus",
    )
    return services.random()
}