package support;

import reactor.core.Disposable;

import java.util.List;

import static java.util.function.Predicate.not;

public class Wait {

    public static void untilAllAreCompleted(final List<Disposable> disposables) {
        var failed = false;
        while (disposables.stream().anyMatch(not(Disposable::isDisposed)) || failed) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                failed = true;
            }
        }
    }
}
