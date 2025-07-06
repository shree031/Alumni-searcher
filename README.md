
# 🎓 Alumni LinkedIn Profile Searcher

A Spring Boot REST API that searches LinkedIn profiles of alumni from a specific university using the [PhantomBuster](https://phantombuster.com) API.

## 🚀 Features

- Launches PhantomBuster’s LinkedIn Search Export agent via API
- Accepts inputs like University name, Designation, and Passout Year
- Saves and returns results in JSON format
- PostgreSQL integration ready
- Clean, testable REST architecture

## 📦 Technologies Used

- Java 21
- Spring Boot
- PhantomBuster REST API
- PostgreSQL 
- Jackson for JSON parsing

## 📄 API Endpoints

### 🔍 POST `/api/alumni/search`

Searches LinkedIn alumni profiles.

**Request Body:**
```json
{
  "university": "Savitribai Phule Pune University",
  "designation": "Software Engineer",
  "passoutYear": 2020
}
```

**Response:**
```json
{
  "status": "success",
  "data": [
    {
      "name": "John Doe",
      "currentRole": "Software Engineer",
      "university": "Savitribai Phule Pune University",
      "location": "New York",
      "linkedinHeadline": "Software Engineer @ XYZ",
      "passoutYear": 2020
    }
  ]
}
```

### 📥 GET `/api/alumni/all` (Optional)
Fetch all saved alumni (if DB is used).

---

## 🔧 Setup Instructions

1. **Clone the repo**
2. **Configure `application.properties`:**
```properties
# PhantomBuster Config
phantombuster.api.key=your_api_key
phantombuster.phantom.id=your_phantom_id
phantombuster.output.url=https://api.phantombuster.com/api/v2/containers/fetch-output
phantombuster.session.cookie=your_li_at_cookie_value
phantombuster.identity.id=your_identity_id
phantombuster.identity.name=Your Full Name
```

3. **Run the application:**
```bash
./mvnw spring-boot:run
```

4. **Test with Postman:**
- POST to `/api/alumni/search` with JSON body

---

## ✅ Best Practices Followed

- Clean architecture with separation of concerns
- Externalized configuration for secrets
- DTO-based request/response handling
- Error handling for failed API calls or JSON parsing
- Expandable with DB, caching, or scheduling

---

## 📧 Author

Developed by Shreedevi Patil as part of a technical assignment.
