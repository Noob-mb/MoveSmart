import java.sql.*;
import java.io.*;
import java.util.*;
import java.awt.Desktop;

class Accident {
    String place;
    String description;
    double latitude;
    double longitude;

    public Accident(String place, String description, double latitude, double longitude) {
        this.place = place;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}

public class project {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/localhost";
        String user = "localhost";
        String password = "manya2005";

        Scanner sc = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // --- User login ---
            boolean loggedIn = false;
            while (!loggedIn) {
                System.out.print("Enter username (id): ");
                String username = sc.nextLine();

                System.out.print("Enter password (name): ");
                String pwd = sc.nextLine();

                String loginQuery = "SELECT * FROM userinfo WHERE id=? AND name=?";
                PreparedStatement loginStmt = conn.prepareStatement(loginQuery);
                loginStmt.setString(1, username);
                loginStmt.setString(2, pwd);
                ResultSet rs = loginStmt.executeQuery();

                if (rs.next()) {
                    System.out.println("Login successful!");
                    loggedIn = true;
                } else {
                    System.out.println("Invalid username or password. Try again.");
                }
            }

            // --- Accident input ---
            System.out.print("Enter accident place: ");
            String place = sc.nextLine();

            System.out.print("Enter accident time (YYYY-MM-DD HH:MM:SS): ");
            String accidentTime = sc.nextLine();

            System.out.print("Enter accident description: ");
            String description = sc.nextLine();

            System.out.print("Enter accident latitude: ");
            double latitude = sc.nextDouble();

            System.out.print("Enter accident longitude: ");
            double longitude = sc.nextDouble();

            // --- Insert accident info ---
            String accidentSql = "INSERT INTO accidentinfo (place, accident_time, description, latitude, longitude) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement accidentStmt = conn.prepareStatement(accidentSql);
            accidentStmt.setString(1, place);
            accidentStmt.setString(2, accidentTime);
            accidentStmt.setString(3, description);
            accidentStmt.setDouble(4, latitude);
            accidentStmt.setDouble(5, longitude);
            accidentStmt.executeUpdate();
            System.out.println("Accident info inserted successfully!");

            // --- Generate map ---
            generateMap(url, user, password);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }

    private static void generateMap(String url, String user, String password) {
        List<Accident> accidents = new ArrayList<>();

        // Fetch all accidents from DB
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT place, description, latitude, longitude FROM accidentinfo";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                accidents.add(new Accident(
                        rs.getString("place"),
                        rs.getString("description"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Generate HTML map
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("accidents_map.html"));
            writer.write("<!DOCTYPE html>\n<html>\n<head>\n");
            writer.write("<title>Accident Map</title>\n");
            writer.write("<meta charset='utf-8'/>\n");
            writer.write("<meta name='viewport' content='width=device-width, initial-scale=1.0'>\n");
            writer.write("<link rel='stylesheet' href='https://unpkg.com/leaflet/dist/leaflet.css'/>\n");
            writer.write("<link rel='stylesheet' href='https://unpkg.com/leaflet-control-geocoder/dist/Control.Geocoder.css'/>\n");
            writer.write("<script src='https://unpkg.com/leaflet/dist/leaflet.js'></script>\n");
            writer.write("<script src='https://unpkg.com/leaflet-control-geocoder/dist/Control.Geocoder.js'></script>\n");
            writer.write("<style>#map { height: 100vh; width: 100%; }</style>\n");
            writer.write("</head>\n<body>\n");
            writer.write("<div id='map'></div>\n<script>\n");

            writer.write("var map = L.map('map').setView([28.8315, 77.5792], 14);\n");

            // Base layers
            writer.write("var osm = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { maxZoom:19, attribution:'&copy; OSM contributors' }).addTo(map);\n");
            writer.write("var hot = L.tileLayer('https://{s}.tile.openstreetmap.fr/hot/{z}/{x}/{y}.png', { maxZoom:20, attribution:'&copy; HOT tiles' });\n");
            writer.write("var esriSat = L.tileLayer('https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}', { maxZoom:20, attribution:'Tiles © Esri' });\n");
            writer.write("var cartoLight = L.tileLayer('https://{s}.basemaps.cartocdn.com/light_all/{z}/{x}/{y}{r}.png', { maxZoom:20, attribution:'&copy; OSM &copy; CARTO', subdomains:'abcd' });\n");
            writer.write("L.control.layers({ 'OSM': osm, 'HOT': hot, 'Esri Satellite': esriSat, 'Carto Light': cartoLight }).addTo(map);\n");

            // Geocoder
            writer.write("L.Control.geocoder({ defaultMarkGeocode:true }).addTo(map);\n");

            // Marker icon
            writer.write("var redIcon = new L.Icon({iconUrl:'https://maps.gstatic.com/mapfiles/ms2/micons/red-dot.png',iconSize:[32,32],iconAnchor:[16,32],popupAnchor:[0,-32]});\n");

            writer.write("var bounds = [];\n");

            for (Accident a : accidents) {
                String placeEscaped = a.place.replace("'", "\\'").replace("\"","\\\"");
                String descEscaped = a.description.replace("'", "\\'").replace("\"","\\\"");
                writer.write("var marker = L.marker([" + a.latitude + "," + a.longitude + "],{icon:redIcon}).addTo(map)\n");
                writer.write(".bindPopup('<b>" + placeEscaped + "</b><br>" + descEscaped + "<br>(" + a.latitude + "," + a.longitude + ")<br>' +\n");
                writer.write("'<a href=\"https://waze.com/ul?ll=" + a.latitude + "," + a.longitude + "&navigate=yes\" target=\"_blank\">Navigate in Waze</a>');\n");
                writer.write("bounds.push([" + a.latitude + "," + a.longitude + "]);\n");
            }

            writer.write("if(bounds.length > 0){ map.fitBounds(bounds); } else { map.setView([28.8315,77.5792],14); }\n");

            writer.write("</script>\n</body>\n</html>");
            writer.close();

            Desktop.getDesktop().browse(new File("accidents_map.html").toURI());
            System.out.println("Map generated and opened in browser!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


