# ParseLiveQuery-Android
## Documentation

### Installation
---
Step 1. Add the JitPack repository to your build file 

```gradle
	allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
```

Step 2. Add the dependency

```gradle
	dependencies {
          implementation 'com.github.rommansabbir:ParseLiveQuery-Android:Tag'
	}
```

---

### Version available

| Releases        
| ------------- |
| v1.0.1        |
| v1.0          |


# Usages

### For Java: 

```java
public class MainActivity extends AppCompatActivity implements ParseLiveQuery.ParseLiveQueryInterface {
    private ParseLiveQuery parseLiveQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Instantiate ParseLiveQuery
         */
        parseLiveQuery = new ParseLiveQuery(this);

        /**
         * Create a usual parse query, add some constraint if you want
         * Don't execute the query
         */
        ParseQuery<ParseObject> liveQuery = ParseQuery.getQuery("CLASS_NAME");
        liveQuery.whereGreaterThan("AGE", 25);

        /**
         * Add live event listener to your query
         * Provide the name of your app, pass the liveQuery to the live event listener
         * @APP_NAME
         * @liveQuery
         */
        parseLiveQuery.addLiveEventListener("YOUR_APP_NAME_HERE", liveQuery);

    }

    @Override
    public void onEventListenerSuccess(ParseObject retrievedObject) {
        //TODO implement your logic here

    }

    @Override
    public void onEventListenerFailure(String errorMessage) {
        //TODO implement your logic here
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * Destroy the callback after it usages
         */
        parseLiveQuery.destroyCallback();
    }
}
```

### Contact me
[Portfolio](https://www.rommansabbir.com/) | [LinkedIn](https://www.linkedin.com/in/rommansabbir/) | [Twitter](https://www.twitter.com/itzrommansabbir/) | [Facebook](https://www.facebook.com/itzrommansabbir/)

