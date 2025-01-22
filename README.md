# COP16 Connect

COP16 Connect is an Android application designed to manage and display information about species, habitats, events, and locations related to the biodiversity of Cali, Colombia, and the COP16 event.

## Features

- **Species Management**: Manage species with attributes such as scientific name, common name, description, conservation status, and taxonomic classification.
- **Habitat Management**: Manage habitats with attributes like name, description, image, and location ID.
- **Event Management**: Manage events with attributes such as title, description, start date, end date, and location ID.
- **Location Management**: Manage locations with attributes like name, description, latitude, and longitude.
- **Database Interaction**: Perform CRUD operations on species, habitats, events, and locations using Room Persistence Library.
- **User Interface**: Display a Google Map with markers for species and events, and show detailed information when a marker is clicked.

## Target Users

The application is intended for:

- **Researchers and Scientists**: Professionals studying biodiversity who need access to detailed data.
- **Students**: Biology, ecology, and environmental science students requiring accurate information for their studies.
- **Event Organizers**: Individuals planning and executing biodiversity and conservation events like COP16.
- **Tourists and Visitors**: People interested in exploring Cali's biodiversity and attending related events.
- **Local Authorities and NGOs**: Governmental and non-governmental organizations working on biodiversity conservation.

## Technologies Used

- **Languages**: Java, Kotlin
- **Build System**: Gradle
- **Libraries**: 
  - Google Maps SDK: For visualizing species and event locations on a map.
  - Room Persistence Library: For local database management.
  - Android SDK: Provides tools and APIs for Android development.

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/0xCamiX/COP16Connect.git
    ```
2. Open the project in Android Studio.
3. Build the project using Gradle.
4. Run the application on an Android device or emulator.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
