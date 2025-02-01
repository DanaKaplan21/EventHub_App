# EventHub Android Library Documentation

## Overview
The EventHub Android Library simplifies interaction with the EventHub API. It allows developers to integrate event creation, guest management, and event listing seamlessly into their applications.

## Installation
To use this library, simply clone the repository and include the necessary files in your Android project.

---

## Usage

### Initialize the API Client
```java
ApiService apiService = ApiClient.getClient().create(ApiService.class);
```

### Fetch All Events
```java
apiService.getEvents().enqueue(new Callback<List<Event>>() {
    @Override
    public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
        if (response.isSuccessful()) {
            List<Event> events = response.body();
            // Handle events
        }
    }
    @Override
    public void onFailure(Call<List<Event>> call, Throwable t) {
        // Handle failure
    }
});
```

### Create a New Event
```java
Event event = new Event(null, "21-02-2025 9:00", "Alice's Birthday", "Dana's Home", "dana123", "Birthday Party", new ArrayList<>());
apiService.createEvent(event).enqueue(new Callback<Void>() {
    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {
        if (response.isSuccessful()) {
            Log.d("EventHub", "Event created successfully!");
        }
    }
    @Override
    public void onFailure(Call<Void> call, Throwable t) {
        Log.e("EventHub", "Error creating event", t);
    }
});
```

### Get Guests for an Event
```java
apiService.getGuestsByEvent(eventId).enqueue(new Callback<List<Guest>>() {
    @Override
    public void onResponse(Call<List<Guest>> call, Response<List<Guest>> response) {
        if (response.isSuccessful()) {
            List<Guest> guests = response.body();
            // Handle guests
        }
    }
    @Override
    public void onFailure(Call<List<Guest>> call, Throwable t) {
        // Handle failure
    }
});
```

### Update Guest Status
```java
Map<String, String> statusMap = new HashMap<>();
statusMap.put("status", "Attending");
apiService.updateGuestStatus(eventId, guestEmail, statusMap).enqueue(new Callback<Void>() {
    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {
        if (response.isSuccessful()) {
            Log.d("EventHub", "Guest status updated");
        }
    }
    @Override
    public void onFailure(Call<Void> call, Throwable t) {
        Log.e("EventHub", "Error updating guest status", t);
    }
});
```

---

## Additional Features
- **RecyclerView Adapters**: Built-in adapters for event and guest lists.
- **Error Handling**: Standardized error responses from the API.
- **Authentication**: The API is public and does not require an API key.

## License
This library follows the [MIT License](LICENSE).

## Contact
For further questions or support, please visit the [GitHub Repository](https://github.com/DanaKaplan21/EventHub_App).
