package ru.artemaa.shaurma.reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;

import static java.util.Arrays.asList;

public class ReactorTest {
    private static final String[] fruits = {"Apple", "Orange", "Grape", "Banana", "Strawberry"};

    @Test
    public void testFluxFromArray() {
        Flux<String> flux = Flux.fromArray(fruits);
        assertFruitFlux(flux);
    }

    @Test
    public void testFluxFromIterable() {
        Flux<String> flux = Flux.fromIterable(asList(fruits));
        assertFruitFlux(flux);
    }

    @Test
    public void testFluxFromStream() {
        Flux<String> flux = Flux.fromStream(Arrays.stream(fruits));
        assertFruitFlux(flux);
    }

    private void assertFruitFlux(Flux<String> flux) {
        StepVerifier.create(flux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();
    }

    @Test
    public void testFluxRange() {
        Flux<Integer> flux = Flux.range(1, 5);
        StepVerifier.create(flux)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .expectNext(5)
                .verifyComplete();
    }

    @Test
    public void testFluxInterval() {
        Flux<Long> flux = Flux.interval(Duration.ofSeconds(1))
                .take(5);
        StepVerifier.create(flux)
                .expectNext(0L)
                .expectNext(1L)
                .expectNext(2L)
                .expectNext(3L)
                .expectNext(4L)
                .verifyComplete();
    }


}
