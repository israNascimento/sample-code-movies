package io.petros.movies.config.deps

@Suppress("StringLiteralDuplication")
object Versions {

    // DEBUG IMPLEMENTATION // *****************************************************************************************

    object LeakCanary {

        // Releases: https://github.com/square/leakcanary/releases
        const val LEAK_CANARY = "2.7" // Released: 26-03-21

    }

    // IMPLEMENTATION // ***********************************************************************************************

    object Kotlin {

        // Releases: https://github.com/Kotlin/kotlinx.coroutines/releases
        const val COROUTINES = "1.5.0" // Released: 14-05-21

    }

    @Suppress("MemberNameEqualsClassName")
    object Material {

        // Releases: https://github.com/material-components/material-components-android/releases
        @Suppress("unused") const val MATERIAL = "1.3.0" // Released: 04-02-21
        const val MATERIAL_RC = "1.4.0-rc01" // Released: 03-06-21

    }

    object Android {

        object Core {

            // Releases: https://developer.android.com/jetpack/androidx/releases/appcompat
            const val APP_COMPAT = "1.3.0" // Released: 18-05-20

            // Releases: https://developer.android.com/jetpack/androidx/releases/fragment
            const val FRAGMENT = "1.3.4" // Released: 18-05-21

            // Releases: https://developer.android.com/jetpack/androidx/releases/recyclerview
            const val RECYCLER_VIEW = "1.2.1" // Released: 02-06-21

            // Releases: https://developer.android.com/jetpack/androidx/releases/constraintlayout
            @Suppress("unused") const val CONSTRAINT_LAYOUT = "2.0.4" // Released: 29-10-20
            const val CONSTRAINT_LAYOUT_BETA = "2.1.0-beta02" // Released: 04-05-21

            // Releases: https://developer.android.com/jetpack/androidx/releases/drawerlayout
            const val DRAWER_LAYOUT = "1.1.1" // Released: 02-09-20

            // Releases: https://developer.android.com/jetpack/androidx/releases/coordinatorlayout
            const val COORDINATOR_LAYOUT = "1.1.0" // Released: 04-12-19

        }

        object Ktx {

            // Releases: https://developer.android.com/jetpack/androidx/releases/core
            @Suppress("unused") const val CORE = "1.5.0" // Released: 18-05-20
            const val CORE_BETA = "1.6.0-beta02" // Released: 02-06-21

        }

        @Suppress("MemberNameEqualsClassName")
        object Compose {

            // Releases: https://developer.android.com/jetpack/androidx/releases/compose
            const val COMPOSE = "1.0.0-beta08" // Released: 02-06-21

            object ConstraintLayout {

                // Releases: https://developer.android.com/jetpack/androidx/releases/constraintlayout
                const val CONSTRAINT_LAYOUT = "1.0.0-alpha07" // Released: 18-05-21

            }

            object Accompanist {

                // Releases: https://github.com/chrisbanes/accompanist/releases
                const val ACCOMPANIST = "0.11.1" // Released: 02-06-21

            }

        }

        object Arch {

            @Suppress("MemberNameEqualsClassName")
            object Core {

                // Releases: https://developer.android.com/jetpack/androidx/releases/arch-core
                const val CORE = "2.1.0" // Released: 05-09-19

            }

            @Suppress("MemberNameEqualsClassName")
            object Lifecycle {

                // Releases: https://developer.android.com/jetpack/androidx/releases/lifecycle
                const val LIFECYCLE = "2.3.1" // Released: 24-03-20

            }

            @Suppress("MemberNameEqualsClassName")
            object SavedState {

                // Releases: https://developer.android.com/jetpack/androidx/releases/savedstate
                const val SAVED_STATE = "1.1.0" // Released: 10-02-20

            }

            @Suppress("MemberNameEqualsClassName")
            object Navigation {

                // Releases: https://developer.android.com/jetpack/androidx/releases/navigation
                const val NAVIGATION = "2.3.5" // Released: 07-04-21

            }

