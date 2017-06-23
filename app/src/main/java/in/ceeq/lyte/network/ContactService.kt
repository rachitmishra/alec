package `in`.ceeq.lyte.network

import `in`.ceeq.lyte.databinding.Contact
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ContactService {

    @get:GET("users")
    val contacts: Call<List<Contact>>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @GET("users/{id}")
    fun getContact(@Path("id") id: Int): Call<Contact>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("users")
    fun postContact(@Body contact: Contact): Call<Contact>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @PUT("users/{id}")
    fun putContact(@Body contact: Contact, @Path("id") id: Int): Call<Contact>
}
