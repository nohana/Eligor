Eligor
======

Thin wrapper for managing periodic sync with ContentResolver.

## Usage

### Initialize

Initialize `Eligor` at `Application#onCreate()` in your implementation.

```java
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Eligor.initialize(1000); // sync period is set by default as every 1sec

        Eligor.getInstance().registerPeriodicSyncManager(new PeriodicSyncManager(new Account("account_name", "account_type"), "authority")); // register manager
    }

    @Override
    public void onTerminate() {
        Eligor.destroy();
        super.onTerminate();
    }
}
```

### Add manager

```java
public class MyActivity extends Activity {
    private final Eligor mEligor = Eligor.getInstance(); // you may inject the instance by DI container using Provider.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sample)
    }

    public void onSyncButtonClick(View view) {
        mEligor.requestSync(); // start provider sync manually
    }
}
```

## Download

Via gradle.

```groovy
dependencies {
    compile 'jp.co.nohana:Eligor:1.2.0'
}
```

## License

```
Copyright (C) 2014 nohana, Inc. All rights reserved.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use
this file except in compliance with the License. You may obtain a copy of the
License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.
```
