package org.zhaosd.shorturl.infrastructure.inmemory;

import org.springframework.stereotype.Component;
import org.zhaosd.shorturl.domain.Url;
import org.zhaosd.shorturl.domain.UrlRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 内存保存超链接对象仓库实现类
 * @author mvt-zhaosandong-mac
 */
@Component
public class InMemoryUrlRepository implements UrlRepository {

    private final static Integer MAX_SAVE_COUNT = 10 * 10000;
    Map<String, Url> urlRepo = new ConcurrentHashMap<>();
    Map<String, String> shortUrl2UrlIdMap = new ConcurrentHashMap<>();
    Map<String, String> srcUrl2UrlIdMap = new ConcurrentHashMap<>();

    @Override
    public void save(Url aUrl) {
        if (urlRepo.size() > MAX_SAVE_COUNT) {
            clearHalfData();
        }
        urlRepo.put(aUrl.getId(), aUrl);
        shortUrl2UrlIdMap.put(aUrl.getShortUrl(), aUrl.getId());
        srcUrl2UrlIdMap.put(aUrl.getSrcUrl(), aUrl.getId());
    }

    @Override
    public Url get(String id) {
        return urlRepo.get(id);
    }

    @Override
    public Url getByShortUrl(String shortUrl) {
        String id = shortUrl2UrlIdMap.get(shortUrl);
        if (id == null) {
            return null;
        }
        return get(id);
    }

    @Override
    public Url getBySrcUrl(String srcUrl) {
        String id = srcUrl2UrlIdMap.get(srcUrl);
        if (id == null) {
            return null;
        }
        return get(id);
    }

    @Override
    public Integer count() {
        return urlRepo.size();
    }

    /**
     * 清空前一半数据，防止内存溢出
     */
    private void clearHalfData() {
        Integer halfCount = MAX_SAVE_COUNT / 2;
        Integer clearIndex = 0;
        if (urlRepo.size() > halfCount) {
            for (String id : urlRepo.keySet()) {
                urlRepo.remove(id);
                clearIndex += 1;
                if (clearIndex >= halfCount) {
                    break;
                }
            }
        }

        clearIndex = 0;
        if (shortUrl2UrlIdMap.size() > halfCount) {
            for (String shortUrl : shortUrl2UrlIdMap.keySet()) {
                shortUrl2UrlIdMap.remove(shortUrl);
                clearIndex += 1;
                if (clearIndex >= halfCount) {
                    break;
                }
            }
        }

        clearIndex = 0;
        if (srcUrl2UrlIdMap.size() > halfCount) {
            for (String srcUrl : srcUrl2UrlIdMap.keySet()) {
                srcUrl2UrlIdMap.remove(srcUrl);
                clearIndex += 1;
                if (clearIndex >= halfCount) {
                    break;
                }
            }
        }
    }

}
