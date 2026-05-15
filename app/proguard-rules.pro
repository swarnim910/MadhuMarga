# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.

# Keep Room entities
-keep class com.madhumarga.app.data.database.entities.** { *; }

# Keep Room DAOs
-keep class com.madhumarga.app.data.database.dao.** { *; }
