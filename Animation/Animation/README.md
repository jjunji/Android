# Animation

�ȵ���̵� �ִϸ��̼��� ũ�� 2������ �з� �� �� �ִ�.

1. Tween Animation

2. Property Animation



Tween Animation �� �信 ���� �ִϸ��̼��� ������ xml�� �δ� Directory�̸�, ȸ��, ������ ��ȭ, ����ȭ, �̵� ���� �����Ѵ�. (Resource type : anim)

Property Animation �� ��ü ������ ���� ������ �ִϸ��̼��� �����ϴ� Directory, anim�� �־ ����� ����. (Resource type : animator)

**Animation ����.**

* Rotate
* Scale
* Translate
* Alpha
* Property Animation

***
**1. Rotate(ȸ��)**

res ���丮����  -> anim ���丮 ���� - rotate.xml �߰�.

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
pivotX,Y �� 50%�� ���� ������ �䰡 ���ڸ����� ���� �ʴ´�.

������ ȸ���ϴ� �߽����� �̹��� ���� ����� �������� �ϴ� ���� �ƴ϶� �̹��� �並 ���ΰ��ִ� �簢�� ����� ���� ������� ���ֱ� ����.



android:fromDegrees & toDegrees -> ȸ���� ���� ������ ������ ������ �ǹ��Ѵ�. 720 -> 2���� ȸ��.

android:duration : �и� ��. 3000 -> 3��

***
**2. Scale(������ ��ȭ)**

res ���丮����  -> anim ���丮 ���� - scale.xml �߰�.
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
android : fromXScale & fromYScalse -> 1.0 �̹Ƿ� ���� ���� ũ�⸦ �ǹ��Ѵ�.

android : toXScale & toYScale -> 5.0 �� �����Ƿ� 5�� Ŀ��.

android : fillAfter -> ���� ���� �� ������ ���¸� ����.

***
**3. Translate(�̵�)**

 res ���丮����  -> anim ���丮 ���� - trans.xml �߰�.
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
android : fromXDelta & fromYDelta -> ���� ��ǥ

android : toXDelta & toYDelta -> �� ��ǥ

***
**4. Alpha(����ȭ)**

 res ���丮����  -> anim ���丮 ���� - trans.xml �߰�.

```java
<alpha
    xmlns:android="http://schemas.android.com/apk/res/android"
        android:fromAlpha="1.0"
        android:toAlpha="0.0"
        android:fillAfter="true"
        android:duration="3000">
</alpha>
```
android : fromAlpha -> ���� ���� 1�̹Ƿ� ���� �� ����.

android : toAlpha -> ������ ���� ������ ����, 0 �̹Ƿ� ������ ������ �䰡 ������ �ʴ´�.

***
4���� Ʈ���ִϸ��̼��� ��Ʈ�� �ϴ� class

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
Animation ��ü�� animation�� �����ϰ�, ������ ������ ��ư Ŭ����, loadAnimation�� ���� anim ���丮�� ������ ������ �����´�. 

animation ��ü�� ������� �ʴٸ�, startAnimation( ) �޼��带 ���� �����Ѵ�.

***
**5. Property Animation**

ObjectAnimator ��ü�� ���� �Ӽ����� �߰��Ͽ� �������� �׼��� ���� �� �� �ִ�.

```java
 @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnProp:
                ObjectAnimator transY = ObjectAnimator.ofFloat(
                        btnRed,         // ������ ���
                        "translationY", //�ִϸ��̼� �Ӽ�
                        -500             // �Ӽ� ��
                );
                ObjectAnimator transX = ObjectAnimator.ofFloat(
                        btnRed,         // ������ ���
                        "translationX", //�ִϸ��̼� �Ӽ�
                        300             // �Ӽ� ��
                );
                ObjectAnimator rotate = ObjectAnimator.ofFloat(
                        btnRed,         // ������ ���
                        "rotation", //�ִϸ��̼� �Ӽ�
                        1440             // �Ӽ� ��
                );
                // �ִϸ����� ���� �����ؼ� �����Ѵ�.
                AnimatorSet aniSet = new AnimatorSet();
                aniSet.playTogether(transX,transY,rotate); // ������ ������ ����
                aniSet.setDuration(3000);           // �ִϸ����� ���� ���� �ð�
                // ó���� ���� ���� ������
                aniSet.setInterpolator(new AccelerateDecelerateInterpolator());
                aniSet.start();
                break;
        }
    }
```
 ObjectAnimator.ofFloat( ) �� 3���� �Ķ���͸� ���� �޴´�.

1) ������ ���

2) �ִϸ��̼� �Ӽ�

3) �Ӽ� ��

AnimatorSet ��ü�� ���� ���� �׼��� ���������� ���� �����ϸ�, �Ӽ��� �ο��� �� �ִ�.
