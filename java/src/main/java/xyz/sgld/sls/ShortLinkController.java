package xyz.sgld.sls;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.sgld.sls.data.ShortLink;
/**
 * @author baigaojing
 * 短链接接口控制器
 * */
@Api(tags = "短链接")
@RestController()
public class ShortLinkController {
    private Logger logger = LoggerFactory.getLogger(ShortLinkController.class);
    private ShortLinkService shortLinkService;
    private final static String API_PATH_SHORT_LINK = "/short_link";
    private final static String API_PATH_ORIGIN_LINK = "/origin_link";

    //    @Autowired
    /**
     * 初始化shortLinkService成员
     * */
    public ShortLinkController(ShortLinkService shortLink) {
        this.shortLinkService = shortLink;
    }

    @GetMapping(API_PATH_SHORT_LINK)
    @ApiOperation("获取短链接")
    public ShortLinkRes<ShortLink> getShortLink(
            @RequestParam("origin_link")
            @Parameter(name = "origin_link", required = true, description = "原始链接,使用URLEncode进行编码") String originLink) {
        //TODO
        logger.info("[S]{}", API_PATH_SHORT_LINK);
        ShortLinkRes<ShortLink> res = shortLinkService.createShortLink(originLink);
        logger.info("[E]{}", API_PATH_SHORT_LINK);
        return res;
    }

    @GetMapping(API_PATH_ORIGIN_LINK)
    @ApiOperation("获取原始链接")
    public ShortLinkRes<ShortLink> getOriginLink(
            @RequestParam("short_link")
            @Parameter(name = "short_link", description = "短链接，链接使用URLEncode编码", required = true)
                    String shortLink) {
        logger.info("[S]{}", API_PATH_ORIGIN_LINK);
        ShortLinkRes res = shortLinkService.getOriginLinkByShortLink(shortLink);
        logger.info("[E][]", API_PATH_ORIGIN_LINK);
        return res;
    }

//    @RequestMapping(method = RequestMethod.GET)
//    public String getShortLine2(){
//        return shortLink.createShortLink("");
//    }
}
