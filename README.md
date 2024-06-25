# Photo Board Android app
사진과 제목, 내용 등을 작성하여 서버에 저장하고 이를 받아 화면에 출력하는 앱입니다.
서버는 스프링부트 프레임워크를 이용하여 구현하였습니다.
안드로이드 앱에서 http 연결을 통해 서버와 접속하여 사진 및 JSON 데이터를 송수신합니다.



## Result Screen
![PhotoBoard_home](https://github.com/Choco-Coding/PhotoBoard_Android/assets/117694927/89ea97da-82fa-4178-9d85-7ca3044fae50)

서버에 저장되어 있는 사진을 수신하여 홈 화면에 grid view 형태로 정렬해 표시합니다.

![PhotoBoard_contents](https://github.com/Choco-Coding/PhotoBoard_Android/assets/117694927/86a440e7-2a5c-4afb-87e7-e11f8f1c7e95)

홈 화면에서 각 사진을 클릭하면 제목, 내용 등의 상세 정보가 표시됩니다. 수정 및 삭제를 할 수 있습니다.

![PhotoBoard_upload](https://github.com/Choco-Coding/PhotoBoard_Android/assets/117694927/e844f5e4-2c36-48ca-a9ce-108c03e0b70d)

새로운 사진을 업로드하는 화면입니다.
