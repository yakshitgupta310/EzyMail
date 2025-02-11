# EzyMail

🚀 A Spring Boot application that powers the AI-generated smart replies and text rephrasing features for the EzyMail Chrome extension.
    It harnesses the power of GEN-AI API to generate smart replies and rephrase text for easy understanding. 
    It has been hosted online using Railway.

## 🌟 Features
- ✅ AI-powered smart replies for Gmail  
- ✅ Text rephrasing with multiple tone options  
- ✅ REST API for seamless integration with the Chrome extension  
- ✅ Secure & scalable Spring Boot backend  



## 📌 Tech Stack
- **Backend**: Java, Spring Boot, Spring Web  
- **AI Integration**: Gen AI API 
- **Hosting**: Railway 
- **Security**: CORS


## 🌍 Deployment
 
 [EzyMail](https://ezymail-production.up.railway.app/ezymail)


## ⚡ API Endpoints

### 1️⃣ Generate Smart Reply  
**POST** `/mail/generate`  
#### **Request:**
```json
{
    "content":"Email content",
    "tone":"reply tone"
}
```

### 2️⃣ Rephrase Text
**POST** `/mail/rephrase`  
#### **Request:**
```json
{
    "content":"Content to be rephrased"
}
```