package nurisezgin.com.rxtrash;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public final class RxTrash {

    Map<String, List<Disposable>> disposableMap = new LinkedHashMap<>();
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
            if (!disposableMap.containsKey(tag)) {
                disposableMap.put(tag, new ArrayList<Disposable>());
            }

            disposableMap.get(tag).add(d);
        }

        return this;
    }

    public void clear() {
        disposables.clear();
        clear(new Filter.AllFilter());
    }

    public void clear(Filter filter) {
        synchronized (disposableMap) {
            for (Iterator<Map.Entry<String, List<Disposable>>> it = disposableMap.entrySet().iterator();
                 it.hasNext(); ) {

                Map.Entry<String, List<Disposable>> entry = it.next();

                String key = entry.getKey();
                List<Disposable> disposables = entry.getValue();

                for (Iterator<Disposable> itDisposable = disposables.iterator(); itDisposable.hasNext(); ) {
                    final Disposable next = itDisposable.next();

                    if (shouldRemoveUnusedDisposable(next)) {
                        itDisposable.remove();
                    } else {
                        boolean isFiltered = filter.apply(key, next);

                        if (isFiltered) {
                            next.dispose();
                            itDisposable.remove();
                        }
                    }
                }

                if (disposables.size() == 0) {
                    it.remove();
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
