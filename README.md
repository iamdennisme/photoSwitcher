# dependencies
```Groovy
 allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
	dependencies {
	        compile 'com.github.iamdennisme:photoSwitcher:1.1.0'
	}
```
# photoSwitcher

fun setCount(count: Int)  max count

fun getPhoto 

# step1

```kotlin
PhotoSwitcher.getInstance(this).setCount(9).getPhoto(CODE) 
```

# step2

```kotlin 
 override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        PhotoSwitcher.dealWith(requestCode, resultCode, data, CODE, object : PhotoResultListener {
            override fun failure() {

            }

            override fun result(result: List<String>) {
                result.map {
                    Log.d("result", it)

                }
            }
        })
    }
```
