package xyz.sgld.sls.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import static xyz.sgld.sls.util.StrUtil.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 短链接数据访问
 */
@Repository
public class ShortLinkRepository {
    Logger logger = LoggerFactory.getLogger(ShortLinkRepository.class);
    private NamedParameterJdbcTemplate jdbc;
    private RowMapper<ShortLink> rowMapper;

    //QUERY BY SHORT LINK SQL
    private final static String QUERY_BY_SHORT_LINK = "SELECT ID, HASH,ORIGIN_LINK FROM short_links WHERE HASH = :hash";
    //QUERY BY ORIGIN LINK SQL
    private final static String QUERY_BY_ORIGIN_LINK = "SELECT ID,HASH,ORIGIN_LINK FROM short_links WHERE origin_Link = :originLink ";
    //QUERY INSERT LINK
    private final static String CREATE_SHORT_LINK = "INSERT INTO short_links(HASH,ORIGIN_LINK) VALUES ( :hash, :originLink )";

    private final static String QUERY_BY_ID = "SELECT ID, HASH,ORIGIN_LINK FROM short_links WHERE ID = :id";

    //
    private final static String PARAM_HASH = "hash";
    private final static String PARAM_ORIGIN_LINK = "originLink";
    private final static String PARAM_ID = "id";

    @Autowired
    public ShortLinkRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
        rowMapper = (rs, rowNum) -> {
            ShortLink sLink = new ShortLink();
            sLink.setId(rs.getLong(1));
            sLink.setHash(rs.getString(2));
            sLink.setOriginLink(rs.getString(3));
            return sLink;
        };
    }

    /**
     * GET ShortLink by origin link
     *
     * @param originLink 原始链接
     * @return ShortLink Object if null the not fround
     */
    public ShortLink getShortLinkByOriginLink(String originLink) {
        String sql = QUERY_BY_ORIGIN_LINK;

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(PARAM_ORIGIN_LINK, originLink);
        return queryShortLink(sql, parameters);
//        return jdbc.query(sql,rowMapper,new String[]{originLink}).get(0);
    }

    /**
     * get ShortLink by hash if not found the return null
     *
     * @param hash the short link
     * @return the short link associated object
     */
    public ShortLink getOriginLinkByHash(String hash) {
        String sql = QUERY_BY_SHORT_LINK;
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(PARAM_HASH, hash);
        return queryShortLink(sql, parameters);
    }

    /**
     * GET START LINK BY ID
     *
     * @param id short link id
     * @return the short link obj if founded,
     */
    public ShortLink getShortLinkById(long id) {
        String sql = QUERY_BY_ID;
        Map<String, Long> parameters = new HashMap<String, Long>();
        parameters.put(PARAM_ID, id);
        return queryShortLink(sql, parameters);
    }

    private ShortLink queryShortLink(String sql, Map parameters) {
        ShortLink shortlink = null;
        try {
            shortlink = (ShortLink) jdbc.queryForObject(sql, parameters, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.info("not fround data");
        } catch (Exception e) {
            logger.error("query exception", e);
        }
        return shortlink;
    }

    /**
     * insert the short link record
     *
     * @param shortLink the short link object the shortLink and originLink properties must not null
     * @return return the short link obj and failed id property
     * @throws IllegalArgumentException if the param shortLink's properties shortLink or originLink
     *                                  is null then throw it
     */
    public ShortLink createShortLink(@org.jetbrains.annotations.NotNull ShortLink shortLink) {
        if (shortLink == null || isEmpty(shortLink.getHash()) || isEmpty(shortLink.getOriginLink()))
            throw new IllegalArgumentException("the shortLink or its shortLink or its originLink is empty");
        String sql = CREATE_SHORT_LINK;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue(PARAM_HASH, shortLink.getHash());
        parameters.addValue(PARAM_ORIGIN_LINK, shortLink.getOriginLink());
        jdbc.update(sql, parameters, keyHolder);
        logger.info("自动插入id============================{}", keyHolder.getKey());
        shortLink.setId(keyHolder.getKey().longValue());
        return shortLink;
    }


}
