package com.gen.marketrss.infrastructure.common.provider;

import com.gen.marketrss.domain.news.News;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.gen.marketrss.common.constant.Html.*;

@Component
@RequiredArgsConstructor
public class EmailProvider {

    @Value("${host}")
    private String host;
    private final JavaMailSender javaMailSender;

    public boolean sendCertificationMail(String email, String certificationNumber) {
        try {
            String CERTIFICATION_SUBJECT = "이메일 인증 메일";
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            String htmlContent = getCertificationMessage(certificationNumber);

            messageHelper.setTo(email);
            messageHelper.setSubject(CERTIFICATION_SUBJECT);
            messageHelper.setText(htmlContent, true);

            javaMailSender.send(message);

        } catch (Exception e ) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean sendNewsMail(String email, News news) {
        try {
            String NEWS_SUBJECT = "금일 뉴스 피드";
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            String newsMessage = getNewsMessage(news);

            messageHelper.setTo(email);
            messageHelper.setSubject(NEWS_SUBJECT);
            messageHelper.setText(newsMessage, true);

            javaMailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private String getCertificationMessage(String certificationNumber) {
        return String.format(EMAIL_VERTIFY, certificationNumber);
    }

    private String getNewsMessage(News news) {
        StringBuffer buffer = new StringBuffer();
        List<News.NewsPayload> newsPayloads = news.getNewsPayloads();

        for (News.NewsPayload newsPayload : newsPayloads) {
            String newsHtml = String.format(NEWS_CONTENT, newsPayload.getUrl(), host + newsPayload.getImage_url(), newsPayload.getTitle(), newsPayload.getSnippet(), newsPayload.getPublished_at());
            buffer.append(newsHtml);
        }

        return buffer.toString();
    }
}
