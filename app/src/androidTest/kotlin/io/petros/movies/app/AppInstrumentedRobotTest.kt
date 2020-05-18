package io.petros.movies.app

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import io.petros.movies.android_test.robot.actions.Actions
import io.petros.movies.android_test.robot.actions.NoActions
import io.petros.movies.app.robot.AppRobot
import io.petros.movies.app.robot.movie_details.MovieDetailsToolbarRobot.Companion.MOVIE_DETAILS_TOOLBAR_LABEL
import io.petros.movies.app.robot.movies.MoviesToolbarRobot.Companion.MOVIES_TOOLBAR_FILTER_MONTH
import io.petros.movies.app.robot.movies.MoviesToolbarRobot.Companion.MOVIES_TOOLBAR_FILTER_MONTH_MAY
import io.petros.movies.app.robot.movies.MoviesToolbarRobot.Companion.MOVIES_TOOLBAR_FILTER_YEAR
import io.petros.movies.app.robot.movies.MoviesToolbarRobot.Companion.MOVIES_TOOLBAR_FILTER_YEAR_2020
import io.petros.movies.app.robot.movies.MoviesToolbarRobot.Companion.MOVIES_TOOLBAR_LABEL
import io.petros.movies.app.robot.picker.MonthPickerRobot.Companion.MOVIE_MONTH_PICKER_MONTH_MAY
import io.petros.movies.app.robot.picker.MonthPickerRobot.Companion.MOVIE_MONTH_PICKER_TITLE
import io.petros.movies.app.robot.picker.YearPickerRobot.Companion.MOVIE_YEAR_PICKER_TITLE
import io.petros.movies.app.robot.picker.YearPickerRobot.Companion.MOVIE_YEAR_PICKER_YEAR_2020
import io.petros.movies.app.robot.robot
import io.petros.movies.movies.MoviesActivity
import io.petros.movies.test.utils.MOCK_WEB_SERVER_PORT
import io.petros.movies.test.utils.mockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@Suppress("LongMethod")
@RunWith(AndroidJUnit4::class)
class AppInstrumentedRobotTest {

