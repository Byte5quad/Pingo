# Pingo
 
Pingo is a real-time chat application for community college students, built in Java with JavaFX. Students join their school ("village"), browse departments, and chat with others in the same course or department. Public messaging is scoped to each department room, and users can privately message anyone on the server — regardless of which room either person is in.
 
Built for CIS 35B at De Anza College by **ByteSquad**.
 
## Features
 
- **User login** — Sign in with a username, a unique ID, the server's IP address, and a port. A checkbox lets one user host the server on their own machine.
- **Village & department chats** — Pick a village (De Anza, Foothill, Transfer) and a department within it. Public messages are scoped to that specific village-and-department room.
- **Public messaging** — Real-time messaging broadcast to everyone in the same room, across clients on a local network.
- **Private messaging** — Send a private message to any connected user with `/msg [recipientID] [message]`. Private messages are global (they reach the recipient in any room) and are styled in orange to distinguish them from public messages.
- **Error handling** — Messaging a user ID that isn't online returns a system notification to the sender.
## Tech Stack
 
| Layer | Technology |
|-------|-----------|
| Language | Java 21 (Temurin / Adoptium) |
| UI | JavaFX (FXML + CSS) |
| Networking | `java.net` — `Socket` / `ServerSocket` (raw TCP) |
| Concurrency | Java threads |
| Build | Gradle (multi-module) |
| Version control | Git / GitHub |
 
## Project Structure
 
The project is a multi-module Gradle build with two modules:
 
```
Pingo/
├── settings.gradle          # declares the 'app' and 'shared' modules
├── build.gradle             # shared config (Java plugin, JUnit) for all modules
├── shared/                  # data models shared by client and server
│   └── src/
│       ├── main/java/model/
│       │   ├── Message.java
│       │   └── User.java
│       └── test/java/model/
│           ├── MessageTest.java
│           └── UserTest.java
└── app/                     # client, server, and UI
    └── src/main/java/
        ├── Launcher.java
        ├── HelloApplication.java
        ├── LoginController.java
        ├── HelloController.java
        ├── VillageViewController.java
        ├── ChatController.java
        ├── ChatComponent.java
        ├── Client.java
        ├── SessionManager.java
        └── server/
            ├── ChatServer.java
            ├── ClientHandler.java
            └── ServerLauncher.java
```
 
### Key Classes
 
**Shared (data models)**
- `User` — a username and an integer ID. Equality is based on ID only.
- `Message` — the object sent over the socket. Holds the sender, content, timestamp, type (`PUBLIC` / `PRIVATE`), recipient ID, and chat room. Implements `Serializable`.
**Server**
- `ChatServer` — opens a `ServerSocket`, accepts connections on a background thread, and keeps a `Map<Integer, ClientHandler>` of connected clients. Broadcasts public messages and routes private ones.
- `ClientHandler` — one per client, runs on its own thread, reads incoming messages and dispatches them by type.
**Client**
- `Client` — manages one user's socket connection and listens for incoming messages on a background thread.
- `ChatController` — bridges the UI and the network; builds outgoing messages and updates the chat UI on incoming ones.
- `ChatComponent` — the JavaFX chat UI (message bubbles, input field, send button); parses the `/msg` command.
- `SessionManager` — singleton holding the logged-in user, server IP, and port.
**UI controllers**
- `LoginController`, `HelloController`, `VillageViewController` — handle the login screen, sidebar navigation, and department grid.
- `HelloApplication` / `Launcher` — JavaFX entry points.
### Architecture
 
Pingo uses a **client-server architecture**. Each client connects to the host's IP and the server's port; the server creates a dedicated `ClientHandler` for that client. Messages sent by a client travel to its handler, which broadcasts them to other handlers in the same room (public) or routes them to a single recipient (private). Each handler then forwards the message to its own client for display.
 
The application also follows the **Model-View-Controller** pattern:
- **Model** — `Message`, `User`, `SessionManager`
- **View** — the `.fxml` files and `styles.css`
- **Controller** — `LoginController`, `HelloController`, `VillageViewController`, `ChatController`
## Getting Started
 
### Prerequisites
 
- JDK 21 (the project was developed and tested on Temurin / Adoptium 21)
- Gradle (the included Gradle wrapper, `./gradlew`, handles this automatically)
> **Note:** If you have a newer JDK installed by default, point your terminal at JDK 21 before building:
> ```bash
> export JAVA_HOME=$(/usr/libexec/java_home -v 21)   # macOS
> ```
 
### Run the app
 
From the project root:
 
```bash
./gradlew :app:run
```
 
To run multiple clients on one machine, open separate terminals and run the command in each.
 
### Logging in
 
On the login screen, enter:
- **Username** — any name
- **User ID** — a unique integer (used to route private messages)
- **Server IP** — `127.0.0.1` for local testing, or the host's IP on a shared network
- **Port** — e.g. `6000`
- **Start Server on this computer?** — exactly one user checks this to host; everyone else leaves it unchecked and connects to that host's IP
After logging in, pick a village, then a department, to open its chat room.
 
### Sending messages
 
- **Public:** type a message and press Send — it goes to everyone in your current room.
- **Private:** type `/msg [recipientID] [message]` — it goes only to that user, shown in orange.
## Testing
 
Unit tests for the model classes (`Message`, `User`) are written with JUnit 5 and run without a server:
 
```bash
./gradlew :shared:test
```
 
The team also performed manual integration testing across multiple clients and two physical machines (Windows and macOS) on the same local network, covering public messaging, room scoping, private messaging, and error handling.
 
## Team — ByteSquad
 
| Member | Role |
|--------|------|
| Saba Feilizadeh | Team Lead, Frontend / Architecture |
| Ishan Mishra | Frontend |
| Ryan Tran | Backend |
| Aaron Dbritto | Backend |
 
## Known Limitations
 
- Switching rooms broadcasts a join notification to all users rather than only the new room (cosmetic).
- Chat history is held in memory and is not persisted between sessions.
- Private messaging uses a `/msg` command rather than a clickable online-user list (a possible future enhancement).
 
