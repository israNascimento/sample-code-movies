@file:Suppress("InvalidPackageDeclaration")

import io.petros.movies.config.deps.Deps
import io.petros.movies.config.deps.Projects
import io.petros.movies.config.deps.identifier

plugins {
    id(Plugins.Id.Android.LIBRARY)
    id(Plugins.Id.Kotlin.Android.ANDROID)
    id(Plugins.Id.Quality.DETEKT)
    id(Plugins.Id.Dependency.VERSIONS)
}

android {
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(Projects.Implementation.Android.Core.ANDROID_UTILS))

    implementation(Deps.Material.MATERIAL)
    implementation(Deps.Android.Core.APP_COMPAT)
    implementation(Deps.Android.Core.FRAGMENT)
    implementation(Deps.Android.Arch.Lifecycle.COMMON)
    implementation(Deps.Android.Arch.Lifecycle.COMMON_JAVA_8)
    implementation(Deps.Android.Arch.Lifecycle.LIVE_DATA_CORE)
    implementation(Deps.Android.Arch.Lifecycle.VIEW_MODEL)
    implementation(Deps.Image.Glide.GLIDE)
    implementation(Deps.Log.TIMBER)

    detektPlugins(Plugins.DETEKT_FORMATTING)
}

dependencyAnalysis {
    issues {
        onIncorrectConfiguration {
            exclude(
                Projects.Implementation.Android.Core.ANDROID_UTILS, // Ignore change to 'api' advice.
                Deps.Material.MATERIAL.identifier(), // Ignore change to 'api' advice.
                Deps.Android.Core.APP_COMPAT.identifier(), // Ignore change to 'api' advice.
                Deps.Android.Core.FRAGMENT.identifier(), // Ignore change to 'api' advice.
                Deps.Android.Arch.Lifecycle.LIVE_DATA_CORE.identifier(), // Ignore change to 'api' advice.
                Deps.Android.Arch.Lifecycle.VIEW_MODEL.identifier(), // Ignore change to 'api' advice.
            )
        }
    }
}
