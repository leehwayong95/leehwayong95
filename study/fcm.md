<img src="https://www.earlysoft.co.kr/wp-content/uploads/2019/05/fcm.jpg">

# FCM(Firebase Cloud Messaging)

"무료"로 메시지를 안정적으로 전송할 수 있는 교차 플랫폼 메시징 솔루션

## 구성요소

1. Message building and targeting

   메시지를 작성하거나 구현하는 도구로 메시지를 작성합니다.

   FCM 서버 프로토콜을 지원하고 신뢰할 수 있는 서버환경에게 메시지요청을 합니다.

2. FCM Back-end

   메시지 요청을 수락하고, 주제를 통해 메시지를 확장하고 메시지 ID 와같은 메시지 메타데이터를 생성

3. Platfor-level message transport

   기기로 타겟팅된 메시지를 라우팅하고, 메시지 전송을 처리하고, 필요한경우 플랫폼별 구성을 적용하는 플랫폼 수준의 전송 레이어.

   - Google Play 서비스를 사용하는 Android 전송 레이어 (ATL)
   - IOS 기기용 Apple 푸시 알림서비스 (APN)
   - 웹 앱용 웹 푸시 프로토콜

4. SDK on device

   기기에 알림이 표시.

## 유형

1. 알림 메시지

   - FCM이 클라이언트 앱을 대신해 기기에 자동으로 메시지 표시

     사용자에게 표시되는 사전정의된 키모음 및 `Key-Value` 데이터 페이로드 포함

   - 전송방법

     Cloud Functions 또는 앱 서버와 같은 신뢰할수 있는 환경에서 `Admin SDK` 또는 `FCM 서버 프로토콜`을 사용하여 알림키를 설정합니다. 선택사항으로 데이터 페이로드를 추가할 수 있습니다.

     

2. 데이터 메시지

   - 클라이언트 앱이 데이터 메시지 처리를 담당합니다. 

     데이터 메시지에는 예약키 이름 없이 커스텀 `Key-Value`만 있습니다.