            object CustomView {

                // Releases: https://developer.android.com/jetpack/androidx/releases/customview
                const val CUSTOM_VIEW = "1.1.0" // Release: 24-06-20

            }

            @Suppress("MemberNameEqualsClassName")
            object DataStore {

                // Releases: https://developer.android.com/jetpack/androidx/releases/datastore
                const val DATASTORE = "1.0.0-beta01" // Release: 21-04-21

            }

            object Database {

                @Suppress("MemberNameEqualsClassName")
                object SQLight {

                    // Releases: https://developer.android.com/jetpack/androidx/releases/sqlite
                    const val SQLIGHT = "2.1.0" // Release: 22-01-20

                }

                @Suppress("MemberNameEqualsClassName")
                object Room {

                    // Releases: https://developer.android.com/jetpack/androidx/releases/room
                    const val ROOM = "2.3.0" // Release: 21-04-21

                }

            }

            @Suppress("MemberNameEqualsClassName")
            object Pagination {

                // Releases: https://developer.android.com/jetpack/androidx/releases/paging
                const val PAGING = "3.0.0" // Release: 05-05-21

            }

        }

        object Test {

            // Releases: https://developer.android.com/jetpack/androidx/releases/test
            @Suppress("unused") const val CORE = "1.3.0" // Released: 25-08-20
            const val CORE_BETA = "1.4.0-beta01" // Released: 17-05-21
            @Suppress("unused") const val ESPRESSO = "3.3.0" // Released: 25-08-20
            const val ESPRESSO_BETA = "3.4.0-beta01" // Released: 17-05-21
            @Suppress("unused") const val J_UNIT = "1.1.2" // Released: 25-08-20
            const val J_UNIT_BETA = "1.1.3-beta01" // Released: 17-05-21

            // Releases: https://github.com/robolectric/robolectric/releases
            const val ROBOLECTRIC = "4.5.1" // Released: 01-02-21

        }

    }

    object Di {

        @Suppress("MemberNameEqualsClassName")
        object Koin {

            // Releases: https://github.com/InsertKoinIO/koin/releases
            const val KOIN_BETA = "3.0.2" // Released: 18-05-21

        }

    }

    object Net {

        object OkHttp {

            // Releases: https://github.com/square/okhttp/releases
            const val OK_HTTP = "5.0.0-alpha.2" // Released: 30-01-21

        }

        @Suppress("MemberNameEqualsClassName")
        object Gson {

            // Releases: https://github.com/google/gson/releases
            const val GSON = "2.8.7" // Released: 25-05-21

        }

        object Rest {

            // Releases: https://github.com/square/retrofit/releases
            const val RETROFIT = "2.9.0" // Released: 20-05-20

        }

    }

    object Image {

        @Suppress("MemberNameEqualsClassName")
        object Glide {

            // Releases: https://github.com/bumptech/glide/releases
            const val GLIDE = "4.12.0" // Released: 29-01-21

        }

    }

    object Log {

        // Releases: https://github.com/JakeWharton/timber/releases
        const val TIMBER = "4.7.1" // Released: 28-06-18

    }

    // TEST IMPLEMENTATION // ******************************************************************************************

    object Test {

        object JUnit {

            // Releases: https://github.com/junit-team/junit4/releases
            const val J_UNIT_4 = "4.13.2" // Released: 13-02-21

        }

        @Suppress("MemberNameEqualsClassName")
        object Hamcrest {

            // Releases: https://github.com/hamcrest/JavaHamcrest/releases
            const val HAMCREST = "1.3" // Released: 10-06-12

        }

        object Assert {

            // Releases: https://github.com/robfletcher/strikt/releases
            const val STRIKT = "0.31.0" // Released: 25-04-21

        }

        object Mock {

            // Releases: https://github.com/mockk/mockk/releases
            const val MOCK_K = "1.11.0" // Released: 17-03-21

        }

    }

}
