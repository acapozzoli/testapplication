package ariel.capozzoli.testapplication.restApis

import ariel.capozzoli.testapplication.model.Album
import ariel.capozzoli.testapplication.model.Photo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumsApi {
    @GET("/albums")
    fun getAlbums(): Observable<List<Album>>

    @GET("/photos")
    fun getPhotos(@Query("albumId") albumId: String): Observable<List<Photo>>
}