# YoutubeWebsc App

# 소스코드 구성

---

#### ScrapActivity.kt

= 키워드를 입력하면 Scrapping (AsyncTask) 객체 생성

#### Scrapping.kt

= doInBackground()에서 Jsoup을 통해 소스코드 가져옴

#### 나머지는 볼 필요X

# 문제점

---

html코드를 가져올 수는 있는데, 너무 깊숙이 있는 소스코드는 접근을 못하는 것 같았다

그래서 검색 페이지랑 채널 페이지는 접었다

그에 비해 특정 영상 페이지 웹스크래핑은 <head>에 간단히 접근할 수 있다

하지만 너무 최신에 나온 영상은 메타데이터가 아직 작성이 안되어 있기도 했다

![스크린샷 2021-05-10 오후 9.56.12.png](https://res.craft.do/user/full/fa176664-3985-eb36-93ba-d918d8fd4d32/doc/99F86478-0788-4512-95C2-4E383D421B8C/BA774956-CEB4-4F32-AB32-DEAD89D4D1E6_2/%202021-05-10%20%209.56.12.png)

어느정도 이름 탄 영상 소스코드는 다음과 같다

![Image.png](https://res.craft.do/user/full/fa176664-3985-eb36-93ba-d918d8fd4d32/doc/99F86478-0788-4512-95C2-4E383D421B8C/15ED0BA3-7CA8-4878-9C28-FE4DABABF88F_2/Image.png)

![Image.png](https://res.craft.do/user/full/fa176664-3985-eb36-93ba-d918d8fd4d32/doc/99F86478-0788-4512-95C2-4E383D421B8C/EA716328-2A83-4376-BC91-E24231B359B7_2/Image.png)

# 결론

---

유튜브 영상 검색 등을 위해서 Youtube API를 이용해야만 할 것 같다

하지만 request 량이 제한되어있다보니 DB에 영상id를 저장해야할 것 같다

사실 AsyncTask가 아니라 Retrofit을 사용하려 했는데 Thread가 실행이 안되었다 (?)

구글링해보니 보통 코루틴을 사용하는 것 같았다

