package com.example.moviescomposeapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviescomposeapp.dao.MovieDao
import com.example.moviescomposeapp.data.remote.MovieApi
import com.example.moviescomposeapp.model.Movie
import com.example.moviescomposeapp.model.Results


class MoviePagingSource(
    private val movieApi: MovieApi,
    private val isSearchEndPoint: Boolean,
    private val searchQuery: String? = null,
    private val movieDao: MovieDao,
) : PagingSource<Int, Results>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results> {

        return try {
            val cachedMovies = movieDao.getCachedMovies()
            val currentPage = params.key ?: 1

            try {
                val movies = if (isSearchEndPoint)
                    movieApi.search(
                        page = currentPage,
                        query = searchQuery.orEmpty(),
                    )
                else
                    movieApi.getUpcoming(
                        page = currentPage
                    )

                val moviesList = movies.body()?.results?.map {
                    Movie(
                        id = it.id ?: -1,
                        title = it.title.orEmpty(),
                        overview = it.overview.orEmpty(),
                        posterPath = it.posterPath.orEmpty(),
                        backdropPath = it.backdropPath.orEmpty(),
                        voteAverage = it.voteAverage,
                        voteCount = it.voteCount,
                        page = currentPage
                    )
                }
                movieDao.insertMovies(moviesList.orEmpty())
                // Determine the range of pages to keep based on the current page
                val pagesToKeep = listOf(currentPage - 1, currentPage, currentPage + 1)
                //current page is 5
                //4,5,6
                movieDao.deleteMoviesNotInPages(
                    pagesToKeep
                )

                LoadResult.Page(
                    data = movies.body()?.results.orEmpty(),
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (movies.body()?.results?.isEmpty() == true) null else movies.body()?.page!! + 1
                )

            } catch (e: Exception) {
                LoadResult.Page(
                    data = cachedMovies.map {
                        Results(
                            adult = false,
                            backdropPath = it.backdropPath,
                            id = it.id,
                            title = it.title,
                            overview = it.overview,
                            posterPath = it.posterPath
                        )
                    },
                    prevKey = null,
                    nextKey = null// Add one to the page number here.
                )
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Results>): Int? {
        return state.anchorPosition?.let { position ->
            val anchorPage = state.closestPageToPosition(position)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}