package ru.artemaa.shaurma.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.artemaa.shaurma.entity.jpa.Shaurma;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login");
    }

    @Bean
    public ResourceProcessor<PagedResources<Resource<Shaurma>>> shaurmaProcessor(EntityLinks entityLinks) {
        return new ResourceProcessor<PagedResources<Resource<Shaurma>>>() {
            @Override
            public PagedResources<Resource<Shaurma>> process(PagedResources<Resource<Shaurma>> resources) {
                resources.add(
                        entityLinks.linkFor(Shaurma.class)
                                .slash("recent")
                                .withRel("recents")
                );
                return resources;
            }
        };
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create("http://localhost:9090");
    }
}
