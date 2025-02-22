# SSE VS WebSocket 성능 테스트용 서버 Repo

주요 알림 사항
- SSE의 브라우저당 6개의 제한을 풀기위해 Https를 적용해 HTTP2 사용
- WebSocket에 여러개의 요청이 한번에 들어올시 **the remote endpoint was in state [TEXT_PARTIAL_WRITING] which is an invalid stat e for called method** 에러 발생
  하나의 socket에 여러개의 스레드가 쓰기접근하여 발생하는 에러 -> synchronized로 해결
  ```
  synchronized(session) {
                    println("Send : $message Session : ${session.id}")
                    session.sendMessage(TextMessage(message))
                }
  ```
