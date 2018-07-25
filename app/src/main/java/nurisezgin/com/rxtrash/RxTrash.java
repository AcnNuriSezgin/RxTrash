package nurisezgin.com.rxtrash;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RxTrash {

    Map<String, Disposable> disposableMap = new LinkedHashMap<>();
    CompositeDisposable disposables = new CompositeDisposable();

    private RxTrash() { }

    public static RxTrash getInstance() {
        return CREATOR.INSTANCE;
    }

    public RxTrash add(Disposable d) {
        disposables.add(d);
        return this;
    }

    public RxTrash add(String tag, Disposable d) {
        synchronized (disposableMap) {
            disposableMap.put(tag, d);
        }
        return this;
    }

    public void clear() {
        disposables.clear();
        clear(new Filter.AllFilter());
    }

    public void clear(Filter filter) {
        synchronized (disposableMap) {
            for (Iterator<Map.Entry<String, Disposable>> it = disposableMap.entrySet().iterator();
                 it.hasNext(); ) {

                Map.Entry<String, Disposable> entry = it.next();

                String key = entry.getKey();
                Disposable disposable = entry.getValue();

                if (shouldRemoveUnusedDisposable(disposable)) {
                    it.remove();
                } else {
                    boolean isFiltered = filter.apply(key, disposable);

                    if (isFiltered) {
                        disposable.dispose();
                        it.remove();
                    }
                }
            }
        }
    }

    private boolean shouldRemoveUnusedDisposable(Disposable disposable) {
        return disposable == null || disposable.isDisposed();
    }

    private static final class CREATOR {
        private static final RxTrash INSTANCE = new RxTrash();
    }
}
