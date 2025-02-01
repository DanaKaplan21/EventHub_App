package com.example.eventhub.network;

import com.example.eventhub.models.Event;
import com.example.eventhub.models.Guest;
import com.example.eventhub.models.Reminder;
import com.example.eventhub.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;
import java.util.Map;

public interface ApiService {

    @GET("/api/users")
    Call<List<User>> getUsers();

    @POST("/api/users")
    Call<Void> createUser(@Body User user);

    @PUT("/api/users/{email}")
    Call<Void> updateUser(@Path("email") String email, @Body User user);

    @DELETE("/api/users/{email}")
    Call<Void> deleteUser(@Path("email") String email);

    @GET("/api/events")
    Call<List<Event>> getEvents();

    @GET("/api/events/{event_Id}")
    Call<Event> getEvent(@Path("event_Id") String eventId);


    @POST("/api/events")
    Call<Void> createEvent(@Body Event event);

    @PUT("/api/events/{event_Id}")
    Call<Void> updateEvent(@Path("event_Id") String eventId, @Body Event event);

    @DELETE("/api/events/{eventId}")
    Call<Void> deleteEvent(@Path("eventId") String eventId);

    @GET("/api/guests/{event_Id}")
    Call<List<Guest>> getGuestsByEvent(@Path("event_Id") String eventId);

    @POST("/api/guests/{event_Id}")
    Call<Void> addGuest(@Body Guest guest);

    @PUT("/api/guests/{event_id}/{email}")
    Call<Void> updateGuestStatus(
            @Path("event_id") String eventId,
            @Path("email") String email,
            @Body Map<String, String> statusMap
    );




    @DELETE("/api/guests/{guestId}")
    Call<Void> deleteGuest(@Path("guestId") String guestId);

    @GET("/api/reminders/{eventId}")
    Call<List<Reminder>> getRemindersByEvent(@Path("eventId") String eventId);

    @POST("/api/reminders")
    Call<Void> createReminder(@Body Reminder reminder);

    @PUT("/api/reminders/{reminderId}")
    Call<Void> updateReminder(@Path("reminderId") String reminderId, @Body Reminder reminder);

    @DELETE("/api/reminders/{reminderId}")
    Call<Void> deleteReminder(@Path("reminderId") String reminderId);

    @GET("/")
    Call<String> checkApiConnection();

}
