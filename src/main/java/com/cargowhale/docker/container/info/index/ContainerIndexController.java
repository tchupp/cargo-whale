package com.cargowhale.docker.container.info.index;

import com.cargowhale.docker.client.containers.ContainerState;
import com.cargowhale.docker.container.ContainerEnumConverter;
import com.cargowhale.docker.container.info.ContainerInfoService;
import com.cargowhale.docker.exception.CargoWhaleErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/containers")
public class ContainerIndexController {

    private final ContainerInfoService service;
    private final ContainerIndexResourceAssembler indexResourceAssembler;

    @Autowired
    public ContainerIndexController(final ContainerInfoService service, final ContainerIndexResourceAssembler indexResourceAssembler) {
        this.service = service;
        this.indexResourceAssembler = indexResourceAssembler;
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
        ContainerIndex summaryIndex = this.service.getAllContainers();
        return this.indexResourceAssembler.toResource(summaryIndex);
    }

    @RequestMapping(method = RequestMethod.GET,
        params = "state",
        produces = MediaTypes.HAL_JSON_VALUE)
    public ContainerIndexResource listContainers(final StateFilters stateFilters) {
        ContainerIndex summaryIndex = this.service.getContainersFilterByStatus(stateFilters.getState());
        return this.indexResourceAssembler.toResource(summaryIndex);
    }
}
