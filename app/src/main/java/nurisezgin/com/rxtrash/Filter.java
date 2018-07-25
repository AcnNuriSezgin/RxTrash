package nurisezgin.com.rxtrash;

import io.reactivex.disposables.Disposable;

public interface Filter {

    boolean apply(String tag, Disposable d);

    class NameFilter implements Filter {

        private final String expectedName;

        public NameFilter(String expectedName) {
            this.expectedName = expectedName;
        }

        @Override
        public boolean apply(String tag, Disposable d) {
            return expectedName.equals(tag);
        }
    }

    class AllFilter implements Filter {

        @Override
        public boolean apply(String tag, Disposable d) {
            return true;
        }
    }

}

