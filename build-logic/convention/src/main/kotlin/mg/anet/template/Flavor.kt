package mg.anet.template

import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor
import org.gradle.api.Project
import org.gradle.kotlin.dsl.support.uppercaseFirstChar

@Suppress("EnumEntryName")
enum class FlavorDimension {
    type,
    device
}

@Suppress("EnumEntryName")
enum class Flavor(
    val dimension: FlavorDimension,
    val hasVersionNameSuffix: Boolean = false
) {
    prod(FlavorDimension.type),
    demo(FlavorDimension.type),

    apollo(FlavorDimension.device),
    nexgo(FlavorDimension.device),
    pax(FlavorDimension.device),
    avd(FlavorDimension.device),
}


fun Project.configureFlavors(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    flavorConfigurationBlock: ProductFlavor.(flavor: Flavor) -> Unit = {}
) {
    commonExtension.apply {
        flavorDimensions += FlavorDimension.entries.map { it.name }
        productFlavors {
            Flavor.entries.forEach { flavor ->
                create(flavor.name) {
                    dimension = flavor.dimension.name

                    flavorConfigurationBlock(this, flavor)
                    if (this is ApplicationProductFlavor) {
                        if (flavor.hasVersionNameSuffix) {
                            versionNameSuffix = "-${flavor.dimension.name.uppercaseFirstChar()}"
                        }
                    }
                }
            }
        }
    }
}