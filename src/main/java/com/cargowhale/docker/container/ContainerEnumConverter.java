package com.cargowhale.docker.container;

import com.cargowhale.docker.container.info.ContainerState;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class ContainerEnumConverter extends PropertyEditorSupport {

    @Override
    public void setAsText(final String state) throws IllegalArgumentException {
        setValue(ContainerState.valueOf(state.toUpperCase()));
    }
}