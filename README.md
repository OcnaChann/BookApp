# Book Finder App

## Overview

The **Book App** is an Android application built using Kotlin that allows users to search for books using the **Google Books API**. The app provides features such as category navigation, viewing book details, and saving favorites.

## Features

- Search for books using keywords
- Display book details including title, author, and cover image
- Category-based book browsing
- Favorite management (save and remove favorite books)
- Dark mode support
- Error handling for API requests

## Technologies Used

- **Kotlin** (Primary language)
- **Android Jetpack Components** (ViewModel, LiveData, Navigation, Room)
- **Retrofit** (API calls)
- **Glide** (Image loading)
- **SQLite** (Local storage for favorites)
- **ViewPager2 & TabLayout** (Category navigation)

## Setup Instructions

### Prerequisites

Make sure you have the following installed:

- Android Studio (
- Kotlin SDK
- An active internet connection

### Installation Steps

1. **Clone the repository:**

   ```sh
   git clone https://github.com/OcnaChann/BookApp
   cd book-app
   ```

2. **Open in Android Studio:**

   - Open Android Studio.
   - Click on `Open an existing project` and select the cloned folder.
   - Let Gradle sync the dependencies.

3. **Run the project:**

   - Connect an emulator or an Android device.
   - Click the **Run** button in Android Studio.

## Dependencies

Ensure the following dependencies are included in `build.gradle (Module: app)`:

```gradle
dependencies {
    implementation 'androidx.core:core-ktx:1.7.0'
    implementation 'androidx.appcompat:appcompat:1.4.1'
    implementation 'com.google.android.material:material:1.5.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'

    // Jetpack Compose
    implementation'androidx.compose.ui:ui:1.5.0'
    implementation 'com.google.android.material:material:1.6.0'
    implementation 'androidx.activity:activity-ktx:1.8.0'
    implementation 'androidx.fragment:fragment-ktx:1.6.1'

    // ViewModel & LiveData
    implementation'androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2'
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1'
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.6.2'
    // Retrofit for API calls
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

    // Room Database for storing favorite books
    implementation 'androidx.room:room-runtime:2.6.1'
    kapt 'androidx.room:room-compiler:2.6.1'
    // Room KTX for coroutines
    implementation androidx.room:room-ktx:2.6.1'
    // Coroutines for asynchronous database operations
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4'
    //glide for image process
    implementation 'com.github.bumptech.glide:glide:4.14.2'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.14.2'
}
```

## Contributing

1. Fork the repository
2. Create a new branch (`git checkout -b feature-branch`)
3. Commit your changes (`git commit -m 'Add new feature'`)
4. Push to the branch (`git push origin feature-branch`)
5. Open a Pull Request

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

## Contact

For any queries or suggestions, contact: sabinasherchan10@gmail.com

