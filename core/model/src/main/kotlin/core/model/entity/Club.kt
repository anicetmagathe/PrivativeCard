package core.model.entity

data class Club(val name: String, val logo: Int)

data class MainClub(val name: String, val clubs: List<Club>)