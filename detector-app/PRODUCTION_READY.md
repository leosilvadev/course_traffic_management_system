# Requirements

- Detector must keep functioning in case of network outage
  - Data must be persisted and eventually sent to the server
- Improved detection throughput
  - Batch requests
  - Compress data in the request
- Possibility to change some equipment configurations through the server
  - Enable/disable lane
- Detector's health must be monitored
  - What happens if the detector stop to run by a bug or a hardware issue?
- Security
  - Token generation using JWS (signed JWT)
  - Protect against (D)DoS: using rate limiters
- Documentation
  - Diagrams and simple doc on how the application works
  - How to operate the application? How to setup a new equipment?

# Steps to implement

- Refactor detector code: use Thread's methods, and tests
- Stop sending the detections, persistent them in a local queue
- Send messages from the queue in batch
- Handle batch request in the API
- Compress request and handle compressed request in the API
- Endpoint to enable/disable lane inside the API
- Propagate changes regarding a Lane from the API to the App
- Implement authentication and authorization in the API
- Implement authentication and authorization in the App
- Implement rate limiter in the API
- Implement App's heartbeat in the App
- Document the App's design integrated with our codebase
- Creation of App's runbook