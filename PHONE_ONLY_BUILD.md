# Phone-only build

Upload the contents of this project to the root of your GitHub repository.

Required top-level items:

- `.github/workflows/build-apk.yml`
- `app/`
- `build.gradle.kts`
- `gradle.properties`
- `settings.gradle.kts`

Then go to GitHub → Actions → Build BREAK APK → Run workflow.

The APK will appear as a downloadable artifact after the build succeeds.
