package com.gen.marketrss.infrastructure.api;

import com.gen.marketrss.domain.entity.StockEntity;
import com.gen.marketrss.domain.news.News;
import com.gen.marketrss.infrastructure.repository.StockRepository;
import com.gen.marketrss.infrastructure.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static com.gen.marketrss.infrastructure.common.util.RedisUtil.getCurrentDateNewsKey;


@Service
@Slf4j
@RequiredArgsConstructor
public class MarketAuxApiService {

    private final WebClientUtil webClientUtil;
    private final RedisTemplate<String, News> redisTemplate;
    private final StockRepository stockRepository;

    @Value("${market-aux.uri.base-uri}")
    private String uri;

    @Value("${market-aux.uri.all}")
    private String all;

    @Value("${market-aux.token}")
    private String token;

    @Value("${market-aux.domains}")
    private String domains;

    @Value("${market-aux.payload-ttl}")
    private long payloadTtl;

    @Value("${img.upload}")
    private String uploadPath;

    public void cacheNewsLettersByUser() {

        try {
            List<StockEntity> stockEntities = stockRepository.findAll();

            stockEntities.forEach(stockEntity -> {
                String key = getCurrentDateNewsKey(stockEntity.getUserId());
                News news = getCurrentNewsPayloadsFromCache(key).orElseGet(() -> {
                    News newsPayload = fetchNewsPayloadsFromApi(stockEntity.getStock());
                    cacheNewsPayloads(key, newsPayload);
                    return newsPayload;
                });
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Optional<News> getCurrentNewsPayloadsFromCache(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    public News fetchNewsPayloadsFromApi(String stock) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("symbols", stock);
        params.add("industries", "Technology");
        params.add("filter_entities", "true");
        params.add("domains", domains);
        params.add("api_token", token);
        News news = webClientUtil.sendGetRequest(uri.concat(all)
                , params
                , new HashMap<>()
                , News.class);

        List<News.NewsPayload> newsPayloadList = news.getNewsPayloads();
        int i = 0;
        for (News.NewsPayload newsPayload : newsPayloadList) {
            i ++;
            String source = newsPayload.getSource().contains(".") ? newsPayload.getSource().split("\\.")[0] : newsPayload.getSource();
            newsPayload.setCustom_image_url(thirdPartyImageUpload(newsPayload.getImage_url(), source + i));
        }

        return news;
    }

    // 7일간 보관
    private void cacheNewsPayloads(String key , News newsPayloads) {
        redisTemplate.opsForValue().set(key, newsPayloads, payloadTtl , TimeUnit.DAYS);
    }

    /**
     * thirdParty 이미지 업로드
     * @param url
     * @return
     */
    private String thirdPartyImageUpload(String url, String source) {
        String fileName = "";

        try (InputStream in = new URL(url).openStream()) {
            String uploadPath = System.getProperty("user.dir") + this.uploadPath;

            File directory = new File(uploadPath);
            boolean isDirectory = directory.isDirectory();

            if (!isDirectory) {
                isDirectory = directory.mkdir();
            }

            if (isDirectory) {
                fileName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + source + ".png";
                String fullPath = uploadPath + fileName;
                Path path = Path.of(fullPath);
                BufferedImage inputImage = ImageIO.read(in);
                BufferedImage outputImage = new BufferedImage(800, 600, inputImage.getType());
                Graphics2D graphics2D = outputImage.createGraphics();
                graphics2D.drawImage(inputImage, 0, 0, 800, 600, null);
                graphics2D.dispose();
                ImageIO.write(outputImage, "png", new File(fullPath));
//                Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
            }

            return "/images/"+ fileName;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return "";
    }
}
