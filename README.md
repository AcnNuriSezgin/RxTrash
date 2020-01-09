# RxTrash
Keep disposables with custom tag names. You can dispose each disposable or disposable groups with recorded tag name when you want.

## Prerequisites
First, dependency must be added to build.gradle file.
```groovy
implementation 'nurisezgin.com.rxtrash:rxtrash:1.0.3'
```

## How To Use
* Use RxTrash Singleton Object
```java
    String tag = "MainFragment";
    Disposable d = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> ..., throwable -> ...);

    RxTrash.getInstance()
        .add(tag, d);
```
* Dispose All
```java
    RxTrash.getInstance()
        .clear(new Filter.AllFilter());
```
* Dispose With Tag Name
```java
    RxTrash.getInstance()
        .clear(new Filter.NameFilter(TAG));
```
* Dispose With Custom Strategy
```java
    RxTrash.getInstance()
        .clear((tag, disposable) -> tag.endsWith("Fragment"));
```

## Authors
* **Nuri SEZGIN**-[Email](acnnurisezgin@gmail.com)

## Licence

```
Copyright 2018 Nuri SEZGİN

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```