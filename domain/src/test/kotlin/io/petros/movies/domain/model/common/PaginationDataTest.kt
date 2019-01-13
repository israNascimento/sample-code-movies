package io.petros.movies.domain.model.common

import io.petros.movies.domain.model.movie.Movie
import io.petros.movies.domain.model.movie.MoviesResultPage
import io.petros.movies.test.domain.TestMoviesProvider.Companion.NEXT_PAGE
import io.petros.movies.test.domain.TestMoviesProvider.Companion.provideMovie
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import strikt.assertions.isTrue

class PaginationDataTest {

    private val firstPageItems = listOf(provideMovie(id = 1), provideMovie(id = 2), provideMovie(id = 3))
    private val secondPageItems = listOf(provideMovie(id = 4), provideMovie(id = 5), provideMovie(id = 6))

    private lateinit var testedClass: PaginationData<Movie>

    @Before
    fun setUp() {
        testedClass = PaginationData()
    }

    @Test
    fun `Given no pages, when checking if it is empty, then the return value is true`() {
        expectThat(testedClass.isEmpty()).isTrue()
    }

    @Test
    fun `Given some pages, when checking if it is empty, then the return value is false`() {
        testedClass.addPage(MoviesResultPage(NEXT_PAGE, firstPageItems))

        expectThat(testedClass.isEmpty()).isFalse()
    }

    @Test
    fun `Given single page, when checking if it is the first page, then the return value is true`() {
        testedClass.addPage(MoviesResultPage(NEXT_PAGE, firstPageItems))

        expectThat(testedClass.isFirstPage()).isTrue()
    }

    @Test
    fun `Given multiple pages, when checking it is the first page, then the return value is false`() {
        testedClass.addPage(MoviesResultPage(NEXT_PAGE, firstPageItems))
        testedClass.addPage(MoviesResultPage(NEXT_PAGE, secondPageItems))

        expectThat(testedClass.isFirstPage()).isFalse()
    }

    @Test
    fun `When adding a first page, then the pagination state is the expected one`() {
        expectThat(testedClass.items()).isEmpty()
        expectThat(testedClass.latestItems()).isNull()
        expectThat(testedClass.nextPage()).isNull()
        val firstPage = MoviesResultPage(NEXT_PAGE, firstPageItems)

        testedClass.addPage(firstPage)

        expectThat(testedClass.items()).isEqualTo(firstPageItems)
        expectThat(testedClass.latestItems()).isEqualTo(firstPageItems)
        expectThat(testedClass.nextPage()).isEqualTo(NEXT_PAGE)
    }

    @Test
    fun `When adding a second page, then the pagination state is the expected one`() {
        expectThat(testedClass.items()).isEmpty()
        expectThat(testedClass.latestItems()).isNull()
        expectThat(testedClass.nextPage()).isNull()
        val firstPage = MoviesResultPage(NEXT_PAGE, firstPageItems)
        testedClass.addPage(firstPage)
        val secondPage = MoviesResultPage(NEXT_PAGE + 1, secondPageItems)

        testedClass.addPage(secondPage)

        expectThat(testedClass.items()).isEqualTo(firstPageItems + secondPageItems)
        expectThat(testedClass.latestItems()).isEqualTo(secondPageItems)
        expectThat(testedClass.nextPage()).isEqualTo(NEXT_PAGE + 1)
    }

    @Test
    fun `When pagination data is cleared, then all fields are reset`() {
        testedClass.addPage(MoviesResultPage(NEXT_PAGE, firstPageItems))
        expectThat(testedClass.isEmpty()).isFalse()
        expectThat(testedClass.latestItems()).isNotNull()
        expectThat(testedClass.nextPage()).isNotNull()

        testedClass.clear()

        expectThat(testedClass.isEmpty()).isTrue()
        expectThat(testedClass.latestItems()).isNull()
        expectThat(testedClass.nextPage()).isNull()
    }

}
