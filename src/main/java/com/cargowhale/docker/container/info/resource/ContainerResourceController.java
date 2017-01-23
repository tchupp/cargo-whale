package com.cargowhale.docker.container.info.resource;

import com.cargowhale.docker.exception.CargoWhaleErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.cargowhale.docker.container.info.resource.ListContainersParam.allContainers;
import static java.util.Arrays.stream;

@RestController
@RequestMapping("/api/containers")
public class ContainerResourceController {

    private final ContainerResourceService service;
    private final ContainerIndexResourceAssembler resourceAssembler;
    private final ContainerResourceProcessor resourceProcessor;

    @Autowired
    public ContainerResourceController(final ContainerResourceService service, final ContainerIndexResourceAssembler resourceAssembler, final ContainerResourceProcessor resourceProcessor) {
        this.service = service;
        this.resourceAssembler = resourceAssembler;
        this.resourceProcessor = resourceProcessor;
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(ContainerState.class, new ContainerEnumConverter());
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CargoWhaleErrorMessage> handleBadFilter(final HttpServletRequest request, final MethodArgumentTypeMismatchException ex) {
        CargoWhaleErrorMessage errorMessage = new CargoWhaleErrorMessage(request.getRequestURI(), "Bad Filter", ex.getClass().toString());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.GET,
        produces = MediaTypes.HAL_JSON_VALUE)
    public ContainerIndexResource listContainers() {
        List<ContainerResource> containerResources = this.service.getContainers(allContainers());

        return this.resourceAssembler.toResource(containerResources);
    }

    @RequestMapping(method = RequestMethod.GET,
        params = "state",
        produces = MediaTypes.HAL_JSON_VALUE)
    public ContainerIndexResource listContainers(@RequestParam final ContainerState... state) {
        ListContainersParam[] params = stream(state)
            .map(ListContainersParam::state)
            .toArray(ListContainersParam[]::new);

        List<ContainerResource> containerResources = this.service.getContainers(params);

        return this.resourceAssembler.toResource(containerResources);
    }

    @RequestMapping(value = "/{id}",
        method = RequestMethod.GET,
        produces = MediaTypes.HAL_JSON_VALUE)
    public ContainerResource inspectContainer(@PathVariable final String id) {
        ContainerResource containerResource = this.service.getContainer(id);
        return this.resourceProcessor.process(containerResource);
    }
}
