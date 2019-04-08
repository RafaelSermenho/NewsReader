
# News Reader Android App  
  
This news reader app made with *Kotlin* has the following features:  
  
 - Main Screen: List all sources for every category that is available for english language;  
 - Article Screen: Shows all articles from a previously selected source (The pagination is loading 20 items each time).  
  
## Architecture  
This app uses *MVVM* (**M**odel-**V**iew-**V**iew**M**odel).   
Why? I must confess that I not experienced with MVVM pattern and that's why I decided to try it.  
The main advantage that I could note is that is much easier an intuitive to create classes with single responsibilities. 

Another great thing that I learned was the use of LiveData to update the UI with results from data server (I wish that I had the knowledge ~~and opportunity~~ to use it in my previous projects).

Some interesting characteristics about MVVM pattern implemented here:
- The ViewModel doesn't contains any reference to activities, fragments or views and this avoid leaks due to screen rotation.
- The ViewModel exposes data using LiveData.
- The views are subscribing to the ViewModel LiveData objects, that are lifecycle-aware components. This is great because the data changes are not applied if the observer is not active. (Avoiding null pointers, invalid state exception, etc).

## DI  
For dependency injection I'm using Koin (https://github.com/InsertKoinIO/koin). Why? Is written in Kotlin and it's very simple to start using it. The learning curve is low and it's not complex as Dagger.  
  
## Packages  
### di  
Stores the module file for dependency injection using Koin.
  
### model  
Stores the POJO files to be serialized with JSON from server.
  
### repository  
Stores all classes responsible to load data (only from rest server in my case). In this package I'm storing all retrofit initialization and service implementation.  
  
### view  
Stores all visual components: Activities, fragments, components, listeners, adapters, etc.  
  
### viewmodel  
Stores all classes responsible for communication between views and models.  
 
## 3rd Party Libraries  
This project uses some 3rd party libraries as follows:   
- constraint-layout:1.1.3: Android component to make it possible to use constraint layouts  
- lifecycle:extensions:1.1.1: Part of Android Architecture Components to use ViewModel and LiveData  
- retrofit:2.5.0: Used to make rest calls to server  
- converter-gson:2.3.0: Used to convert JSON to an object  
- logging-interceptor:3.8.0: OkHTTP log package used on retrofit  
- koin-android:1.0.2: Used to make Dependency Injection with Kotlin  
- koin-android-viewmodel:1.0.2: Used to inject ViewModels with Kotlin  
- cardview-v7:28.0.0: Added for cardview support  
- recyclerview-v7:28.0.0: Added for recyclerview support  
- glide:4.9.0: Used to load and cache images in android  
- mockk:1.9: Added to support Kotlin mocks  
- mockwebserver:3.14.0: Added to create mockwebservers to test API services  

### Icons
All custom icons are made by [Freepik](http://www.freepik.com/ "Freepik") from [www.flaticon.com](http://www.flaticon.com/ "Flaticon")
  
## What could be improved?  
For the future, I'd like to work on cache service using the Single Source of Truth pattern, possibility to change default language, enable multi-source selection and queries based on keywords.  
