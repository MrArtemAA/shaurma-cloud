package ru.artemaa.shaurma.web.api.hateoas;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import ru.artemaa.shaurma.entity.jpa.Shaurma;
import ru.artemaa.shaurma.web.api.DesignShaurmaRestController;

@Component
public class ShaurmaResourceAssembler extends ResourceAssemblerSupport<Shaurma, ShaurmaResource> {
    public ShaurmaResourceAssembler() {
        super(DesignShaurmaRestController.class, ShaurmaResource.class);
    }

    @Override
    protected ShaurmaResource instantiateResource(Shaurma shaurma) {
        return new ShaurmaResource(shaurma);
    }

    @Override
    public ShaurmaResource toResource(Shaurma shaurma) {
        return createResourceWithId(shaurma.getId(), shaurma);
    }
}
