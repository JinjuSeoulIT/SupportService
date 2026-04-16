# 간호기록 접수 목록 연동 API

간호기록 화면의 `접수 환자 목록`은 **접수 MSA의 대기열 API**를 medical_support에서 프록시한다.

## 1) 외래 접수 대기열 (권장)

- Method: `GET`
- Path: `/api/receptions/queue`
- Query params
  - `date` (optional): `yyyy-MM-dd` — **생략 시 서버 기준 오늘**
  - `departmentId` (optional)
  - `doctorId` (optional)

### 요청 예시

```http
GET /api/receptions/queue
GET /api/receptions/queue?date=2026-04-15
GET /api/receptions/queue?date=2026-04-15&departmentId=5
```

### 응답

접수 MSA와 동일한 `ApiResponse<List<OutpatientReceptionDTO>>` 형식.

---

## 2) 접수 목록(조건) 조회

접수 MSA가 `visitDate/statuses` 등을 지원할 때 연동용으로 사용한다. (환경에 따라 무시될 수 있음)

- Method: `GET`
- Path: `/api/receptions`
- Query: `visitDate` (required), `visitType`, `statuses`

---

## 3) 접수 상세 조회

- Method: `GET`
- Path: `/api/receptions/{id}`

---

## 프론트 적용 가이드

- 간호기록 왼쪽 목록은 **`GET /api/receptions/queue`** 만 호출하면 된다 (`date` 생략 시 오늘).
- 베이스 URL은 medical_support 서버 (예: `http://localhost:8181`).
