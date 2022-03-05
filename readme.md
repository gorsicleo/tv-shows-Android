# Infinum academy Android - TV Shows app -

### Brief project overview 

For learning purposes on Infinum android academy (ISA) I created an application that allows you to view TV shows, comment and rate them. Application was natively  made for Android devices (I have now iOS version as well) in Kotlin language.


### Installation

Clone repository and build APK file using Android studio or similar IDE. Future plan is to make release with pre-built APK file that will be ready for installation.

### Application screenshots
<img src="https://github.com/gorsicleo/tv-shows-Android/blob/master/Screenshots/1.jpg?raw=true" width="300" height="600">
<img src="https://github.com/gorsicleo/tv-shows-Android/blob/master/Screenshots/2.jpg?raw=true" width="300" height="600">
<img src="https://github.com/gorsicleo/tv-shows-Android/blob/master/Screenshots/3.jpg?raw=true" width="300" height="600">
<img src="https://github.com/gorsicleo/tv-shows-Android/blob/master/Screenshots/4.jpg?raw=true" width="300" height="600">
<img src="https://github.com/gorsicleo/tv-shows-Android/blob/master/Screenshots/5.jpg?raw=true" width="300" height="600">
<img src="https://github.com/gorsicleo/tv-shows-Android/blob/master/Screenshots/6.jpg?raw=true" width="300" height="600">
<img src="https://github.com/gorsicleo/tv-shows-Android/blob/master/Screenshots/7.jpg?raw=true" width="300" height="600">
<img src="https://github.com/gorsicleo/tv-shows-Android/blob/master/Screenshots/8.jpg?raw=true" width="300" height="600">

### What I have learned and technologies I have used

Through the process of creating an application I learned lot about android operating system and how app conforms to OS requirements (app lifecycle). In that manner I learned about Activities, intents and views.  Displaying shows list required knowledge of "recycler view". The big improvement to the app was "single activity design principle" that forced me to get comfortable with Android's Jetpack Navigation. There is also a feature of adding profile photo and throughout implementation of this I scratched the surface of using device resources and handling permissions. Since app heavily relied on backend for networking framework I used "Retrofit" and created singleton for intercepting API calls to inject authentication data in headers (tokens and UID). For offline use of application I used Google's ROOM database to store offline created reviews. Finally I learned a bit about custom views and animations when designing custom "Splash (welcome) screen". 
