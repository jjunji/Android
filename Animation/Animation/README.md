# Animation

안드로이드 애니메이션은 크게 2가지로 분류 할 수 있다.

1. Tween Animation

2. Property Animation



Tween Animation 은 뷰에 대한 애니메이션을 정의한 xml을 두는 Directory이며, 회전, 사이즈 변화, 투명화, 이동 등을 정의한다. (Resource type : anim)

Property Animation 는 객체 단위로 적용 가능한 애니메이션을 정의하는 Directory, anim에 넣어도 상관은 없다. (Resource type : animator)

**Animation 사용법.**

* Rotate
* Scale
* Translate
* Alpha
* Property Animation

***
**1. Rotate(회전)**

res 디렉토리에서  -> anim 디렉토리 생성 - rotate.xml 추가.

```java
<rotate
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:fromDegrees="0"
    android:toDegrees="720"
    android:pivotX="50%"
    android:pivotY="50%"
    android:duration="3000"
    >
</rotate>
```
pivotX,Y 를 50%로 잡지 않으면 뷰가 제자리에서 돌지 않는다.

이유는 회전하는 중심점이 이미지 뷰의 가운데를 기준으로 하는 것이 아니라 이미지 뷰를 감싸고있는 사각형 배경의 좌측 상단으로 되있기 때문.



android:fromDegrees & toDegrees -> 회전의 시작 각도와 끝내는 각도를 의미한다. 720 -> 2바퀴 회전.

android:duration : 밀리 초. 3000 -> 3초

***
**2. Scale(사이즈 변화)**

res 디렉토리에서  -> anim 디렉토리 생성 - scale.xml 추가.
```java
<scale
    xmlns:android="http://schemas.android.com/apk/res/android"    
    android:fromXScale="1.0"
    android:fromYScale="1.0"
    android:toXScale="5.0"
    android:toYScale="5.0"
    android:pivotX="50%"
    android:pivotY="50%"
    android:fillAfter="true"
    android:duration="3000">
</scale>
```
android : fromXScale & fromYScalse -> 1.0 이므로 뷰의 원본 크기를 의미한다.

android : toXScale & toYScale -> 5.0 을 했으므로 5배 커짐.

android : fillAfter -> 동작 종료 후 마지막 형태를 고정.

***
**3. Translate(이동)**

 res 디렉토리에서  -> anim 디렉토리 생성 - trans.xml 추가.
```java
<translate
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:fromXDelta="0"
    android:fromYDelta="0"
    android:toXDelta="100"
    android:toYDelta="300"
    android:duration="3000"
    >
</translate>
```
android : fromXDelta & fromYDelta -> 시작 좌표

android : toXDelta & toYDelta -> 끝 좌표

***
**4. Alpha(투명화)**

 res 디렉토리에서  -> anim 디렉토리 생성 - trans.xml 추가.

```java
<alpha
    xmlns:android="http://schemas.android.com/apk/res/android"
        android:fromAlpha="1.0"
        android:toAlpha="0.0"
        android:fillAfter="true"
        android:duration="3000">
</alpha>
```
android : fromAlpha -> 시작 값이 1이므로 본래 뷰 상태.

android : toAlpha -> 동작의 끝난 시점의 투명도, 0 이므로 동작이 끝나면 뷰가 보이지 않는다.

***
4가지 트윈애니메이션을 컨트롤 하는 class

```java
@Override
    public void onClick(View v) {

        Animation animation = null;
        switch (v.getId()) {
            case R.id.btnTrans:
                animation = AnimationUtils.loadAnimation(this, R.anim.trans);
                break;
            case R.id.btnRotate:
                animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
                break;
            case R.id.btnScale:
                animation = AnimationUtils.loadAnimation(this, R.anim.scale);
                break;
            case R.id.btnAlpha:
                animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
                break;
            case R.id.imageView:
                Intent intent = new Intent(this, WindmillActivity.class);
                startActivity(intent);
                break;
        }
        if (animation != null) {
            imageView.startAnimation(animation);
        }
    }
```
Animation 객체인 animation을 선언하고, 행위를 정의한 버튼 클릭시, loadAnimation을 통해 anim 디렉토리에 정의한 행위를 가져온다. 

animation 객체가 비어있지 않다면, startAnimation( ) 메서드를 통해 실행한다.

***
**5. Property Animation**

ObjectAnimator 객체에 여러 속성들을 추가하여 복합적인 액션을 수행 할 수 있다.

```java
 @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnProp:
                ObjectAnimator transY = ObjectAnimator.ofFloat(
                        btnRed,         // 움직일 대상
                        "translationY", //애니메이션 속성
                        -500             // 속성 값
                );
                ObjectAnimator transX = ObjectAnimator.ofFloat(
                        btnRed,         // 움직일 대상
                        "translationX", //애니메이션 속성
                        300             // 속성 값
                );
                ObjectAnimator rotate = ObjectAnimator.ofFloat(
                        btnRed,         // 움직일 대상
                        "rotation", //애니메이션 속성
                        1440             // 속성 값
                );
                // 애니메이터 셋을 구성해서 실행한다.
                AnimatorSet aniSet = new AnimatorSet();
                aniSet.playTogether(transX,transY,rotate); // 개수의 제한이 없슴
                aniSet.setDuration(3000);           // 애니메이터 셋의 실행 시간
                // 처음엔 빨리 점점 느리게
                aniSet.setInterpolator(new AccelerateDecelerateInterpolator());
                aniSet.start();
                break;
        }
    }
```
 ObjectAnimator.ofFloat( ) 은 3가지 파라미터를 전달 받는다.

1) 움직일 대상

2) 애니메이션 속성

3) 속성 값

AnimatorSet 객체를 통해 여러 액션을 복합접으로 수행 가능하며, 속성도 부여할 수 있다.
