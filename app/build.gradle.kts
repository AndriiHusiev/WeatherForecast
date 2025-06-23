plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.kotlin.compose)
	alias(libs.plugins.devtools.ksp)
	alias(libs.plugins.dagger.hilt)
	alias(libs.plugins.kotlin.serialization)
}

android {
	namespace = "com.husiev.weather.forecast"
	compileSdk = 35
	
	defaultConfig {
		applicationId = "com.husiev.weather.forecast"
		minSdk = 24
		targetSdk = 35
		versionCode = 8
		versionName = "0.6.1"
		
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}
	
	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	kotlinOptions {
		jvmTarget = "11"
	}
	buildFeatures {
		compose = true
	}
}

dependencies {
	
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	implementation(libs.androidx.activity.compose)
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.ui)
	implementation(libs.androidx.ui.graphics)
	implementation(libs.androidx.ui.tooling.preview)
	implementation(libs.androidx.material3)
	implementation(libs.androidx.work.runtime.ktx)
	implementation(libs.androidx.material.icons.core)
	implementation(libs.androidx.material.icons.core.android)
	implementation(libs.androidx.material.icons.extended.android)
	implementation(libs.androidx.material.icons.extended)
	implementation(libs.androidx.navigation.compose)
	// Hilt
	implementation(libs.hilt.android)
	implementation(libs.androidx.hilt.navigation.compose)
	implementation(libs.androidx.hilt.work)
	implementation(libs.gson)
	ksp (libs.hilt.compiler)
	ksp (libs.androidx.hilt.compiler)
	// Retrofit
	implementation(libs.retrofit)
	implementation(libs.retrofit2.kotlinx.serialization.converter)
	implementation(libs.okhttp)
	implementation(libs.kotlinx.serialization.json)
	implementation(libs.coil.compose)
	// Room
	implementation(libs.androidx.room.runtime)
	implementation(libs.androidx.room.ktx)
	annotationProcessor(libs.androidx.room.compiler)
	ksp(libs.androidx.room.compiler)
	
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.androidx.ui.test.junit4)
	debugImplementation(libs.androidx.ui.tooling)
	debugImplementation(libs.androidx.ui.test.manifest)
	
}