Before you build the app, do not forget to open app/src/main/res/values/strings.xml and change the api_key value from "HERE_YOUR_FLICKR_API_KEY" to the value of a real api key without spaces.

-Overall architecture

We have an adapter package with the paginator view (a custom view) and the photo adapter for the recyclerview.

In the model package we make use of parcelable data classes and liveData for the models.

The network package has the Image provider that uses Picasso for image downloading and PhotoProviderService which uses Retrofit.

We make use of a ViewModel containing the liveData and delegating to it the backend call.

We use 2 Androidx's Activities for the purpose of the demo.
