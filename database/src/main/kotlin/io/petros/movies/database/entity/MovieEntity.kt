package io.petros.movies.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.petros.movies.domain.model.movie.Movie
import io.petros.movies.utils.MOVIE_DATE_FORMAT
import io.petros.movies.utils.toDate

@Suppress("DataClassContainsFunctions")
@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val movieId: Int,
    val title: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val overview: String,
    val backdrop: String?,
) {

    companion object {

        fun from(movie: Movie) = MovieEntity(
            movieId = movie.id,
            title = movie.title,
            releaseDate = movie.releaseDate.toDate(MOVIE_DATE_FORMAT),
            voteAverage = movie.voteAverage,
            voteCount = movie.voteCount,
            overview = movie.overview,
            backdrop = movie.backdrop,
        )

    }

    @Suppress("unused")
    fun toMovie() = Movie(
        id = movieId,
        title = title,
        releaseDate = releaseDate.toDate(MOVIE_DATE_FORMAT),
        voteAverage = voteAverage,
        voteCount = voteCount,
        overview = overview,
        backdrop = backdrop,
    )

}
