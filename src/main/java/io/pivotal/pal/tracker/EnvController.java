package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {
    private final String port;
    private final String memoryLimit;
    private final String cfInstanceIndex;
    private final String cfInstanceAddr;


    public EnvController(
            @Value("${PORT:NOT SET}") String port,
            @Value("${MEMORY_LIMIT:NOT SET}") String memoryLimit,
            @Value("${CF_INSTANCE_INDEX:NOT SET}")String cfInstanceIndex,
            @Value("${CF_INSTANCE_ADDR:NOT SET}")String cfInstanceAddr){
        this.port = port;
        this.memoryLimit = memoryLimit;
        this.cfInstanceIndex = cfInstanceIndex;
        this.cfInstanceAddr = cfInstanceAddr;
    }

    @GetMapping("/env")
    public Map<String, String> getEnv(){
        Map<String, String> envVariables = new HashMap<>();
        envVariables.put("PORT", port);
        envVariables.put("MEMORY_LIMIT", memoryLimit);
        envVariables.put("CF_INSTANCE_ADDR", cfInstanceAddr);
        envVariables.put("CF_INSTANCE_INDEX", cfInstanceIndex);
        return envVariables;
    }
}
