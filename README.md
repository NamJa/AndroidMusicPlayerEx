# MyMusicApp

## DataBinding & RecyclerView Applied
https://github.com/NamJa/AndroidMusicPlayerEx/commit/1a5a06233d304636fa652188e630cf8f41f2ff65

Google IO에서 발표된 아주 편한 방법들을 이번 기회에 적용시켜 보았습니다.

I applied this skill from Google I/O for this time

## RecyclerView Item click listener Applied

https://github.com/NamJa/AndroidMusicPlayerEx/commit/34d5179fe44bdf15dcaa7a1405385039e9a0809b

list에 표시되는 item을 클릭했을 때의 이벤트를 변경할 수 있도록 추가

Want to change of Item-Click event action for add RecyclerView Click-listener

## Use MediaStore provider with AsyncTask for Permission setting of after Android Marshmallow version

https://github.com/NamJa/AndroidMusicPlayerEx/commit/b92fa56f1ffb5f0ec80c44a3982041f0e3a5fed5

recyclerview의 아이템 부분을 표시할 음악 파일 목록을 MediaStore를 통해 가져왔으며,
마쉬멜로 이후의 버전에서 요구하는 권한 설정 문제 때문에 벌어지는
ANR를 막고자 asyncTask를 사용하여 가져오도록 하였다.

Using MediaStore for get Music-list and viewing recyclerview items.
Using AsyncTask for ANR problems caused by permission settings of since Marshmallow version

## Music playing function complete
https://github.com/NamJa/AndroidMusicPlayerEx/commit/ff36559fa62b6da906eb17e8e223bd99f3f543ea (UI 1/2)
https://github.com/NamJa/AndroidMusicPlayerEx/commit/fd5486fe0ec6de862ff7cb02e7791a95dd42a50c (UI 2/2 & playing function)

ui 구성 후, mediaStore에서 오디오 파일의 아이디를 매칭시켜 재생하는 방식으로 구현되었으며, 재생 시간 업데이트 같은 경우에는 asyncTask, Thread 방식으로 시도하다가 에러가 발생하여 seekbar 클래스 내의 필수 구현 함수인 onProgressChanged()에서 구현하였다.

After ui configuration, this function is implemented by matching id of mediaStore audio file and playing. In the case of update of playing time, it is implemented in onProgressChanged (), which is a mandatory implementation function in seekbar class because error occurs while trying asyncTask, Thread method.

## Load Album art on RecyclerView
https://github.com/NamJa/AndroidMusicPlayerEx/commit/534f9617224a44bc119b7059e9165e84349d445f

recyclerview adapter 소스에 앨범아트를 로딩시키는 기능을 Glide라는 오픈 소스를 사용하여 구현하였다.

Loading albumart on RecyclerView function is implemented with Called open source name 'Glide'
