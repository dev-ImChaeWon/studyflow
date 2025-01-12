## 프로젝트 소개
학원 학생들의 효율적인 학습 관리와 성과 향상을 위한 통합 관리 시스템입니다. 출결 관리부터 성적 관리까지 원스톱 솔루션을 제공합니다.

## 기술 스택 및 도구
![Java](https://img.shields.io/badge/Java-007396?style=flat-square&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat-square&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=flat-square&logo=postman&logoColor=white)
![Spring Data JPA](https://upload.wikimedia.org/wikipedia/commons/a/a1/Spring_Data_JPA_logo.svg)
![JWT](https://img.shields.io/badge/Authentication-JWT-ff69b4?style=flat-square)
![Notion](https://img.shields.io/badge/Notion-000000?style=flat-square&logo=notion&logoColor=white)
![Slack](https://img.shields.io/badge/Slack-4A154B?style=flat-square&logo=slack&logoColor=white)

## 주요 기능

## API 명세
| Domain | URL | HTTP Method | Description |
| :-: | :-: | :-: | :-: |
| Student | /api/student | Post | 학생 추가 |
|| /api/student/{studentId} | Put | 학생 수정 |
|| /api/all-student | Get | 모든 학생 조회 |
| Teacher | /api/teacher | Post | 선생님 추가 |
|| /api/teacher/{userId} | Delete | 선생님 삭제 |
| Attendance | /api/attendance-update | Post | 출결 생성(수정) |
|| /api/attendance-delete/{studentId}/{attendanceDate} | Delete | 출결 삭제 |
| Homework | /api/homework-create | Post | 숙제 생성 |
|| /api/homework-update | Post | 숙제 수정 |
| Subject | /api/subject | Post | 과목 추가 |
|| /api/subject/{subjectId} | Delete | 과목 삭제 |
