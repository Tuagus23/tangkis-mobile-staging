# Tangkis Mobile — Staging API integration

This copy adds a small Retrofit client and changes `TangkisApp` so that:

1. on app start it requests the latest 100 shared detections from `GET /api/v1/detections`;
2. after a local detection, it immediately shows the result and asynchronously sends it to `POST /api/v1/detections`;
3. if the server is unavailable, the app keeps the local result instead of blocking the user.

## Set the staging URL

The API URL is injected through Gradle property `tangkisApiBaseUrl`.

Preferred local developer setup in `android/gradle.properties`:

```properties
tangkisApiBaseUrl=https://YOUR-RAILWAY-DOMAIN.up.railway.app/api/v1/
```

Do not put database credentials or other secrets in the Android app. A base API URL is not a secret.

## Build

From `android/`:

```powershell
.\\gradlew.bat assembleDebug -PtangkisApiBaseUrl=https://YOUR-RAILWAY-DOMAIN.up.railway.app/api/v1/
```

APK output:

```text
android/app/build/outputs/apk/debug/app-debug.apk
```

## Important scope

The AI inference remains on-device. The server stores classification results only. This is intentionally a phase-1 staging integration.
