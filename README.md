# StatusApplication

This project is developed as a Status Application wherein a user can view the status fetched from an Api.

## Detailed Specification

1. When the app is launched it should fetch all the statuses from:
GET http://private-176645-utilita.apiary-mock.com/status
2. Display the list of statuses in a view.
3. Clicking a status should show the information about the status in a new screen.
4. There are error views for server and internet unavailable errors.

## Getting Started

```
git clone https://github.com/NamrataBagerwal/StatusApplication.git
```

Or the same can be imported directly in Android Studio via VCS -> Git -> Clone.

## Built With
1. Android Studio 4.0.1
2. Android Kotlin 1.4.10
4. Retrofit for Networking Calls
5. Koin for Dependency Injection
6. RxJava for Thread Management
7. Variety of Test Libraries: Mockito, JUnit, Robolectric, Koin-Test, AndroidX and their applicable respective Kotlin extensions

## Prerequisite
Device with Android version 6 (v23), Android Studio 4.0.1, Android Kotlin 1.4.10

## Installing
Wait for the project to get build successfully then run the app module on device/emulator.

HomePage with APIs_DBs as Default Tab will be loaded:
![HomePage with APIs_DBs](screenshots/screenshot_apis_dbs.png)

Sites Tab:
![Sites Tab](screenshots/screenshot_sites.png)

Detail Screen:
![Detail Screen](screenshots/screenshot_detail_screen.png)

Sites Tab Landscape:
![Sites Tab Landscape](screenshots/screenshot_sites_landscape.png)

Detail Screen Landscape:
![Detail Screen Landscape](screenshots/screenshot_detail_screen_landscape.png)

## Running the tests
TestCases are coming in next commit.

## Known Issues
1. RxJava Unsubscription to avoid making unnecessary Retrofit Api calls via Interceptor
while rotating the Application as well as while returning from Detail Screen to Home Page.

## Future Enhancements
1. Revamping Data model class for StatusApiResponse so as to get "APIs & DBs", "Sites" as dynamic keys and prepare Tab section with any value coming from Api.
2. Resolving RxJava Unsubscription issue
2. Code Optimization and adding Comments.
3. Coverage of more unit test cases and applicable instrumentation test cases

## Authors
Namrata Bagerwal

## License
This project is open source.
