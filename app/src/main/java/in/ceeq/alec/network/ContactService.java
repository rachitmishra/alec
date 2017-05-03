package in.ceeq.alec.network;

import java.util.List;

import in.ceeq.alec.databinding.Contact;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ContactService {

    @GET("users")
    Call<List<Contact>> getContacts();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("users/{id}")
    Call<Contact> getContact(@Path("id") int id);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("users")
    Call<Contact> postContact(@Body Contact contact);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @PUT("users/{id}")
    Call<Contact> putContact(@Body Contact contact, @Path("id") int id);
}
