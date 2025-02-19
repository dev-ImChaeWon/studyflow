## 프로젝트 소개
학원 학생들의 효율적인 학습 관리와 성과 향상을 위한 통합 관리 시스템입니다. 출결 관리부터 성적 관리까지 원스톱 솔루션을 제공합니다.
- 프로젝트 기간: `20241005` ~ `20241208` (하단에 서술된 주요 기능은 완성했으나, 개선이 필요한 경우 상의한 후 수정)

## 기술 스택 및 도구
![Java](https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=for-the-badge&logo=spring-data-jpa&logoColor=white)
![JWT](https://img.shields.io/badge/Authentication-JWT-ff69b4?style=for-the-badge)

![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

![Vue.js](https://img.shields.io/badge/vue.js-4FC08D?style=for-the-badge&logo=vue.js&logoColor=white)

![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)

![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white)
![Slack](https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white)

## ERD 설계
[✔︎ erdcloud](https://www.erdcloud.com/d/g7RwRrf2vAF5KagaY)
![StudyFlow-2](https://github.com/user-attachments/assets/bcd66d22-3a49-4f16-b1ce-284b68474354)

## 주요 기능
### [ 공통 기능 ]
- 아이디, 비밀번호로 로그인할 수 있음
### [ 학부모 기능 ]
- 아이디, 비밀번호, 이름으로 회원가입할 수 있음
- 자녀의 수강 과목과 출석률을 확인할 수 있음
- 자녀의 과목, 날짜별 주간평가 성적을 확인할 수 있음
### [ 선생님 기능 ]
- 원생관리
    - 학생의 날짜별 출석 관리를 할 수 있음
    - 출석, 결석, 지각, 조퇴
- 학습관리
    - 학생의 수강 과목별 숙제를 추가할 수 있음
    - 숙제별 해야 할 페이지, 완료 페이지를 설정하고 코멘트를 작성할 수 있음
    - 숙제 완료 정도를 퍼센트(%)로 나타낼 수 있음
- 수납관리
    - 학생들의 수강 과목 수납여부를 버튼으로 관리할 수 있음
- 환경설정
    - 학생을 신규 등록할 수 있음
    - 학생의 수강 과목을 설정하고 정보를 수정할 수 있음
    - 교사를 신규 등록할 수 있음
    - 교사의 담당 과목을 설정하고 정보를 수정할 수 있음
    - 학부모와 자녀 관계를 등록할 수 있음
    - 학생의 과목별 주간평가 점수를 등록할 수 있음
    - 학생의 과목별 수납정보를 등록할 수 있음

## API 명세
| Domain | URL | HTTP Method | Description |
| :-: | :-: | :-: | :-: |
| Student | /api/all-student | Get | 모든 학생 조회 |
|| /api/student | Get | 학생 조건별 숙제 조회 |
|| /api/student-by-subject | Get | 과목별 학생 조회 |
|| /api/test-score | Get | 테스트 점수 조회 |
|| /api/attendance | Get | 날짜별 출결 조회 |
|| /api/parent-student-info | Get | 부모ID로 학생 조회 |
|| /api/student | Post | 학생 신규 생성 |
|| /api/test-score | Post | 테스트 점수 생성 |
|| /api/parent-student | Post | 부모-학생 관계 등록 |
|| /api/student/{studentId} | Put | 학생 정보 수정 |
|| /api/test-score/{studentId}/{subjectId} | Put | 테스트 점수 수정 |
| Teacher | /api/teacher | Post | 선생님 추가 |
|| /api/teacher | Get | 선생님 신규 생성 |
|| /api/teacher/{userId} | Put | 선생님 정보 수정 |
|| /api/teacher/{userId} | Delete | 선생님 삭제 |
| Attendance | /api/attendance-update | Post | 출결 생성(수정) |
|| /api/attendance/{studentId} | Get | 출결 조회 |
|| /api/attendance-delete/{studentId}/{attendanceDate} | Delete | 출결 삭제 |
| Homework | /api/homework-create | Post | 숙제 생성 |
|| /api/homework-update | Post | 숙제 수정 |
|| /api/student-homework/{id} | Get | 숙제 조회 |
|| /api/student-homework | Get | ID, 날짜별 숙제 조회 |
| Subject | /api/student-subject/{id} | Get | id별 학생-과목 객체 조회 |
|| /api/subject | Get | 과목 조회 |
|| /api/teacher-subject/{userId} | Get | 교사ID별 과목 조회 |
|| /api/student-subject | Get | 학생ID별 과목 조회 |
|| /api/student-subject/{studentId}/{subjectId} | Get | (학생, 과목)ID로 학생-과목 객체 조회 |
|| /api/subject | Post | 과목 신규 생성 |
|| /api/student-subject | Post | 학생-과목 관계 등록 |
|| /api/subject/{subjectId} | Delete | 과목 삭제 |
| Parent | /api/parent | Get | 부모 조회 |
| BillManagement | /api/bill-management/{id} | Get | (학생, 과목)ID로 수납 정보 조회 |
|| /api/bill-management | Get | 수납 정보 조회 |
|| /api/bill-management-update | Post | 수납 정보 생성 및 수정 |
| User | /api/auth/signup | Post | 계정 신규 생성 |
|| /api/auth/signup | Get | 계정 조회 |
|| /api/auth/signin | Post | 로그인 |
