package ru.artemaa.shaurma.reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

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

    @Test
    public void testMerge() {
        Flux<String> characterFlux = Flux
                .just("Garfield", "Kojak", "Barbossa")
                .delayElements(Duration.ofMillis(500));
        Flux<String> foodFlux = Flux
                .just("Lasagna", "Lollipops", "Apples")
                .delaySubscription(Duration.ofMillis(250))
                .delayElements(Duration.ofMillis(500));

        Flux<String> flux = characterFlux.mergeWith(foodFlux);
        StepVerifier.create(flux)
                .expectNext("Garfield")
                .expectNext("Lasagna")
                .expectNext("Kojak")
                .expectNext("Lollipops")
                .expectNext("Barbossa")
                .expectNext("Apples")
                .verifyComplete();
    }

    @Test
    public void testZip() {
        Flux<String> characterFlux = Flux
                .just("Garfield", "Kojak", "Barbossa");
        Flux<String> foodFlux = Flux
                .just("Lasagna", "Lollipops", "Apples");

        Flux<Tuple2<String, String>> flux = Flux.zip(characterFlux, foodFlux);

        StepVerifier.create(flux)
                .expectNextMatches(pair ->
                        pair.getT1().equals("Garfield") && pair.getT2().equals("Lasagna"))
                .expectNextMatches(pair ->
                        pair.getT1().equals("Kojak") && pair.getT2().equals("Lollipops"))
                .expectNextMatches(pair ->
                        pair.getT1().equals("Barbossa") && pair.getT2().equals("Apples"))
                .verifyComplete();
    }

    @Test
    public void testZipFunction() {
        Flux<String> characterFlux = Flux
                .just("Garfield", "Kojak", "Barbossa");
        Flux<String> foodFlux = Flux
                .just("Lasagna", "Lollipops", "Apples");

        Flux<String> flux = Flux.zip(characterFlux, foodFlux, (c, f) -> c + " eats " + f);
        StepVerifier.create(flux)
                .expectNext("Garfield eats Lasagna")
                .expectNext("Kojak eats Lollipops")
                .expectNext("Barbossa eats Apples")
                .verifyComplete();
    }

    @Test
    public void testFirst() {
        Flux<String> slowFlux = Flux
                .just("tortoise", "snail", "sloth")
                .delaySubscription(Duration.ofMillis(100));
        Flux<String> fastFlux = Flux
                .just("hare", "cheetah", "squirrel");

        Flux<String> flux = Flux.first(slowFlux, fastFlux);
        StepVerifier.create(flux)
                .expectNext("hare")
                .expectNext("cheetah")
                .expectNext("squirrel")
                .verifyComplete();
    }
}
