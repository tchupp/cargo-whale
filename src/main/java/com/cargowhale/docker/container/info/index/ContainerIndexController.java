package com.cargowhale.docker.container.info.index;

import com.cargowhale.docker.client.containers.ContainerState;
import com.cargowhale.docker.container.ContainerEnumConverter;
import com.cargowhale.docker.container.info.ContainerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/containers")
public class ContainerIndexController {

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(ContainerState.class, new ContainerEnumConverter());
    }

    private final ContainerInfoService service;
    private final ContainerIndexResourceAssembler indexResourceAssembler;

    @Autowired
    public ContainerIndexController(final ContainerInfoService service, final ContainerIndexResourceAssembler indexResourceAssembler) {
        this.service = service;
        this.indexResourceAssembler = indexResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ContainerIndexResource listContainers() {
        ContainerIndex summaryIndex = this.service.getAllContainers();
        return this.indexResourceAssembler.toResource(summaryIndex);
    }

    @RequestMapping(method = RequestMethod.GET,
            params = "state",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ContainerIndexResource listContainers(final StateFilters stateFilters) {
        ContainerIndex summaryIndex = this.service.getContainersFilterByStatus(stateFilters.getState());
        return this.indexResourceAssembler.toResource(summaryIndex);
    }
}
