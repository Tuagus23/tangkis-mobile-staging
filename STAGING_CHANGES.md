# Staging changes

- Added Android INTERNET permission.
- Added Retrofit 3.0.0 + Gson 2.14.0 client dependencies.
- Added `BuildConfig.API_BASE_URL` driven by Gradle property `tangkisApiBaseUrl`.
- Added API DTOs, Retrofit service, client and mapper.
- Changed `TangkisApp` to load shared detection history on startup and upload new detections asynchronously.
- Kept AI inference and immediate UI result local/on-device.
