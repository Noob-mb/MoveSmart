# MoveSmart
HELPING YOU TRAVEL BETTER AROUND CAMPUS
# 🚗 MoveSmart – Helping You Travel Better Around Campus

## 📖 Overview
**MoveSmart** is an interactive, map-based web application designed to improve travel safety and convenience within college campuses.  
It provides **real-time updates** on road blockages, accidents, and public facility ratings, helping students and staff make smarter travel decisions.  
The system encourages **community participation**, allowing users to report incidents, share feedback, and contribute to a safer, better-connected campus.

---

## 🧠 Inspiration
The idea for MoveSmart was inspired by frequent road issues and lack of real-time navigation updates within our college area.  
Existing apps like Google Maps don’t offer localized campus-level data. We wanted a **community-driven solution** where users could both access and contribute information relevant to their immediate surroundings.

---

## 💡 Features
- 🔐 **User Authentication** – Secure login and access control  
- 🗺️ **Interactive Map Interface** – Built using Leaflet.js and OpenStreetMap  
- ⚠️ **Real-Time Road Alerts** – Displays roadblocks, accidents, and ongoing constructions  
- 🏫 **Public Facility Ratings** – Users can rate campus facilities (parking, canteen, library, etc.)  
- 📍 **User-Generated Reports** – Users can report incidents or suggest updates  
- 🔄 **Dynamic Data Updates** – Map refreshes automatically as new data is added  

---

## 🏗️ How We Built It
- **Backend:** Java  
- **Database:** MySQL (via JDBC)  
- **Frontend:** HTML5, CSS3, JavaScript, Leaflet.js  
- **Mapping & Navigation:** OpenStreetMap and Waze Integration  
- **Development Tools:** JDK, IntelliJ IDEA / Eclipse  

---

## ⚙️ System Architecture
1. **User Interface:** Interactive map and input forms built with HTML/CSS/JS.  
2. **Backend Logic:** Java application handles user requests, validation, and data management.  
3. **Database Layer:** MySQL stores user data, reports, facility ratings, and incident details.  
4. **Real-Time Updates:** The system fetches and displays live data on the map using Leaflet APIs.  

---

## 🧩 Data Structures & Algorithms
- **MySQL Tables:** `users`, `incidents`, `facilities`, `road_status`  
- **Geolocation Data:** Stored as latitude-longitude pairs  
- **Algorithms Used:**
  - Haversine Formula for distance calculation  
  - Geohash indexing for efficient spatial queries  
  - Aggregation algorithm for facility ratings  

---

## 🧱 Challenges We Faced
- Implementing dynamic, real-time updates on the map interface  
- Managing concurrent user submissions and database synchronization  
- Designing an intuitive UI that displays complex data clearly  
- Integrating OpenStreetMap and geocoding efficiently  

---

## 🏆 Accomplishments
- Built a fully functional prototype with live map updates  
- Established secure login and data management system  
- Developed a feedback-based facility rating mechanism  
- Encouraged active user participation in improving campus safety  

---

## 📚 What We Learned
- How to integrate **frontend and backend systems** effectively  
- Working with **mapping libraries (Leaflet.js)** and **geolocation data**  
- Importance of user-centered design and data accuracy  
- Team collaboration and version control using Git  

---

## 🚀 Future Scope
- Develop a **mobile app version** with push notifications  
- Integrate **AI-based route prediction** and **accident forecasting**  
- Add **IoT sensor connectivity** for automatic road condition updates  
- Expand the system beyond campus to cover nearby neighborhoods 
---

## 🏁 Conclusion
MoveSmart brings together technology, safety, and community engagement to make travel smarter and safer around campus.  
By enabling users to share real-time information, the platform transforms ordinary navigation into a collaborative experience that promotes awareness, efficiency, and connectivity.

---

### 📬 Contact
For any queries or contributions, reach out to the team via GitHub or email.

