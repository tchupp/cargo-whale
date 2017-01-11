package com.cargowhale.docker.container.info.index;

import com.cargowhale.docker.client.containers.ContainerState;
import com.cargowhale.docker.container.ContainerEnumConverter;
import com.cargowhale.docker.exception.CargoWhaleErrorMessage;
import com.cargowhale.docker.index.IndexController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/containers")
public class ContainerIndexController {

    private final ContainerIndexService service;

    @Autowired
    public ContainerIndexController(final ContainerIndexService service) {
        this.service = service;
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(ContainerState.class, new ContainerEnumConverter());
    }

    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<CargoWhaleErrorMessage> handleBadFilter(final HttpServletRequest request, final BindException ex) {
        CargoWhaleErrorMessage errorMessage = new CargoWhaleErrorMessage(request.getRequestURI(), "Bad Filter", ex.getClass().toString());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.GET,
        produces = MediaTypes.HAL_JSON_VALUE)
    public ContainerIndexResource listContainers() {
        ContainerIndexResource containerIndex = this.service.getContainerIndex();

        containerIndex.add(linkTo(methodOn(IndexController.class).index()).withRel("up"));
        containerIndex.add(linkTo(methodOn(ContainerIndexController.class).listContainers()).withSelfRel());

        return containerIndex;
    }

    @RequestMapping(method = RequestMethod.GET,
        params = "filters",
        produces = MediaTypes.HAL_JSON_VALUE)
    public ContainerIndexResource listContainers(@RequestParam("filters") final ContainerState[] filters) {
        ContainerIndexResource containerIndex = this.service.getContainerIndex(filters);

        containerIndex.add(linkTo(methodOn(IndexController.class).index()).withRel("up"));
        containerIndex.add(linkTo(methodOn(ContainerIndexController.class).listContainers()).withSelfRel());

        return containerIndex;
    }
}
