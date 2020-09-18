[![API](https://img.shields.io/badge/API-23%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=23)

# MagicBox
A Demo project for [The Movie DB](https://developers.themoviedb.org/3/getting-started/introduction) based on Kotlin MVVM clean architecture and material design & animations. <br>
<img src="https://github.com/Andreu35/MagicBox/blob/develop/images/01.png" width="200" height="400" />
<img src="https://github.com/Andreu35/MagicBox/blob/develop/images/02.png" width="200" height="400" />
<img src="https://github.com/Andreu35/MagicBox/blob/develop/images/03.png" width="200" height="400" />
<img src="https://github.com/Andreu35/MagicBox/blob/develop/images/04.png" width="200" height="400" />
<img src="https://github.com/Andreu35/MagicBox/blob/develop/images/05.png" width="200" height="400" />
<img src="https://github.com/Andreu35/MagicBox/blob/develop/images/06.png" width="200" height="400" />
<img src="https://github.com/Andreu35/MagicBox/blob/develop/images/07.png" width="200" height="400" />
<img src="https://github.com/Andreu35/MagicBox/blob/develop/images/08.png" width="200" height="400" />
## How to build:
Add your API key in strings.xml file.
```xml
api_key=YOUR_API_KEY
```

## Development process
Based on Test-driven development and short time.<br>
1. API Service
2. Room Database
2. Room Database DAO
3. Repository
4. ViewModel
5. Hilt Dependency Injection & Refactoring ( aplha version )
6. Implementation of UI & Layouts
7. Adding animations <br><br>

## Architecture
Based on MVVM architecture and repository pattern.<br><br>
<img src="https://github.com/Andreu35/MagicBox/blob/develop/images/MVVM.png" width="400" height="400" />

## Specs & Open-source libraries
- [x] Minimum SDK 23
- [x] 100% Kotlin based
- [x] MVVM Architecture
- [x] Architecture Components (Lifecycle, LiveData, ViewModel, Room Persistence)
- [x] ViewBinding
- [ ] DataBinding
- [x] Material Design & Animations
- [x] The Movie DB API
- [x] [Google Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency injection
- [x] [Retrofit2 & Gson](https://github.com/square/retrofit) for constructing the REST API
- [x] [OkHttp3](https://github.com/square/okhttp) for implementing interceptor, logging and mocking web server
- [x] [Glide](https://github.com/bumptech/glide) for loading images
- [x] [Timber](https://github.com/JakeWharton/timber) for logging
- [x] Shared element transition
- [x] Custom Views [ExactRatingBar](https://github.com/Jamshid-M/ExactRatingBar)