    companion object {

        // MOCK RESPONSES

        private const val MOVIES_DIR = "responses/movies"
        private const val NO_FILTER_DIR = "/no_filter"
        private const val YEAR_FILTER_DIR = "/year_filter"
        private const val MONTH_FILTER_DIR = "/month_filter"
        private const val NO_FILTER_FIRST_MOVIES_PAGE_FILE = "$MOVIES_DIR$NO_FILTER_DIR/movies_page_first.json"
        private const val YEAR_FILTER_FIRST_MOVIES_PAGE_FILE = "$MOVIES_DIR$YEAR_FILTER_DIR/movies_page_first.json"
        private const val YEAR_AND_MONTH_FILTER_FIRST_MOVIES_PAGE_FILE =
            "$MOVIES_DIR$YEAR_FILTER_DIR$MONTH_FILTER_DIR/movies_page_first.json"

        // NO FILTER

        private const val SONIC_THE_HEDGEHOG_ITEM_POSITION = 1
        private const val SONIC_THE_HEDGEHOG_ITEM_TITLE = "Sonic the Hedgehog"
        private const val SONIC_THE_HEDGEHOG_ITEM_RELEASE_DATE = "2020 (February)"
        private const val SONIC_THE_HEDGEHOG_ITEM_VOTE = "7.6 ★ (3013)"
        private const val SONIC_THE_HEDGEHOG_ITEM_OVERVIEW =
            "Based on the global blockbuster videogame franchise from Sega, Sonic the Hedgehog tells the story of the " +
                    "world’s speediest hedgehog as he embraces his new home on Earth. In this live-action adventure " +
                    "comedy, Sonic and his new best friend team up to defend the planet from the evil genius Dr. " +
                    "Robotnik and his plans for world domination."

        private const val MORTAL_KOMBAT_LEGENDS_ITEM_POSITION = 10
        private const val MORTAL_KOMBAT_LEGENDS_ITEM_TITLE = "Mortal Kombat Legends: Scorpion’s Revenge"
        private const val MORTAL_KOMBAT_LEGENDS_ITEM_RELEASE_DATE = "2020 (April)"
        private const val MORTAL_KOMBAT_LEGENDS_ITEM_VOTE = "8.5 ★ (192)"
        private const val MORTAL_KOMBAT_LEGENDS_ITEM_OVERVIEW =
            "After the vicious slaughter of his family by stone-cold mercenary Sub-Zero, Hanzo Hasashi is exiled to " +
                    "the torturous Netherrealm. There, in exchange for his servitude to the sinister Quan Chi, he’s " +
                    "given a chance to avenge his family – and is resurrected as Scorpion, a lost soul bent on revenge. " +
                    "Back on Earthrealm, Lord Raiden gathers a team of elite warriors – Shaolin monk Liu Kang, Special " +
                    "Forces officer Sonya Blade and action star Johnny Cage – an unlikely band of heroes with one chance " +
                    "to save humanity. To do this, they must defeat Shang Tsung’s horde of Outworld gladiators and reign " +
                    "over the Mortal Kombat tournament."

        // YEAR FILTER

        private const val UNDERWATER_ITEM_POSITION = 1
        private const val UNDERWATER_ITEM_TITLE = "Underwater"
        private const val UNDERWATER_ITEM_RELEASE_DATE = "2020 (January)"
        private const val UNDERWATER_ITEM_VOTE = "6.4 ★ (864)"
        private const val UNDERWATER_ITEM_OVERVIEW =
            "After an earthquake destroys their underwater station, six researchers must navigate two miles along the " +
                    "dangerous, unknown depths of the ocean floor to make it to safety in a race against time."

        private const val TROLLS_WORLD_TOUR_ITEM_POSITION = 10
        private const val TROLLS_WORLD_TOUR_ITEM_TITLE = "Trolls World Tour"
        private const val TROLLS_WORLD_TOUR_ITEM_RELEASE_DATE = "2020 (March)"
        private const val TROLLS_WORLD_TOUR_ITEM_VOTE = "7.7 ★ (397)"
        private const val TROLLS_WORLD_TOUR_ITEM_OVERVIEW =
            "Queen Poppy and Branch make a surprising discovery — there are other Troll worlds beyond their own, and " +
                    "their distinct differences create big clashes between these various tribes. When a mysterious " +
                    "threat puts all of the Trolls across the land in danger, Poppy, Branch, and their band of friends " +
                    "must embark on an epic quest to create harmony among the feuding Trolls to unite them against " +
                    "certain doom."

        // MONTH FILTER

        private const val LOVE_WEDDING_REPEAT_ITEM_POSITION = 1
        private const val LOVE_WEDDING_REPEAT_ITEM_TITLE = "Love Wedding Repeat"
        private const val LOVE_WEDDING_REPEAT_ITEM_RELEASE_DATE = "2020 (April)"
        private const val LOVE_WEDDING_REPEAT_ITEM_VOTE = "5.8 ★ (176)"
        private const val LOVE_WEDDING_REPEAT_ITEM_OVERVIEW = "While trying to make his sister's wedding day go " +
                "smoothly, Jack finds himself juggling an angry ex-girlfriend, an uninvited guest with a secret, a " +
                "misplaced sleep sedative, and the girl that got away in alternate versions of the same day."

        private const val BEHIND_YOU_ITEM_POSITION = 10
        private const val BEHIND_YOU_ITEM_TITLE = "Behind You"
        private const val BEHIND_YOU_ITEM_RELEASE_DATE = "2020 (April)"
        private const val BEHIND_YOU_ITEM_VOTE = "6.0 ★ (8)"
        private const val BEHIND_YOU_ITEM_OVERVIEW = "Two young sisters find that all the mirrors in their estranged " +
                "aunt's house are covered or hidden. When one of them happens upon a mirror in the basement, she " +
                "unknowingly releases a malicious demon."

    }

    @get:Rule
    val activityRule = ActivityTestRule(MoviesActivity::class.java, false, false)

    private val server = MockWebServer()

    @Before
    fun setUp() {
        server.start(MOCK_WEB_SERVER_PORT)
        enqueueMockResponses()
        activityRule.launchActivity(Intent())
    }

