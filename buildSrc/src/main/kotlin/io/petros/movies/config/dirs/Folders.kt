package io.petros.movies.config.dirs

object Folders {

    @Suppress("MemberNameEqualsClassName")
    object Config {

        const val CONFIG = "config"

        object Subfolder {

            const val QUALITY = "quality"

        }

    }

    object Source {

        const val SRC = "src"

        const val MAIN = "main"
        const val TEST = "test"
        const val INTEGRATION_TEST = "integrationTest"
        const val ROBOLECTRIC_TEST = "robolectricTest"
        const val ANDROID_TEST = "androidTest"

        object Subfolder {

            const val KOTLIN = "kotlin"
            const val RESOURCES = "resources"

        }

    }

}
