# SmartAlert Backend

## [University of Piraeus](https://www.unipi.gr/en/home/) | [Department of Informatics](https://cs.unipi.gr/en/)
**BSc course**: Current Topics Of Software Engineering - Software For Mobile Devices

**Semester**: 7

**Project Completion Year**: 2024

## Description
The SmartAlert Backend is a server-side application designed to support the SmartAlert mobile application, which provides immediate notifications to citizens during high-risk emergency situations. This backend handles incident management, and notification dispatching, ensuring that users receive timely and accurate information.

From the Android app, citizens can send a new report, which is a request to the server (Spring Boot). The backend server receives this request from the sender (citizen) and processes it, generating a group of data through a PostgreSQL database trigger function named [`assign_report_to_group`](docker/sql-scripts/v2.0.0__DDL_create_functions.sql). The group data is then sent to Firebase Real-time Database, allowing the Android application to inform employees in real-time about the new incident. Employees can accept or decline the incident, and based on their decision, if the incident is approved, the backend server sends an alert message (push notification) through Firebase to inform citizens near the incident.

## Features
- **Incident Reporting**: Citizens can submit high-risk incident reports through the Android app, which sends a request to the Spring Boot backend.
- **Data Processing**: The backend processes incoming requests and generates a group of data using the PostgreSQL database trigger function [`assign_report_to_group`](docker/sql-scripts/v2.0.0__DDL_create_functions.sql).
- **Real-time Database Integration**: The generated group data is sent to Firebase Real-time Database, enabling immediate updates to the application.
- **Incident Management**: Employees can accept or decline incidents, with the backend handling the logic for approval.
- **Alert Messaging**: If an incident is approved, the backend server sends a push notification through Firebase to inform citizens near the incident.
- **Employee Dashboard**: An interface for employees to monitor incidents, and view statistics.

## Demo Video
[![SmartAlert-Demo](https://img.youtube.com/vi/9_GyKJ3iy4s/0.jpg)](https://youtu.be/9_GyKJ3iy4s)

## Contributors
<table>
  <tr>
    <td align="center"><a href="https://github.com/dimitrisstyl7"><img src="https://avatars.githubusercontent.com/u/75742419?v=4" width="100px;" alt="Dimitris Stylianou"/><br /><sub><b>Dimitris Stylianou</b></sub></a><br /></td>
    <td align="center"><a href="https://github.com/roussosan"><img src="https://avatars.githubusercontent.com/u/79643636?v=4" width="100px;" alt="Antonis Roussos"/><br /><sub><b>Antonis Roussos</b></sub></a><br /></td>
    <td align="center"><a href="https://github.com/kostas96674"><img src="https://avatars.githubusercontent.com/u/79859276?v=4" width="100px;" alt="Konstantinos Loizidis"/><br /><sub><b>Konstantinos Loizidis</b></sub></a><br /></td>
  </tr>
</table>

## Acknowledgments
This project was developed as part of the "Current Topics Of Software Engineering - Software For Mobile Devices" BSc course at the University of Piraeus. Contributions and feedback are always welcome!

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
