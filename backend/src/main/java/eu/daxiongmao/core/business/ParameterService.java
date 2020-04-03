package eu.daxiongmao.core.business;

import eu.daxiongmao.core.business.cache.InnerCache;
import eu.daxiongmao.core.dao.ParameterRepository;
import eu.daxiongmao.core.model.db.Parameter;
import eu.daxiongmao.core.model.dto.ParameterDTO;
import eu.daxiongmao.core.model.enums.param.IParameterEnum;
import eu.daxiongmao.core.model.exception.UnauthorizedException;
import eu.daxiongmao.core.model.mapper.ParameterMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * To interact with application's settings.
 * @author Guillaume Diaz
 * @version 1.0 - 2020/01
 */
@Service
@Log4j2
public class ParameterService {

    /** Delay to respect between 2 cache refresh, in seconds. This prevents multi-threads issues */
    private final static long DELAY_BETWEEN_REFRESH_IN_SECONDS = 30;

    private final ParameterRepository parameterRepository;
    private final ParameterMapper parameterMapper;
    private InnerCache<String, Parameter> cache;


    private static ParameterService instance;
    /**
     * @return service's instance (singleton pattern)
     */
    public static Optional<ParameterService> getInstance() {
        return Optional.ofNullable(instance);
    }

    @Autowired
    public ParameterService(ParameterRepository parameterRepository, ParameterMapper parameterMapper) {
        this.parameterRepository = parameterRepository;
        this.parameterMapper = parameterMapper;
        instance = this;
    }

    @PostConstruct
    public void setup() {
        // function to populate cache
        cache = new InnerCache<>(log, DELAY_BETWEEN_REFRESH_IN_SECONDS, () -> {
            // Get DB values
            final List<Parameter> dbValues = parameterRepository.findAll();
            // Update local cache
            final Map<String, Parameter> valuesToCache = new HashMap<>(dbValues.size());
            if (!dbValues.isEmpty()) {
                dbValues.forEach((dbParam) -> {
                    // Each param has an unique name, make it toUpperCase() for case insensitive search
                    valuesToCache.put(dbParam.getParamName().toUpperCase(), dbParam);
                });
            }
            log.info("Initialization complete | {} Parameters have been cached in memory", dbValues.size());
            return valuesToCache;
        });
        // cache DB values on startup
        cache.updateCache(true);
    }


    /**
     * To retrieve a parameter VALUE by its name.
     * This will use the local cache
     * @param param search parameter
     * @return corresponding value or null
     */
    public Optional<Object> getValue(final IParameterEnum param) {
        return getValueByName(param.getParamName(), false, false);
    }

    /**
     * To retrieve a parameter VALUE by its name.
     * This will use the local cache
     * @param paramName search parameter
     * @param viewSensitiveParam boolean, "true" to view sensitive parameters ; "false" to hide sensitive parameters such as password. This is highly recommended for controllers
     * @param viewDisabledParam boolean, "true" to view disable parameters ; "false" to only show ACTIVE parameters
     * @return corresponding value or null
     */
    public Optional<Object> getValueByName(final String paramName, boolean viewSensitiveParam, boolean viewDisabledParam) {
        Optional<ParameterDTO> param = getParamByName(paramName, viewSensitiveParam, viewDisabledParam);
        if (param.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(param.get().getValue());
    }

    /**
     * To retrieve a parameter by its name.
     * This will use the local cache
     * @param param search parameter
     * @return corresponding DTO or null
     */
    public Optional<ParameterDTO> getParam(final IParameterEnum param) {
        return getParamByName(param.getParamName(), false, false);
    }

    /**
     * To retrieve a parameter by its name.
     * This will use the local cache
     * @param paramName search parameter
     * @param viewSensitiveParam boolean, "true" to view sensitive parameters ; "false" to hide sensitive parameters such as password. This is highly recommended for controllers
     * @param viewDisabledParam boolean, "true" to view disable parameters ; "false" to only show ACTIVE parameters
     * @return corresponding DTO or null
     */
    public Optional<ParameterDTO> getParamByName(final String paramName, boolean viewSensitiveParam, boolean viewDisabledParam) {
        if (StringUtils.isBlank(paramName)) {
            return Optional.empty();
        }
        // Retrieve value from cache
        final Parameter param = cache.getCachedValues().get(paramName.toUpperCase());
        if (param == null) {
            return Optional.empty();
        }
        // Validity check (must be enabled)
        if (!param.getIsActive() && !viewDisabledParam) {
            log.warn("Data leak avoidance|Someone tried to access a disabled parameter {}", paramName);
            return Optional.empty();
        }
        // security check
        if (param.getIsSensitive() && !viewSensitiveParam) {
            log.warn("Data leak avoidance|Someone asked to view the sensitive parameter {} without authorization: nothing has been returned", paramName);
            throw new UnauthorizedException("This information is restricted, you are not allowed to view '" + paramName + "'. Please contact our support.");
        }
        // Conversion to DTO
        return Optional.ofNullable(parameterMapper.dbEntityToDto(param));
    }

    /**
     * To retrieve all parameters at once
     * @param viewSensitiveParams boolean, "true" to view sensitive parameters ; "false" to hide sensitive parameters such as password. This is highly recommended for controllers
     * @param viewDisabledParam boolean, "true" to view disable parameters ; "false" to only show ACTIVE parameters
     * @return corresponding DTOs or null
     */
    public List<ParameterDTO> getAll(boolean viewSensitiveParams, boolean viewDisabledParam) {
        // Retrieve value from cache
        final List<Parameter> dbParameters = new ArrayList<>(cache.getCachedValues().values());

        // Convert to DTOs
        final List<ParameterDTO> dtos = new ArrayList<>();
        for (Parameter dbParam : dbParameters) {
            // Validity check (must be enabled)
            if (!dbParam.getIsActive() && !viewDisabledParam) {
                continue;
            }
            // security check, skip sensitive params if required
            if (dbParam.getIsSensitive() && !viewSensitiveParams) {
                continue;
            }
            dtos.add(parameterMapper.dbEntityToDto(dbParam));
        }
        return dtos;
    }

}
