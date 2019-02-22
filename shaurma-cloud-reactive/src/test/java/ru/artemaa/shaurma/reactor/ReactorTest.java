package ru.artemaa.shaurma.reactor;

import lombok.Data;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    @Test
    public void testSkip() {
        Flux<String> flux = Flux.just("one", "two", "three", "four", "five")
                .skip(3);

        StepVerifier.create(flux)
                .expectNext("four", "five")
                .verifyComplete();
    }

    @Test
    public void testSkipDuration() {
        Flux<String> flux = Flux.just("one", "two", "three", "four", "five")
                .delayElements(Duration.ofSeconds(1))
                .skip(Duration.ofSeconds(4));

        StepVerifier.create(flux)
                .expectNext("four", "five")
                .verifyComplete();
    }

    @Test
    public void testTake() {
        Flux<String> flux = Flux.just("one", "two", "three", "four", "five")
                .take(3);

        StepVerifier.create(flux)
                .expectNext("one", "two", "three")
                .verifyComplete();
    }

    @Test
    public void testTakeDuration() {
        Flux<String> flux = Flux.just("one", "two", "three", "four", "five")
                .delayElements(Duration.ofSeconds(1))
                .take(Duration.ofMillis(3500));

        StepVerifier.create(flux)
                .expectNext("one", "two", "three")
                .verifyComplete();
    }

    @Test
    public void testFilter() {
        Flux<String> flux = Flux.just("one", "two", "twenty three", "forty four", "five")
                .filter(s -> !s.contains(" "));

        StepVerifier.create(flux)
                .expectNext("one", "two", "five")
                .verifyComplete();
    }

    @Test
    public void testDistinct() {
        Flux<String> flux = Flux.just("one", "two", "three", "one", "four", "five", "two")
                .distinct();

        StepVerifier.create(flux)
                .expectNext("one", "two", "three", "four", "five")
                .verifyComplete();
    }

    @Data
    private class Player {
        private final String firstName;
        private final String lastName;
    }

    @Test
    public void testMap() {
        Flux<Player> flux = Flux
                .just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
                .map(name -> {
                    String[] split = name.split(" ");
                    return new Player(split[0], split[1]);
                });

        StepVerifier.create(flux)
                .expectNext(new Player("Michael", "Jordan"))
                .expectNext(new Player("Scottie", "Pippen"))
                .expectNext(new Player("Steve", "Kerr"))
                .verifyComplete();
    }

    @Test
    public void testFlatMap() {
        Flux<Player> flux = Flux
                .just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
                .flatMap(s -> Mono.just(s)
                        .map(name -> {
                            String[] split = name.split(" ");
                            return new Player(split[0], split[1]);
                        })
                        .subscribeOn(Schedulers.parallel())
                        .log()
                );

        List<Player> players = asList(
                new Player("Michael", "Jordan"),
                new Player("Scottie", "Pippen"),
                new Player("Steve", "Kerr")
        );

        StepVerifier.create(flux)
                .expectNextMatches(players::contains)
                .expectNextMatches(players::contains)
                .expectNextMatches(players::contains)
                .verifyComplete();
    }

    @Test
    public void testBuffer() {
        Flux<List<String>> flux = Flux.fromArray(fruits)
                .buffer(3);

        StepVerifier.create(flux)
                .expectNext(Arrays.asList(fruits[0], fruits[1], fruits[2]))
                .expectNext(Arrays.asList(fruits[3], fruits[4]))
                .verifyComplete();

        flux
                .flatMap(fruitList -> Flux.fromIterable(fruitList)
                        .map(String::toUpperCase)
                        .subscribeOn(Schedulers.parallel())
                        .log()
                ).subscribe();
    }

    @Test
    public void testCollectList() {
        Mono<List<String>> collectList = Flux.fromArray(fruits)
                .collectList();

        StepVerifier.create(collectList)
                .expectNext(asList(fruits))
                .verifyComplete();
    }

    @Test
    public void testCollectMap() {
        Mono<Map<Character, String>> collectMap = Flux.fromArray(fruits)
                .collectMap(elem -> elem.charAt(0));

        StepVerifier.create(collectMap)
                .expectNextMatches(map -> map.size() == 5)
                .verifyComplete();
    }

    @Test
    public void testAll() {
        Flux<String> flux = Flux.fromArray(fruits)
                .map(String::toLowerCase);

        Mono<Boolean> monoTrue = flux
                .all(fruit -> fruit.contains("a"));
        StepVerifier.create(monoTrue)
                .expectNext(true)
                .verifyComplete();

        Mono<Boolean> monoFalse = flux
                .all(fruit -> fruit.contains("A"));
        StepVerifier.create(monoFalse)
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    public void testAny() {
        Flux<String> flux = Flux.fromArray(fruits);

        Mono<Boolean> monoTrue = flux.any(fruit -> fruit.contains("b"));
        StepVerifier.create(monoTrue)
                .expectNext(true)
                .verifyComplete();

        Mono<Boolean> monoFalse = flux.any(fruit -> fruit.contains("W"));
        StepVerifier.create(monoFalse)
                .expectNext(false)
                .verifyComplete();
    }
}
