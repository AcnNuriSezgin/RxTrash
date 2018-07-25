package nurisezgin.com.rxtrash;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.disposables.Disposable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RxTrashTest {

    private static final String TAG = RxTrashTest.class.getSimpleName();

    @Mock
    Disposable mockDisposable;

    @Before
    public void setUp_() {
        releaseInstances();
    }

    @After
    public void tearDown_() {
        releaseInstances();
    }

    private void releaseInstances() {
        RxTrash.getInstance().clear();
    }

    @Test
    public void should_AddDisposableCorrect() {
        final int expected = 1;

        RxTrash instance = RxTrash.getInstance();
        instance.add(mockDisposable);

        int actual = instance.disposables.size();

        assertThat(actual, is(expected));
    }

    @Test
    public void should_AddDisposableWithTagCorrect() {
        final int expected = 1;

        RxTrash instance = RxTrash.getInstance();
        instance.add(TAG, mockDisposable);

        int actual = instance.disposableMap.size();

        assertThat(actual, is(expected));
    }

    @Test
    public void should_ClearDisposablesCorrect() {
        final int expected = 0;

        RxTrash instance = RxTrash.getInstance();
        instance.add(mockDisposable);
        instance.clear();

        int actual = instance.disposables.size();

        assertThat(actual, is(expected));
    }

    @Test
    public void should_ClearDisposablesWithFilterCorrect() {
        final int expected = 0;

        RxTrash instance = RxTrash.getInstance();
        instance.add(TAG, mockDisposable);
        instance.clear(new Filter.NameFilter(TAG));

        int actual = instance.disposableMap.size();

        assertThat(actual, is(expected));
    }

    @Test
    public void should_ClearUnusableDisposablesCorrect() {
        final String newTag = TAG + TAG;

        final int expected = 0;

        when(mockDisposable.isDisposed())
                .thenReturn(true);

        RxTrash instance = RxTrash.getInstance();
        instance.add(newTag, mockDisposable);
        instance.clear(new Filter.NameFilter(TAG));

        int actual = instance.disposableMap.size();

        assertThat(actual, is(expected));
    }

}