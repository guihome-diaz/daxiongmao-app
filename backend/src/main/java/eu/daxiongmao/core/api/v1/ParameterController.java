package eu.daxiongmao.core.api.v1;

import eu.daxiongmao.core.api.error.ApiValidationError;
import eu.daxiongmao.core.business.ParameterService;
import eu.daxiongmao.core.model.dto.ParameterDTO;
import eu.daxiongmao.core.model.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Web-service (HTTP REST) to interact with application's parameters
 * @author Guillaume Diaz
 * @version 1.0
 * @since 2020/03
 */
@RestController
@RequestMapping(path = "/v1/parameters")
@RequiredArgsConstructor
public class ParameterController {

    private final ParameterService parameterService;

    @GetMapping("/getAll")
    public List<ParameterDTO> getAll() {
        return parameterService.getAll(false, false);
    }

    @GetMapping("/getByName/{paramName}")
    public Optional<ParameterDTO> getParamByName(@PathVariable String paramName) {
        if (StringUtils.isBlank(paramName)) {
            throw new BadRequestException(new ApiValidationError(ParameterDTO.class.getName(), "paramName", paramName, "cannot be blank"));
        }

        return parameterService.getParamByName(paramName, false, false);
    }

}
