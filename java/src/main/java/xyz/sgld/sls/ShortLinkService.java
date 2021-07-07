package xyz.sgld.sls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.sgld.sls.data.ShortLink;
import xyz.sgld.sls.data.ShortLinkRepository;
import xyz.sgld.sls.util.HashUtil;
import xyz.sgld.sls.util.NumberUtil;
import xyz.sgld.sls.util.StrUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Component
public class ShortLinkService {
    private Logger logger = LoggerFactory.getLogger(ShortLinkService.class);
    private static final String UTF_8 = "utf-8";
    @Value("${domain.name}")
    private String domainName;

    private static final String SHORT_LINK_FORMAT_ERROR = "短链接格式错误（URLEncode问题）";

    private ShortLinkRepository shortLinkRepository;

    @Autowired
    public ShortLinkService(ShortLinkRepository shortLinkRepository) {
        this.shortLinkRepository = shortLinkRepository;
    }

    public ShortLinkRes<ShortLink> createShortLink(String originLink) {

        ShortLinkRes<ShortLink> res = null;
        ShortLink shortLink = null;
        try {
            //decode origin link
            if (originLink != null)
                originLink = URLDecoder.decode(originLink, UTF_8);
            shortLink = shortLinkRepository.getShortLinkByOriginLink(originLink);
            res = ShortLinkRes.createOkRes();
            if (shortLink == null) {
                shortLink = new ShortLink();
                shortLink.setOriginLink(originLink);
                shortLink.setHash(HashUtil.sha256(originLink));
                shortLink = this.shortLinkRepository.createShortLink(shortLink);
                //set short link
                shortLink.setShortLink(buildShortLink(shortLink.getId()));
                res.setData(shortLink);
            } else {
                res = ShortLinkRes.createOkRes();
                res.setData(shortLink);
            }
        } catch (IllegalArgumentException e) {
            res = ShortLinkRes.createArgsErrorRes();
        } catch (Exception e) {
            logger.error("unknown error", e);
            res = ShortLinkRes.createUnknownRes();
        } finally {
            if (res == null)
                res = ShortLinkRes.createUnknownRes();
        }
        return res;
    }

    public ShortLinkRes<ShortLink> getOriginLinkByShortLink(String shortLink) {
        if (StrUtil.isEmpty(shortLink)) {
            return ShortLinkRes.createArgsErrorRes();
        }
        try {
            shortLink = URLDecoder.decode(shortLink, UTF_8);
        } catch (UnsupportedEncodingException e) {
            logger.error("url decode error");
            ShortLinkRes res = ShortLinkRes.createArgsErrorRes();
            res.setDes(res.getDes() + ":" + SHORT_LINK_FORMAT_ERROR);
            return res;
        }
        long id = getShortLinkId(shortLink);
        logger.info("id:{}", id);
        ShortLink shortLinkObj = shortLinkRepository.getShortLinkById(id);
        if (shortLinkObj != null) {
            ShortLinkRes<ShortLink> res = ShortLinkRes.createOkRes();
            //build short link
            shortLinkObj.setShortLink(buildShortLink(shortLinkObj.getId()));
            res.setData(shortLinkObj);
            return res;
        }
        ShortLinkRes res = ShortLinkRes.createNotFoundRes();
        return res;
    }

    private long getShortLinkId(String shortLink) {
        return NumberUtil.str62ToLong(shortLink.substring(shortLink.indexOf('/') + 1));
    }

    /**
     * build short link
     */
    private String buildShortLink(long linkId) {
        //TODO 可以在这里进行按照id分组，然后使用不懂的域名来进行负载均衡
        return domainName + "/" + NumberUtil.longTo62Str(linkId);
    }
}
