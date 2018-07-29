# RxTrash
Keep disposables with custom tag names. You can dispose each disposable or disposable groups with recorded tag name when you want.

## Prerequisites
First, dependency must be added to build.gradle file.
```groovy
implementation 'nurisezgin.com.rxtrash:rxtrash:1.0.0'
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
Copyright 2018 Nuri SEZGIN

Apache License 2.0