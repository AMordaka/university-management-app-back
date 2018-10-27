package mordaka.arkadiusz.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
public class SwaggerController {

    private static final String SWAGGER_URL = "redirect:/swagger-ui.html#";

    @GetMapping(value = "/docs")
    public String swaggerMapping() {
        return SWAGGER_URL;
    }
}