    private fun enqueueMockResponses() {
        server.enqueue(mockResponse(NO_FILTER_FIRST_MOVIES_PAGE_FILE))
        server.enqueue(mockResponse(YEAR_FILTER_FIRST_MOVIES_PAGE_FILE))
        server.enqueue(mockResponse(YEAR_AND_MONTH_FILTER_FIRST_MOVIES_PAGE_FILE))
        server.enqueue(mockResponse(NO_FILTER_FIRST_MOVIES_PAGE_FILE))
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun app_e2e_robot_test() {
        robot {
            check_movies_no_filter()
            check_movies_year_picker()
            check_movies_year_filter()
            check_movies_month_picker()
            check_movies_month_filter()
            check_movies_defaults()
        }
    }

    private fun AppRobot.check_movies_no_filter(): Actions {
        check_sonic_the_hedgehog_movie()
        check_mortal_kombat_legends_movie()
        check_sonic_the_hedgehog_movie()
        return NoActions
    }

    private fun AppRobot.check_movies_year_picker(): Actions {
        check_filter_icon()
        check_year_filter()
        check_year_picker()
        return NoActions
    }

    private fun AppRobot.check_movies_year_filter(): Actions {
        check_underwater_movie()
        check_trolls_world_tour_movie()
        check_underwater_movie()
        return NoActions
    }

    private fun AppRobot.check_movies_month_picker(): Actions {
        check_month_filter()
        check_month_picker()
        return NoActions
    }

    private fun AppRobot.check_movies_month_filter(): Actions {
        check_love_wedding_repeat_movie()
        check_behind_you_movie()
        check_love_wedding_repeat_movie()
        return NoActions
    }

    private fun AppRobot.check_movies_defaults(): Actions {
        check_close_icon()
        check_sonic_the_hedgehog_movie()
        return NoActions
    }

    /* ROBOT TEST CASES */

    private fun AppRobot.check_sonic_the_hedgehog_movie() {
        inMovies {
            inToolbar {
                inLabel { hasText(MOVIES_TOOLBAR_LABEL) }
                inFilterIcon { isShown() }
                inCloseIcon { isNotShown() }
                inYearFilter { isNotShown() }
                inMonthFilter { isNotShown() }
            }
            inList {
                inItem(SONIC_THE_HEDGEHOG_ITEM_POSITION) {
                    inTitle { hasText(SONIC_THE_HEDGEHOG_ITEM_TITLE) }
                    inReleaseDate { hasText(SONIC_THE_HEDGEHOG_ITEM_RELEASE_DATE) }
                    inVote { hasText(SONIC_THE_HEDGEHOG_ITEM_VOTE) }
                    performClick()
                }
            }
        }
        inMovieDetails {
            inToolbar {
                inLabel { hasText(MOVIE_DETAILS_TOOLBAR_LABEL) }
            }
            inScreen {
                inTitle { hasText(SONIC_THE_HEDGEHOG_ITEM_TITLE) }
                inReleaseDate { hasText(SONIC_THE_HEDGEHOG_ITEM_RELEASE_DATE) }
                inVote { hasText(SONIC_THE_HEDGEHOG_ITEM_VOTE) }
                inOverview { hasText(SONIC_THE_HEDGEHOG_ITEM_OVERVIEW) }
            }
        }
        performBack()
    }

    private fun AppRobot.check_mortal_kombat_legends_movie() {
        inMovies {
            inToolbar {
                inLabel { hasText(MOVIES_TOOLBAR_LABEL) }
                inFilterIcon { isShown() }
                inCloseIcon { isNotShown() }
                inYearFilter { isNotShown() }
                inMonthFilter { isNotShown() }
            }
            inList {
                inItem(MORTAL_KOMBAT_LEGENDS_ITEM_POSITION) {
                    inTitle { hasText(MORTAL_KOMBAT_LEGENDS_ITEM_TITLE) }
                    inReleaseDate { hasText(MORTAL_KOMBAT_LEGENDS_ITEM_RELEASE_DATE) }
                    inVote { hasText(MORTAL_KOMBAT_LEGENDS_ITEM_VOTE) }
                    performClick()
                }
            }
        }
        inMovieDetails {
            inToolbar {
                inLabel { hasText(MOVIE_DETAILS_TOOLBAR_LABEL) }
            }
            inScreen {
                inTitle { hasText(MORTAL_KOMBAT_LEGENDS_ITEM_TITLE) }
                inReleaseDate { hasText(MORTAL_KOMBAT_LEGENDS_ITEM_RELEASE_DATE) }
                inVote { hasText(MORTAL_KOMBAT_LEGENDS_ITEM_VOTE) }
                inOverview { hasText(MORTAL_KOMBAT_LEGENDS_ITEM_OVERVIEW) }
            }
        }
        performBack()
    }

    private fun AppRobot.check_filter_icon() {
        inMovies {
            inToolbar {
                inLabel { hasText(MOVIES_TOOLBAR_LABEL) }
                inFilterIcon { isShown() }
                inCloseIcon { isNotShown() }
                inYearFilter { isNotShown() }
                inMonthFilter { isNotShown() }
                inFilterIcon { performClick() }
            }
        }
    }

    private fun AppRobot.check_year_filter() {
        inMovies {
            inToolbar {
                inLabel { hasText(MOVIES_TOOLBAR_LABEL) }
                inFilterIcon { isNotShown() }
                inCloseIcon { isShown() }
                inYearFilter { hasText(MOVIES_TOOLBAR_FILTER_YEAR) }
                inMonthFilter { isNotShown() }
                inYearFilter { performClick() }
            }
        }
    }

    private fun AppRobot.check_year_picker() {
        inMovies {
            inYearPicker {
                inTitle { hasText(MOVIE_YEAR_PICKER_TITLE) }
                inYear { hasTextInside(MOVIE_YEAR_PICKER_YEAR_2020) }
                onActionOk { performClick() }
            }
        }
    }

    private fun AppRobot.check_underwater_movie() {
        inMovies {
            inToolbar {
                inLabel { hasText(MOVIES_TOOLBAR_LABEL) }
                inFilterIcon { isNotShown() }
                inCloseIcon { isShown() }
                inYearFilter { hasText(MOVIES_TOOLBAR_FILTER_YEAR_2020) }
                inMonthFilter { hasText(MOVIES_TOOLBAR_FILTER_MONTH) }
            }
            inList {
                inItem(UNDERWATER_ITEM_POSITION) {
                    inTitle { hasText(UNDERWATER_ITEM_TITLE) }
                    inReleaseDate { hasTextIgnoreMultipleViews(UNDERWATER_ITEM_RELEASE_DATE) }
                    inVote { hasText(UNDERWATER_ITEM_VOTE) }
                    performClick()
                }
            }
        }
        inMovieDetails {
            inToolbar {
                inLabel { hasText(MOVIE_DETAILS_TOOLBAR_LABEL) }
            }
            inScreen {
                inTitle { hasText(UNDERWATER_ITEM_TITLE) }
                inReleaseDate { hasText(UNDERWATER_ITEM_RELEASE_DATE) }
                inVote { hasText(UNDERWATER_ITEM_VOTE) }
                inOverview { hasText(UNDERWATER_ITEM_OVERVIEW) }
            }
        }
        performBack()
    }

    private fun AppRobot.check_trolls_world_tour_movie() {
        inMovies {
            inToolbar {
                inLabel { hasText(MOVIES_TOOLBAR_LABEL) }
                inFilterIcon { isNotShown() }
                inCloseIcon { isShown() }
                inYearFilter { hasText(MOVIES_TOOLBAR_FILTER_YEAR_2020) }
                inMonthFilter { hasText(MOVIES_TOOLBAR_FILTER_MONTH) }
            }
            inList {
                inItem(TROLLS_WORLD_TOUR_ITEM_POSITION) {
                    inTitle { hasText(TROLLS_WORLD_TOUR_ITEM_TITLE) }
                    inReleaseDate { hasText(TROLLS_WORLD_TOUR_ITEM_RELEASE_DATE) }
                    inVote { hasText(TROLLS_WORLD_TOUR_ITEM_VOTE) }
                    performClick()
                }
            }
        }
        inMovieDetails {
            inToolbar {
                inLabel { hasText(MOVIE_DETAILS_TOOLBAR_LABEL) }
            }
            inScreen {
                inTitle { hasText(TROLLS_WORLD_TOUR_ITEM_TITLE) }
                inReleaseDate { hasText(TROLLS_WORLD_TOUR_ITEM_RELEASE_DATE) }
                inVote { hasText(TROLLS_WORLD_TOUR_ITEM_VOTE) }
                inOverview { hasText(TROLLS_WORLD_TOUR_ITEM_OVERVIEW) }
            }
        }
        performBack()
    }

    private fun AppRobot.check_month_filter() {
        inMovies {
            inToolbar {
                inLabel { hasText(MOVIES_TOOLBAR_LABEL) }
                inFilterIcon { isNotShown() }
                inCloseIcon { isShown() }
                inYearFilter { hasText(MOVIES_TOOLBAR_FILTER_YEAR_2020) }
                inMonthFilter {
                    hasText(MOVIES_TOOLBAR_FILTER_MONTH)
                    performClick()
                }
            }
        }
    }

    private fun AppRobot.check_month_picker() {
        inMovies {
            inMonthPicker {
                inTitle { hasText(MOVIE_MONTH_PICKER_TITLE) }
                inMonth { hasTextInside(MOVIE_MONTH_PICKER_MONTH_MAY) }
                onActionOk { performClick() }
            }
        }
    }

    private fun AppRobot.check_love_wedding_repeat_movie() {
        inMovies {
            inToolbar {
                inLabel { hasText(MOVIES_TOOLBAR_LABEL) }
                inFilterIcon { isNotShown() }
                inCloseIcon { isShown() }
                inYearFilter { hasText(MOVIES_TOOLBAR_FILTER_YEAR_2020) }
                inMonthFilter { hasText(MOVIES_TOOLBAR_FILTER_MONTH_MAY) }
            }
            inList {
                inItem(LOVE_WEDDING_REPEAT_ITEM_POSITION) {
                    inTitle { hasText(LOVE_WEDDING_REPEAT_ITEM_TITLE) }
                    inReleaseDate { hasTextIgnoreMultipleViews(LOVE_WEDDING_REPEAT_ITEM_RELEASE_DATE) }
                    inVote { hasText(LOVE_WEDDING_REPEAT_ITEM_VOTE) }
                    performClick()
                }
            }
        }
        inMovieDetails {
            inToolbar {
                inLabel { hasText(MOVIE_DETAILS_TOOLBAR_LABEL) }
            }
            inScreen {
                inTitle { hasText(LOVE_WEDDING_REPEAT_ITEM_TITLE) }
                inReleaseDate { hasText(LOVE_WEDDING_REPEAT_ITEM_RELEASE_DATE) }
                inVote { hasText(LOVE_WEDDING_REPEAT_ITEM_VOTE) }
                inOverview { hasText(LOVE_WEDDING_REPEAT_ITEM_OVERVIEW) }
            }
        }
        performBack()
    }

    private fun AppRobot.check_behind_you_movie() {
        inMovies {
            inToolbar {
                inLabel { hasText(MOVIES_TOOLBAR_LABEL) }
                inFilterIcon { isNotShown() }
                inCloseIcon { isShown() }
                inYearFilter { hasText(MOVIES_TOOLBAR_FILTER_YEAR_2020) }
                inMonthFilter { hasText(MOVIES_TOOLBAR_FILTER_MONTH_MAY) }
            }
            inList {
                inItem(BEHIND_YOU_ITEM_POSITION) {
                    inTitle { hasText(BEHIND_YOU_ITEM_TITLE) }
                    inReleaseDate { hasTextIgnoreMultipleViews(BEHIND_YOU_ITEM_RELEASE_DATE) }
                    inVote { hasText(BEHIND_YOU_ITEM_VOTE) }
                    performClick()
                }
            }
        }
        inMovieDetails {
            inToolbar {
                inLabel { hasText(MOVIE_DETAILS_TOOLBAR_LABEL) }
            }
            inScreen {
                inTitle { hasText(BEHIND_YOU_ITEM_TITLE) }
                inReleaseDate { hasText(BEHIND_YOU_ITEM_RELEASE_DATE) }
                inVote { hasText(BEHIND_YOU_ITEM_VOTE) }
                inOverview { hasText(BEHIND_YOU_ITEM_OVERVIEW) }
            }
        }
        performBack()
    }

    private fun AppRobot.check_close_icon() {
        inMovies {
            inToolbar {
                inLabel { hasText(MOVIES_TOOLBAR_LABEL) }
                inFilterIcon { isNotShown() }
                inCloseIcon { isShown() }
                inYearFilter { hasText(MOVIES_TOOLBAR_FILTER_YEAR_2020) }
                inMonthFilter { hasText(MOVIES_TOOLBAR_FILTER_MONTH_MAY) }
                inCloseIcon { performClick() }
            }
        }
    }

}
