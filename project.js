// ================= BACKGROUND MAP (for auth pages) =================
// ================= BACKGROUND MAP (auth pages) =================
if (document.getElementById("bg-map")) {
  var bgMap = L.map('bg-map', {
    attributionControl: false,
    zoomControl: false,
    dragging: false,
    scrollWheelZoom: false,
    doubleClickZoom: false,
    boxZoom: false,
    keyboard: false,
    tap: false,
  }).setView([28.8315, 77.5792], 13);

  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19
  }).addTo(bgMap);

  // Animate background map by panning slowly
  let lat = 28.8315;
  let lng = 77.5792;
  let step = 0.01; // small step for smooth movement

  setInterval(() => {
    lng += step; // slowly move east
    bgMap.panTo([lat, lng], { animate: true, duration: 2 });
  }, 2000); // move every 4 seconds
}


// ================= SIGN UP =================
if (document.getElementById("signupForm")) {
  document.getElementById("signupForm").addEventListener("submit", function(e) {
    e.preventDefault();
    let user = document.getElementById("signup-username").value;
    let pass = document.getElementById("signup-password").value;
    let confirm = document.getElementById("signup-confirm").value;

    if (pass !== confirm) {
      document.getElementById("signup-error").innerText = "Passwords do not match!";
      return;
    }

    // Save to localStorage (demo only, replace with backend in real app)
    localStorage.setItem("user_" + user, pass);
    alert("Account created! Please sign in.");
    window.location.href = "signin.html";
  });
}

// ================= SIGN IN =================
if (document.getElementById("signinForm")) {
  document.getElementById("signinForm").addEventListener("submit", function(e) {
    e.preventDefault();
    let user = document.getElementById("signin-username").value;
    let pass = document.getElementById("signin-password").value;

    let storedPass = localStorage.getItem("user_" + user);

    if (storedPass && storedPass === pass) {
      localStorage.setItem("loggedIn", "true");
      window.location.href = "index.html"; // go to map
    } else {
      document.getElementById("signin-error").innerText = "Invalid username or password!";
      document.getElementById("signin-error").color = "red";
    }
  });
}
// Show/Hide password
function togglePassword(fieldId, btn) {
  const field = document.getElementById(fieldId);
  if (field.type === "password") {
    field.type = "text";
    btn.textContent = "🙈"; // change icon when visible
  } else {
    field.type = "password";
    btn.textContent = "👁";
  }
}

// ================= FORGOT PASSWORD =================
if (document.getElementById("forgotForm")) {
  document.getElementById("forgotForm").addEventListener("submit", function(e) {
    e.preventDefault();
    let user = document.getElementById("forgot-username").value;
    let storedPass = localStorage.getItem("user_" + user);

    if (storedPass) {
      document.getElementById("forgot-message").style.color = "black";
      document.getElementById("forgot-message").innerText = "Your password is: " + storedPass;
    } else {
      document.getElementById("forgot-message").style.color = "red";
      document.getElementById("forgot-message").innerText = "No account found with that username.";
    }
  });
}


// ================= INDEX PAGE PROTECTION =================
if (document.getElementById("map")) {
  if (!localStorage.getItem("loggedIn")) {
    window.location.href = "signin.html";
  }
}


// ====================== MAP PAGE SCRIPT ======================
if (document.getElementById("map")) {
  // Prevent access if not logged in
  if (!localStorage.getItem("loggedIn")) {
    window.location.href = "login.html";
    }
    }
    
  

  var map = L.map('map').setView([28.8315, 77.5792], 14);

  var osm = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; OSM contributors'
  }).addTo(map);

  var hot = L.tileLayer('https://{s}.tile.openstreetmap.fr/hot/{z}/{x}/{y}.png', {
    maxZoom: 20,
    attribution: '&copy; HOT tiles'
  });

  var esriSat = L.tileLayer(
    'https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}',
    { maxZoom: 20, attribution: 'Tiles © Esri' }
  );

  var cartoLight = L.tileLayer('https://{s}.basemaps.cartocdn.com/light_all/{z}/{x}/{y}{r}.png', {
    maxZoom: 20,
    attribution: '&copy; OSM &copy; CARTO',
    subdomains: 'abcd'
  });

  L.control.layers({
    'OSM': osm,
    'HOT': hot,
    'Esri Satellite': esriSat,
    'Carto Light': cartoLight
  }).addTo(map);

  L.Control.geocoder({ defaultMarkGeocode: true }).addTo(map);

  var redIcon = new L.Icon({
    iconUrl: 'https://maps.gstatic.com/mapfiles/ms2/micons/red-dot.png',
    iconSize: [32, 32],
    iconAnchor: [16, 32],
    popupAnchor: [0, -32]
  });


  var bounds = [];

  // Accident markers
  var marker = L.marker([28.8311, 77.577], { icon: redIcon }).addTo(map)
    .bindPopup('<b>Modinagar</b><br>Test accident<br>(28.8311,77.577)<br>' +
               '<a href="https://waze.com/ul?ll=28.8311,77.577&navigate=yes" target="_blank">Navigate in Waze</a>');
  bounds.push([28.8311, 77.577]);

  var marker = L.marker([28.8315, 77.5792], { icon: redIcon }).addTo(map)
    .bindPopup('<b>modinagar bus stand</b><br>minor<br>(28.8315,77.5792)<br>' +
               '<a href="https://waze.com/ul?ll=28.8315,77.5792&navigate=yes" target="_blank">Navigate in Waze</a>');
  bounds.push([28.8315, 77.5792]);

  var marker = L.marker([28.8135, 77.5862], { icon: redIcon }).addTo(map)
    .bindPopup('<b>modinagar </b><br>truck turnover<br>(28.8135,77.5862)<br>' +
               '<a href="https://waze.com/ul?ll=28.8135,77.5862&navigate=yes" target="_blank">Navigate in Waze</a>');
  bounds.push([28.8135, 77.5862]);

  var marker = L.marker([28.8135, 77.5782], { icon: redIcon }).addTo(map)
    .bindPopup('<b>modinagar</b><br>hjak<br>(28.8135,77.5782)<br>' +
               '<a href="https://waze.com/ul?ll=28.8135,77.5782&navigate=yes" target="_blank">Navigate in Waze</a>');
  bounds.push([28.8135, 77.5782]);

  var marker = L.marker([28.8135, 77.5782], { icon: redIcon }).addTo(map)
    .bindPopup('<b>gh</b><br>hg<br>(28.8135,77.5782)<br>' +
               '<a href="https://waze.com/ul?ll=28.8135,77.5782&navigate=yes" target="_blank">Navigate in Waze</a>');
  bounds.push([28.8135, 77.5782]);

  // Fit bounds
  if (bounds.length > 0) {
    map.fitBounds(bounds);
  } else {
    map.setView([28.8315, 77.5792], 14);
  }
