# trading-monorepo
Trading App is a full-stack web application for real-time trading, built with Spring Boot (Java), React, and MySQL (PlanetScale). It supports secure user authentication and market data retrieval. 
The app maintains real-time trading statistics such as average, min, and max prices using in-memory data structures like Deque and TreeMap. Stats are computed over a rolling window and flushed periodically or when stale, ensuring constant-time inserts and efficient range queries without database load.
The backend is deployed on Railway, the frontend on Vercel, and build workflows utilize AWS cloud runners. CORS and bcrypt-based authentication are fully integrated.
